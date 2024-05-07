package org.erkamber.services;

import lombok.extern.slf4j.Slf4j;
import org.erkamber.dtos.RankingDTO;
import org.erkamber.entities.*;
import org.erkamber.repositories.*;
import org.erkamber.services.interfaces.RankingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class RankingServiceImpl implements RankingService {

    private final KartRepository kartRepository;
    private final TrackRepository trackRepository;

    private final RaceRepository raceRepository;

    private final ModelMapper mapper;

    private final RacerRepository racerRepository;

    private final StatisticRepository statisticRepository;

    private final LapRepository lapRepository;

    public RankingServiceImpl(KartRepository kartRepository, TrackRepository trackRepository,
                              RaceRepository raceRepository, ModelMapper mapper, RacerRepository racerRepository,
                              StatisticRepository statisticRepository, LapRepository lapRepository) {
        this.kartRepository = kartRepository;
        this.trackRepository = trackRepository;
        this.raceRepository = raceRepository;
        this.mapper = mapper;
        this.racerRepository = racerRepository;
        this.statisticRepository = statisticRepository;
        this.lapRepository = lapRepository;
    }


    @Override
    public List<RankingDTO> getRankingByLapAndKart(long trackId, long kartId) {
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new EntityNotFoundException("Track with ID " + trackId + " not found"));

        Kart kart = kartRepository.findById(kartId)
                .orElseThrow(() -> new EntityNotFoundException("Kart with ID " + kartId + " not found"));

        List<Racer> allRacers = racerRepository.findAll();
        List<RankingDTO> bestLapDTOs = new ArrayList<>();

        for (Racer racer : allRacers) {
            // Find the best lap time for each racer

            List<Race> allRaces = raceRepository.findRaceByRacerAndTrackAndRaceKart(racer, track, kart);

            Duration bestLapTime = Duration.ZERO;

            for (Race race : allRaces) {
                for (Lap lap : race.getLaps()) {
                    Duration lapTime = lap.getLapTime();
                    if (lapTime.compareTo(bestLapTime) < 0) {
                        bestLapTime = lapTime;
                    }
                }
            }

            // Construct BestLapDTO
            RankingDTO bestLapDTO = new RankingDTO();
            bestLapDTO.setPosition(0);
            bestLapDTO.setRacerPhoto(racer.getPhoto());
            bestLapDTO.setRacerFirstName(racer.getFirstName());
            bestLapDTO.setRacerLastName(racer.getLastName());
            // You may need to find the race date from the bestLaps list or from other source
            bestLapDTO.setRaceDate(null); // Replace null with actual race date
            bestLapDTO.setBestTime(bestLapTime);

            bestLapDTOs.add(bestLapDTO);

            bestLapDTOs.sort(Comparator.comparing(RankingDTO::getBestTime));

            // Set positions based on sorted list
            int position = 1;
            for (RankingDTO dto : bestLapDTOs) {
                dto.setPosition(position++);
            }
        }

        return bestLapDTOs;
    }

}
