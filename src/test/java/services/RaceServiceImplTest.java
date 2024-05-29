package services;

import org.erkamber.dtos.*;
import org.erkamber.entities.*;
import org.erkamber.enums.Expertise;
import org.erkamber.repositories.*;
import org.erkamber.requestDtos.LapRequestDTO;
import org.erkamber.requestDtos.RaceRequestDTO;
import org.erkamber.services.RaceServiceImpl;
import org.erkamber.services.interfaces.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RaceServiceImplTest {

    @InjectMocks
    private RaceServiceImpl raceService;

    @Mock
    private KartRepository kartRepository;

    @Mock
    private TrackRepository trackRepository;

    @Mock
    private RaceRepository raceRepository;

    @Mock
    private ModelMapper mapper;

    @Mock
    private RacerRepository racerRepository;

    @Mock
    private StatisticRepository statisticRepository;

    @Mock
    private LapRepository lapRepository;

    @Mock
    private EmailService emailService;

    private RaceRequestDTO testRaceRequestDTO;
    private Race testRace;
    private RaceDTO testRaceDTO;
    private Racer testRacer;
    private Track testTrack;
    private Kart testKart;
    private List<Lap> testLaps;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        // Initialize the test data
        testRacer = new Racer();
        testRacer.setRacerId(1L);
        testRacer.setFirstName("John");
        testRacer.setLastName("Doe");
        testRacer.setEmail("john.doe@example.com");
        testRacer.setCity("City");
        testRacer.setAgeRange("20-30");
        testRacer.setPassword("password");
        testRacer.setPhoto("photo.jpg");
        testRacer.setExpertise(Expertise.BEGINNER);

        testTrack = new Track();
        testTrack.setTrackId(1L);
        testTrack.setTrackName("Track A");
        testTrack.setBestTrackTime(Duration.ofMinutes(2).plusSeconds(10));
        testTrack.setTrackLengthKms(5.0);

        testKart = new Kart();
        testKart.setKartId(1L);
        testKart.setModel("Kart Model");

        Lap lap = new Lap();
        lap.setLapTime(Duration.ofMinutes(1).plusSeconds(50));
        lap.setBestTime(true);
        testLaps = List.of(lap);

        testRace = new Race();
        testRace.setRaceId(1L);
        testRace.setRacer(testRacer);
        testRace.setTrack(testTrack);
        testRace.setRaceKart(testKart);
        testRace.setLaps(testLaps);

        testRaceDTO = new RaceDTO();
        testRaceDTO.setRaceId(1L);
        testRaceDTO.setRacer(new RacerDTO(testRacer.getRacerId(), testRacer.getFirstName(), testRacer.getLastName(), testRacer.getCity(), testRacer.getAgeRange(), testRacer.getEmail(), testRacer.getPhoto(), testRacer.getExpertise()));
        testRaceDTO.setTrack(new TrackDTO(testTrack.getTrackId(), testTrack.getTrackName(), testTrack.getBestTrackTime(), testTrack.getTrackLengthKms()));
        testRaceDTO.setRaceKart(new KartDTO(testKart.getKartId(), testKart.getModel()));
        testRaceDTO.setLaps(Arrays.asList(new LapDTO()));

        testRaceRequestDTO = new RaceRequestDTO();
        testRaceRequestDTO.setRacerId(testRacer.getRacerId());
        testRaceRequestDTO.setTrackId(testTrack.getTrackId());
        testRaceRequestDTO.setKartId(testKart.getKartId());
        testRaceRequestDTO.setLaps(Arrays.asList(new LapRequestDTO()));

        // Mock the behavior of StatisticRepository
        Statistic statistic = new Statistic();
        statistic.setDrivenKms(0.0); // Set the default value
        when(statisticRepository.findStatisticByRacer(testRacer)).thenReturn(statistic);
    }

    @Test
    public void testGetRaceById_Success() {
        when(raceRepository.findById(1L)).thenReturn(Optional.of(testRace));
        when(mapper.map(testRace, RaceDTO.class)).thenReturn(testRaceDTO);

        RaceDTO result = raceService.getById(1L);

        assertNotNull(result);
        assertEquals(testRaceDTO, result);
        verify(raceRepository).findById(1L);
        verify(mapper).map(testRace, RaceDTO.class);
    }

    @Test
    public void testGetAllRaces_Success() {
        List<Race> allRaces = Arrays.asList(testRace);
        when(raceRepository.findAll()).thenReturn(allRaces);
        when(mapper.map(testRace, RaceDTO.class)).thenReturn(testRaceDTO);

        List<RaceDTO> result = raceService.getAllRaces();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testRaceDTO, result.get(0));
        verify(raceRepository).findAll();
        verify(mapper).map(testRace, RaceDTO.class);
    }

    @Test
    public void testGetLastRaceOfRacer_Success() {
        when(racerRepository.findById(testRacer.getRacerId())).thenReturn(Optional.of(testRacer));
        when(raceRepository.findFirstByRacerOrderByLapsLapDateDesc(testRacer)).thenReturn(Optional.of(testRace));
        when(mapper.map(testRace, RaceDTO.class)).thenReturn(testRaceDTO);

        RaceDTO result = raceService.getLastRaceOfRacer(testRacer.getRacerId());

        assertNotNull(result);
        assertEquals(testRaceDTO, result);
        verify(racerRepository).findById(testRacer.getRacerId());
        verify(raceRepository).findFirstByRacerOrderByLapsLapDateDesc(testRacer);
        verify(mapper).map(testRace, RaceDTO.class);
    }

    @Test
    public void testGetRacesByRacerId_Success() {
        List<Race> races = Arrays.asList(testRace);
        when(racerRepository.findById(testRacer.getRacerId())).thenReturn(Optional.of(testRacer));
        when(raceRepository.findRaceByRacer(testRacer)).thenReturn(races);
        when(mapper.map(testRace, RaceDTO.class)).thenReturn(testRaceDTO);

        List<RaceDTO> result = raceService.getByRacerId(testRacer.getRacerId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testRaceDTO, result.get(0));
        verify(racerRepository).findById(testRacer.getRacerId());
        verify(raceRepository).findRaceByRacer(testRacer);
        verify(mapper).map(testRace, RaceDTO.class);
    }

    @Test
    public void testDeleteRacerRaces_Success() {
        List<Race> races = Arrays.asList(testRace);
        when(racerRepository.findById(testRacer.getRacerId())).thenReturn(Optional.of(testRacer));
        when(raceRepository.findRaceByRacer(testRacer)).thenReturn(races);
        when(mapper.map(testRace, RaceDTO.class)).thenReturn(testRaceDTO);

        List<RaceDTO> result = raceService.deleteRacerRaces(testRacer.getRacerId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testRaceDTO, result.get(0));
        verify(racerRepository).findById(testRacer.getRacerId());
        verify(raceRepository).findRaceByRacer(testRacer);
        verify(raceRepository).deleteAll(races);
        verify(mapper).map(testRace, RaceDTO.class);
    }

    @Test
    public void testDeleteRaceById_Success() {
        when(raceRepository.findById(1L)).thenReturn(Optional.of(testRace));
        when(mapper.map(testRace, RaceDTO.class)).thenReturn(testRaceDTO);

        RaceDTO result = raceService.deleteById(1L);

        assertNotNull(result);
        assertEquals(testRaceDTO, result);
        verify(raceRepository).findById(1L);
        verify(raceRepository).deleteById(1L);
        verify(mapper).map(testRace, RaceDTO.class);
    }
}
