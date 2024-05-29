package services;

import org.erkamber.dtos.RankingDTO;
import org.erkamber.entities.*;
import org.erkamber.repositories.*;
import org.erkamber.services.RankingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RankingServiceImplTest {

    @Mock
    private KartRepository kartRepository;

    @Mock
    private TrackRepository trackRepository;

    @Mock
    private RaceRepository raceRepository;

    @Mock
    private RacerRepository racerRepository;

    @Mock
    private StatisticRepository statisticRepository;

    @Mock
    private LapRepository lapRepository;

    @InjectMocks
    private RankingServiceImpl rankingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRankingByLapAndKart() {
        long trackId = 1L;
        long kartId = 1L;

        Track track = new Track();
        Kart kart = new Kart();
        Racer racer1 = new Racer(1L, "John", "Doe", "photo1.jpg");
        Racer racer2 = new Racer(2L, "Jane", "Smith", "photo2.jpg");

        when(trackRepository.findById(trackId)).thenReturn(Optional.of(track));
        when(kartRepository.findById(kartId)).thenReturn(Optional.of(kart));
        when(racerRepository.findAll()).thenReturn(Arrays.asList(racer1, racer2));

        when(raceRepository.findBestLapTimeByTrackAndKartAndRacer(track, kart, racer1))
                .thenReturn(Duration.ofSeconds(60));
        when(raceRepository.findBestLapDateByTrackAndKartAndRacer(track, kart, racer1))
                .thenReturn(LocalDate.of(2021, 1, 1));
        when(raceRepository.findBestLapTimeByTrackAndKartAndRacer(track, kart, racer2))
                .thenReturn(Duration.ofSeconds(65));
        when(raceRepository.findBestLapDateByTrackAndKartAndRacer(track, kart, racer2))
                .thenReturn(LocalDate.of(2021, 1, 2));

        List<RankingDTO> rankings = rankingService.getRankingByLapAndKart(trackId, kartId);

        assertEquals(2, rankings.size());
        assertEquals(1, rankings.get(0).getPosition());
        assertEquals("John", rankings.get(0).getRacerFirstName());
        assertEquals(2, rankings.get(1).getPosition());
        assertEquals("Jane", rankings.get(1).getRacerFirstName());
    }

    @Test
    public void testGetBestLastSession() {
        long trackId = 1L;
        long kartId = 1L;
        long racerId = 1L;

        Track track = new Track();
        Kart kart = new Kart();
        Racer racer = new Racer(1L, "John", "Doe", "photo1.jpg");

        when(trackRepository.findById(trackId)).thenReturn(Optional.of(track));
        when(kartRepository.findById(kartId)).thenReturn(Optional.of(kart));
        when(racerRepository.findById(racerId)).thenReturn(Optional.of(racer));

        when(raceRepository.findBestLapTimeForLastRaceByTrackAndKartAndRacer(track, kart, racer))
                .thenReturn(Duration.ofSeconds(60));
        when(raceRepository.findLastRaceDateByTrackAndKartAndRacer(track, kart, racer))
                .thenReturn(LocalDate.of(2021, 1, 1));

        RankingDTO bestLastSession = rankingService.getBestLastSession(trackId, kartId, racerId);

        assertNotNull(bestLastSession);
        assertEquals(0, bestLastSession.getPosition());
        assertEquals("John", bestLastSession.getRacerFirstName());
        assertEquals("Doe", bestLastSession.getRacerLastName());
        assertEquals(LocalDate.of(2021, 1, 1), bestLastSession.getRaceDate());
        assertEquals(Duration.ofSeconds(60), bestLastSession.getBestTime());
    }
}
