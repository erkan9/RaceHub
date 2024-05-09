package org.erkamber.services;

import lombok.extern.slf4j.Slf4j;
import org.erkamber.dtos.RaceDTO;
import org.erkamber.dtos.StatisticDTO;
import org.erkamber.entities.*;
import org.erkamber.enums.Expertise;
import org.erkamber.exceptions.ResourceNotFoundException;
import org.erkamber.repositories.*;
import org.erkamber.requestDtos.LapRequestDTO;
import org.erkamber.requestDtos.RaceRequestDTO;
import org.erkamber.services.interfaces.RaceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RaceServiceImpl implements RaceService {
    private final KartRepository kartRepository;

    private final TrackRepository trackRepository;

    private final RaceRepository raceRepository;

    private final ModelMapper mapper;

    private final RacerRepository racerRepository;

    private final StatisticRepository statisticRepository;

    private final LapRepository lapRepository;

    public RaceServiceImpl(RaceRepository raceRepository, ModelMapper mapper, RacerRepository racerRepository,
                           StatisticRepository statisticRepository, LapRepository lapRepository,
                           TrackRepository trackRepository,
                           KartRepository kartRepository) {
        this.raceRepository = raceRepository;
        this.mapper = mapper;
        this.racerRepository = racerRepository;
        this.statisticRepository = statisticRepository;
        this.lapRepository = lapRepository;
        this.trackRepository = trackRepository;
        this.kartRepository = kartRepository;
    }

    @Transactional
    @Override
    public RaceDTO save(RaceRequestDTO newRace) {
        Racer racer = findRacerOrThrow(newRace.getRacerId());
        Track track = findTrackOrThrow(newRace.getTrackId());
        List<Lap> raceLaps = mapToDtoList(newRace.getLaps());

        updateStatistics(racer, track, raceLaps);
        updateRacerExpertise(racer);

        Race race = createRace(racer, raceLaps, findKartOrThrow(newRace.getKartId()), track);
        raceRepository.save(race);

        raceLaps.forEach(laps -> laps.setRace(race));

        lapRepository.saveAll(raceLaps);

        return mapToRaceDTO(race);
    }

    @Override
    public RaceDTO getById(long raceID) {
        Optional<Race> races = raceRepository.findById(raceID);

        return mapper.map(races.get(), RaceDTO.class);
    }

    @Override
    public List<RaceDTO> getByUserId(long racerId) {
        List<Race> races = raceRepository.findRaceByRacer(racerRepository.findById(racerId).get());

        return mapToRaceDtoList(races);
    }

    @Override
    public List<RaceDTO> deleteUserRaces(long racerId) {
        List<Race> races = raceRepository.findRaceByRacer(racerRepository.findById(racerId).get());
        raceRepository.deleteAll(races);
        return mapToRaceDtoList(races);
    }

    @Override
    public RaceDTO deleteById(long raceId) {
        Optional<Race> raceOptional = raceRepository.findById(raceId);
        if (raceOptional.isPresent()) {
            Race race = raceOptional.get();
            raceRepository.deleteById(raceId);
            return mapper.map(race, RaceDTO.class);
        } else {
            return null; // Or throw an exception indicating that the race with the given ID doesn't exist
        }
    }

    private Racer findRacerOrThrow(long racerId) {
        return racerRepository.findById(racerId)
                .orElseThrow(() -> new ResourceNotFoundException("Racer not Found: " + racerId, "Racer"));
    }

    private Track findTrackOrThrow(long trackId) {
        return trackRepository.findById(trackId)
                .orElseThrow(() -> new ResourceNotFoundException("Track not Found: " + trackId, "Track"));
    }

    private Kart findKartOrThrow(long kartId) {
        return kartRepository.findById(kartId)
                .orElseThrow(() -> new ResourceNotFoundException("Kart with ID " + kartId, "Kart"));
    }

    private void updateStatistics(Racer racer, Track track, List<Lap> raceLaps) {
        Statistic statistic = findOrCreateStatistic(racer);
        double trackLengthKms = track.getTrackLengthKms();
        double drivenKm = trackLengthKms * raceLaps.size();
        statistic.setDrivenKms(statistic.getDrivenKms() + drivenKm);

        Duration totalLapTime = calculateTotalLapTime(raceLaps);
        statistic.setDrivenTimeMinutes(statistic.getDrivenTimeMinutes().plus(totalLapTime));

        updateBestLapTime(track, raceLaps);
        statisticRepository.save(statistic);
    }

    private Statistic findOrCreateStatistic(Racer racer) {
        return statisticRepository.findStatisticByRacer(racer);
    }

    private Duration calculateTotalLapTime(List<Lap> raceLaps) {
        return raceLaps.stream()
                .map(Lap::getLapTime)
                .reduce(Duration.ZERO, Duration::plus);
    }

    private void updateBestLapTime(Track track, List<Lap> raceLaps) {
        Duration bestTrackTime = track.getBestTrackTime();
        raceLaps.stream()
                .map(Lap::getLapTime)
                .filter(lapTime -> lapTime.compareTo(bestTrackTime) < 0)
                .findFirst()
                .ifPresent(lapTime -> {
                    //TODO: Send Email
                    track.setBestTrackTime(lapTime);
                });

        trackRepository.save(track);
    }

    private void updateRacerExpertise(Racer racer) {
        Statistic statistic = findOrCreateStatistic(racer);
        double drivenKms = statistic.getDrivenKms();
        if (drivenKms >= 75) {
            racer.setExpertise(Expertise.RACER);
        } else if (drivenKms >= 50) {
            racer.setExpertise(Expertise.EXPERT);
        } else if (drivenKms >= 25) {
            racer.setExpertise(Expertise.INTERMEDIATE);
        }
        racerRepository.save(racer);
    }

    private Race createRace(Racer racer, List<Lap> raceLaps, Kart kart, Track track) {
        return new Race(racer, raceLaps, kart, track);
    }

    private RaceDTO mapToRaceDTO(Race race) {
        RaceDTO raceDTO = mapper.map(race, RaceDTO.class);
        Statistic racerStats = findOrCreateStatistic(race.getRacer());
        raceDTO.getRacer().setRacerStatistics(mapper.map(racerStats, StatisticDTO.class));
        return raceDTO;
    }

    private List<Lap> mapToDtoList(List<LapRequestDTO> laps) {

        return laps.stream()
                .map(lap -> mapper.map(lap, Lap.class))
                .collect(Collectors.toList());
    }

    private List<RaceDTO> mapToRaceDtoList(List<Race> races) {

        return races.stream()
                .map(lap -> mapper.map(lap, RaceDTO.class))
                .collect(Collectors.toList());
    }
}
