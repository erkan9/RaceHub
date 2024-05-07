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
        race.setLaps(raceLaps);
        raceRepository.save(race);

        return mapToRaceDTO(race);
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


    @Override
    public RaceDTO getById(long raceID) {
        return null;
    }

    @Override
    public List<RaceDTO> getByUserId(long userId) {
        return null;
    }

    @Override
    public List<RaceDTO> deleteUserRaces(long userId) {
        return null;
    }

    @Override
    public RaceDTO deleteById(long raceId) {
        return null;
    }

    private List<Lap> mapToDtoList(List<LapRequestDTO> laps) {

        return laps.stream()
                .map(lap -> mapper.map(lap, Lap.class))
                .collect(Collectors.toList());

    }
}
