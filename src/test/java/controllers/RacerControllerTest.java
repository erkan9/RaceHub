package controllers;

import org.erkamber.controllers.RacerController;
import org.erkamber.dtos.RacerDTO;
import org.erkamber.enums.Expertise;
import org.erkamber.requestDtos.RacerRequestDTO;
import org.erkamber.services.interfaces.RacerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RacerControllerTest {

    @Mock
    private RacerService racerService;

    @InjectMocks
    private RacerController racerController;

    RacerDTO racerDTO = new RacerDTO(1L, "erkan", "kamber", "plovdiv", "15-25", "test@gmail.com", "somePhoto", Expertise.BEGINNER);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveRacer() {

        RacerRequestDTO newRacerRequestDTO = new RacerRequestDTO();
        newRacerRequestDTO.setFirstName("John");
        newRacerRequestDTO.setLastName("Doe");
        newRacerRequestDTO.setEmail("john.doe@example.com");
        newRacerRequestDTO.setPassword("password123");

        when(racerService.save(newRacerRequestDTO)).thenReturn(racerDTO);

        ResponseEntity<RacerDTO> responseEntity = racerController.saveRacer(newRacerRequestDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(racerDTO, responseEntity.getBody());

        HttpHeaders headers = responseEntity.getHeaders();
        assertEquals("1", headers.getFirst("Racer-Id"));

        verify(racerService).save(newRacerRequestDTO);
    }

    @Test
    public void testGetRacerById() {
        long racerId = 1L;
        racerDTO.setRacerId(racerId);
        racerDTO.setFirstName("John");
        racerDTO.setLastName("Doe");
        racerDTO.setEmail("john.doe@example.com");

        when(racerService.getById(racerId)).thenReturn(racerDTO);

        ResponseEntity<RacerDTO> responseEntity = racerController.getRacerById(racerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(racerDTO, responseEntity.getBody());

        verify(racerService).getById(racerId);
    }

    @Test
    public void testGetRacerByEmailAndPassword() {
        String email = "john.doe@example.com";
        String password = "password123";
        racerDTO.setRacerId(1L);
        racerDTO.setFirstName("John");
        racerDTO.setLastName("Doe");
        racerDTO.setEmail(email);

        when(racerService.getByEmailAndPassword(email, password)).thenReturn(racerDTO);

        ResponseEntity<RacerDTO> responseEntity = racerController.getRacerByEmailAndPassword(email, password);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(racerDTO, responseEntity.getBody());

        verify(racerService).getByEmailAndPassword(email, password);
    }

    @Test
    public void testGetRacerByEmail() {
        String email = "john.doe@example.com";

        racerDTO.setRacerId(1L);
        racerDTO.setFirstName("John");
        racerDTO.setLastName("Doe");
        racerDTO.setEmail(email);

        when(racerService.getByEmail(email)).thenReturn(racerDTO);

        ResponseEntity<RacerDTO> responseEntity = racerController.getRacerByEmail(email);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(racerDTO, responseEntity.getBody());

        verify(racerService).getByEmail(email);
    }

    @Test
    public void testDeleteRacerById() {
        long racerId = 1L;
        RacerDTO deletedRacerDTO = new RacerDTO(1L, "erkan", "kamber", "plovdiv", "15-25", "test@gmail.com", "somePhoto", Expertise.BEGINNER);

        deletedRacerDTO.setRacerId(racerId);
        deletedRacerDTO.setFirstName("John");
        deletedRacerDTO.setLastName("Doe");
        deletedRacerDTO.setEmail("john.doe@example.com");

        when(racerService.deleteById(racerId)).thenReturn(deletedRacerDTO);

        ResponseEntity<RacerDTO> responseEntity = racerController.deleteRacerById(racerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(deletedRacerDTO, responseEntity.getBody());

        verify(racerService).deleteById(racerId);
    }

    @Test
    public void testUpdateRacer() {
        long racerId = 1L;
        RacerRequestDTO updateRacerRequestDTO = new RacerRequestDTO();
        updateRacerRequestDTO.setFirstName("Updated John");
        updateRacerRequestDTO.setLastName("Updated Doe");

        RacerDTO updatedRacerDTO = new RacerDTO(1L, "erkan", "kamber", "plovdiv", "15-25", "test@gmail.com", "somePhoto", Expertise.BEGINNER);

        updatedRacerDTO.setRacerId(racerId);
        updatedRacerDTO.setFirstName("Updated John");
        updatedRacerDTO.setLastName("Updated Doe");
        updatedRacerDTO.setEmail("john.doe@example.com");

        when(racerService.update(updateRacerRequestDTO, racerId)).thenReturn(updatedRacerDTO);

        ResponseEntity<RacerDTO> responseEntity = racerController.updateRacer(updateRacerRequestDTO, racerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedRacerDTO, responseEntity.getBody());

        verify(racerService).update(updateRacerRequestDTO, racerId);
    }
}
