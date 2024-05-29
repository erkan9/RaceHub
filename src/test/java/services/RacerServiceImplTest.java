package services;

import org.erkamber.configurations.PasswordEncoderConfiguration;
import org.erkamber.dtos.RacerDTO;
import org.erkamber.entities.Racer;
import org.erkamber.enums.Expertise;
import org.erkamber.exceptions.ResourceNotFoundException;
import org.erkamber.repositories.RacerRepository;
import org.erkamber.repositories.StatisticRepository;
import org.erkamber.requestDtos.RacerRequestDTO;
import org.erkamber.services.RacerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RacerServiceImplTest {

    @Mock
    private RacerRepository racerRepository;

    @Mock
    private StatisticRepository statisticRepository;

    @Mock
    private ModelMapper mapper;

    @Spy
    private PasswordEncoderConfiguration passwordEncoderConfiguration;

    @InjectMocks
    private RacerServiceImpl racerService;

    Racer racer;

    RacerDTO racerDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        racer = new Racer(1L, "erkan", "kamber", "plovdiv", "15-25", "test@gmail.com", "password123", "somePhoto", Expertise.BEGINNER);

        racerDTO = new RacerDTO(1L, "erkan", "kamber", "plovdiv", "15-25", "test@gmail.com", "somePhoto", Expertise.BEGINNER);

    }

    @Test
    @Disabled
    public void testSaveRacer() {
        RacerRequestDTO newRacer = new RacerRequestDTO();
        Racer racer = new Racer();
        when(mapper.map(newRacer, Racer.class)).thenReturn(racer);
        when(passwordEncoderConfiguration.passwordEncoder().encode(any())).thenReturn("encodedPassword");
        when(racerRepository.save(racer)).thenReturn(racer);
        when(mapper.map(racer, RacerDTO.class)).thenReturn(racerDTO);

        RacerDTO result = racerService.save(newRacer);

        assertNotNull(result);
        assertEquals(racerDTO.getRacerId(), result.getRacerId());
        assertEquals(racerDTO.getFirstName(), result.getFirstName());
        assertEquals(racerDTO.getLastName(), result.getLastName());
        assertEquals(racerDTO.getCity(), result.getCity());
        assertEquals(racerDTO.getAgeRange(), result.getAgeRange());
        assertEquals(racerDTO.getEmail(), result.getEmail());
        assertEquals(racerDTO.getPhoto(), result.getPhoto());
        assertEquals(racerDTO.getExpertise(), result.getExpertise());
        verify(statisticRepository, times(1)).save(any());
    }

    @Test
    @Disabled
    public void testGetRacerById() {

        when(racerRepository.findById(1L)).thenReturn(Optional.of(racer));
        when(mapper.map(racer, RacerDTO.class)).thenReturn(racerDTO);

        RacerDTO result = racerService.getById(1L);

        assertNotNull(result);
        assertEquals(racerDTO.getRacerId(), result.getRacerId());
        assertEquals(racerDTO.getFirstName(), result.getFirstName());
        assertEquals(racerDTO.getLastName(), result.getLastName());
        assertEquals(racerDTO.getCity(), result.getCity());
        assertEquals(racerDTO.getAgeRange(), result.getAgeRange());
        assertEquals(racerDTO.getEmail(), result.getEmail());
        assertEquals(racerDTO.getPhoto(), result.getPhoto());
        assertEquals(racerDTO.getExpertise(), result.getExpertise());
    }

    @Test
    public void testGetRacerByEmail() {
        String email = "test@example.com";
        when(racerRepository.findRacerByEmail(email)).thenReturn(Optional.of(racer));
        when(mapper.map(racer, RacerDTO.class)).thenReturn(racerDTO);

        RacerDTO result = racerService.getByEmail(email);

        assertNotNull(result);
        assertEquals(racerDTO.getRacerId(), result.getRacerId());
        assertEquals(racerDTO.getFirstName(), result.getFirstName());
        assertEquals(racerDTO.getLastName(), result.getLastName());
        assertEquals(racerDTO.getCity(), result.getCity());
        assertEquals(racerDTO.getAgeRange(), result.getAgeRange());
        assertEquals(racerDTO.getEmail(), result.getEmail());
        assertEquals(racerDTO.getPhoto(), result.getPhoto());
        assertEquals(racerDTO.getExpertise(), result.getExpertise());
    }

    @Test
    public void testDeleteRacerById() {
        when(racerRepository.findById(1L)).thenReturn(Optional.of(racer));
        when(mapper.map(racer, RacerDTO.class)).thenReturn(racerDTO);

        RacerDTO result = racerService.deleteById(1L);

        assertNotNull(result);
        assertEquals(racerDTO.getRacerId(), result.getRacerId());
        assertEquals(racerDTO.getFirstName(), result.getFirstName());
        assertEquals(racerDTO.getLastName(), result.getLastName());
        assertEquals(racerDTO.getCity(), result.getCity());
        assertEquals(racerDTO.getAgeRange(), result.getAgeRange());
        assertEquals(racerDTO.getEmail(), result.getEmail());
        assertEquals(racerDTO.getPhoto(), result.getPhoto());
        assertEquals(racerDTO.getExpertise(), result.getExpertise());
        verify(racerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetRacerById_NotFound() {
        long racerId = 1L;
        when(racerRepository.findById(racerId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> racerService.getById(racerId));
        assertEquals("Racer not found with id: 1", exception.getMessage());
    }

    @Test
    public void testGetRacerByEmail_NotFound() {
        String email = "nonexistent@example.com";
        when(racerRepository.findRacerByEmail(email)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> racerService.getByEmail(email));
        assertEquals("Racer not found with email: nonexistent@example.com", exception.getMessage());
    }

    @Test
    public void testGetRacerByEmailAndPassword_NotFound() {
        String email = "nonexistent@example.com";
        String password = "password";
        when(racerRepository.findAll()).thenReturn(List.of());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> racerService.getByEmailAndPassword(email, password));
        assertEquals("Incorrect email or password!", exception.getMessage());
    }

    @Test
    public void testDeleteRacerById_NotFound() {
        long racerId = 1L;
        when(racerRepository.findById(racerId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> racerService.deleteById(racerId));
        assertEquals("Racer not found with id: 1", exception.getMessage());
    }

}
