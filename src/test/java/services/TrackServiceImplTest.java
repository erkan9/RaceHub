package services;

import org.erkamber.dtos.TrackDTO;
import org.erkamber.entities.Race;
import org.erkamber.entities.Racer;
import org.erkamber.entities.Track;
import org.erkamber.exceptions.ResourceNotFoundException;
import org.erkamber.repositories.RaceRepository;
import org.erkamber.repositories.RacerRepository;
import org.erkamber.repositories.TrackRepository;
import org.erkamber.requestDtos.TrackRequestDTO;
import org.erkamber.services.TrackServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrackServiceImplTest {

    @InjectMocks
    private TrackServiceImpl trackService;

    @Mock
    private TrackRepository trackRepository;

    @Mock
    private RacerRepository racerRepository;

    @Mock
    private RaceRepository raceRepository;

    @Mock
    private ModelMapper mapper;

    private long trackId;

    private String trackCity;

    private Track testTrack;

    private TrackRequestDTO testTrackRequestDTO;

    private TrackDTO testTrackDTO;

    private Racer testRacer;
    private List<Race> testRaces;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        testTrack = new Track();
        testTrack.setTrackId(1L);
        testTrack.setCity("Plovdiv");
        testTrack.setTrackName("Silverstone Circuit");
        testTrack.setTrackLengthKms(5.891);
        testTrack.setBestTrackTime(Duration.ZERO);
        testTrack.setTrackPhoto("https://example.com/silverstone.jpg");

        testTrackDTO = new TrackDTO();
        testTrackDTO.setTrackId(1L);
        testTrackDTO.setCity("Plovdiv");
        testTrackDTO.setTrackName("Silverstone Circuit");
        testTrackDTO.setTrackLengthKms(5.891);
        testTrackDTO.setBestTrackTime(Duration.ZERO);
        testTrackDTO.setTrackPhoto("https://example.com/silverstone.jpg");

        testTrackRequestDTO = new TrackRequestDTO();
        testTrackRequestDTO.setTrackName("Silverstone Circuit");
        testTrackRequestDTO.setCity("Plovdiv Updated");
        testTrackRequestDTO.setTrackLengthKms(6.0);
        testTrackRequestDTO.setBestTrackTime(Duration.ofMinutes(1).plusSeconds(27));

        testRacer = new Racer();
        testRacer.setRacerId(1L);

        testTrack = new Track();
        testTrack.setTrackId(1L);
        testTrack.setTrackName("Silverstone Circuit");

        testTrackDTO = new TrackDTO();
        testTrackDTO.setTrackId(1L);
        testTrackDTO.setTrackName("Silverstone Circuit");

        Race testRace = new Race();
        testRace.setRacer(testRacer);
        testRace.setTrack(testTrack);

        testRaces = Arrays.asList(testRace, testRace);
    }

    @Test
    public void testUpdateTrack_Success() {

        when(trackRepository.findById(1L)).thenReturn(Optional.of(testTrack));
        when(trackRepository.save(any(Track.class))).thenReturn(testTrack);
        when(mapper.map(testTrack, TrackDTO.class)).thenReturn(testTrackDTO);

        TrackDTO result = trackService.updateTrack(testTrackRequestDTO, 1L);

        assertEquals(testTrackDTO, result);
        verify(trackRepository).findById(1L);
        verify(trackRepository).save(any(Track.class));
        verify(mapper).map(testTrack, TrackDTO.class);
    }

    @Test
    public void testUpdateTrack_TrackNotFound() {

        when(trackRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            trackService.updateTrack(testTrackRequestDTO, 1L);
        });

        assertEquals("Track not found with id: 1", exception.getMessage());
        verify(trackRepository).findById(1L);
        verify(trackRepository, never()).save(any(Track.class));
        verify(mapper, never()).map(any(Track.class), eq(TrackDTO.class));
    }

    @Test
    public void testGetAll_Success() {

        final List testTracks = mock(List.class);

        when(trackService.getAll()).thenReturn(testTracks);

        List expected = trackService.getAll();

        assertNotNull(expected);
    }

    @Test
    public void testGetById_Success() {

        when(trackRepository.findById(trackId)).thenReturn(Optional.of(testTrack));

        when(mapper.map(testTrack, TrackDTO.class)).thenReturn(testTrackDTO);

        TrackDTO result = trackService.getById(trackId);

        assertNotNull(result);
        assertEquals(testTrackDTO, result);
    }

    @Test
    public void testGetById_TrackNotFound() {

        when(trackRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            trackService.getById(1L);
        });

        assertEquals("Track not found with id: 1", exception.getMessage());
    }

    @Test
    public void testSave_success(){

        when(mapper.map(testTrackDTO, Track.class)).thenReturn(testTrack);

        when(trackService.save(testTrackRequestDTO)).thenReturn(testTrackDTO);

        TrackDTO result = trackService.save(testTrackRequestDTO);

        assertEquals(testTrackRequestDTO.getTrackName(), testTrackDTO.getTrackName());
    }

    @Test
    public void testGetByCity_Success(){

        final List testTracks = mock(List.class);

        when(trackRepository.findTrackByCity(trackCity)).thenReturn(testTracks);

        when(mapper.map(testTrack, TrackDTO.class)).thenReturn(testTrackDTO);

        List expected = trackService.getByCity(trackCity);

        assertNotNull(expected);
    }

    @Test
    public void testFindMostPreferredTrackForRacer_Success() {

        when(racerRepository.findById(1L)).thenReturn(Optional.of(testRacer));
        when(raceRepository.findRaceByRacer(testRacer)).thenReturn(testRaces);
        when(trackRepository.findById(1L)).thenReturn(Optional.of(testTrack));
        when(mapper.map(testTrack, TrackDTO.class)).thenReturn(testTrackDTO);

        TrackDTO result = trackService.findMostPreferredTrackForRacer(1L);

        assertNotNull(result);
        assertEquals(testTrackDTO, result);
        verify(racerRepository).findById(1L);
        verify(raceRepository).findRaceByRacer(testRacer);
        verify(trackRepository).findById(1L);
        verify(mapper).map(testTrack, TrackDTO.class);
    }

    @Test
    public void testFindMostPreferredTrackForRacer_RacerNotFound() {

        when(racerRepository.findById(1L)).thenReturn(Optional.empty());

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            trackService.findMostPreferredTrackForRacer(1L);
        });

        assertEquals("Racer not found with ID: 1", exception.getMessage());
        verify(racerRepository).findById(1L);
        verify(raceRepository, never()).findRaceByRacer(any(Racer.class));
        verify(trackRepository, never()).findById(anyLong());
        verify(mapper, never()).map(any(Track.class), eq(TrackDTO.class));
    }
}
