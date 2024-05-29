package controllers;

import org.erkamber.controllers.RaceController;
import org.erkamber.dtos.RaceDTO;
import org.erkamber.requestDtos.RaceRequestDTO;
import org.erkamber.services.interfaces.RaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RaceControllerTest {

    @Mock
    private RaceService raceService;

    @InjectMocks
    private RaceController raceController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveRace() {
        RaceRequestDTO newRaceRequestDTO = new RaceRequestDTO();
        RaceDTO savedRaceDTO = new RaceDTO();

        when(raceService.save(newRaceRequestDTO)).thenReturn(savedRaceDTO);

        ResponseEntity<RaceDTO> responseEntity = raceController.saveRace(newRaceRequestDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(savedRaceDTO, responseEntity.getBody());

        verify(raceService).save(newRaceRequestDTO);
    }

    @Test
    public void testGetRaceById() {
        long raceId = 1L;
        RaceDTO raceDTO = new RaceDTO();

        when(raceService.getById(raceId)).thenReturn(raceDTO);

        RaceDTO result = raceController.getRaceById(raceId);

        assertEquals(raceDTO, result);

        verify(raceService).getById(raceId);
    }

    @Test
    public void testGetLastRaceOfRacer() {
        long racerId = 1L;
        RaceDTO lastRaceDTO = new RaceDTO();

        when(raceService.getLastRaceOfRacer(racerId)).thenReturn(lastRaceDTO);

        ResponseEntity<RaceDTO> responseEntity = raceController.getLastRaceOfRacer(racerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(lastRaceDTO, responseEntity.getBody());

        verify(raceService).getLastRaceOfRacer(racerId);
    }

    @Test
    public void testGetAllRaces() {
        List<RaceDTO> raceDTOList = Arrays.asList();

        when(raceService.getAllRaces()).thenReturn(raceDTOList);

        ResponseEntity<List<RaceDTO>> responseEntity = raceController.getAllRaces();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(raceDTOList, responseEntity.getBody());

        verify(raceService).getAllRaces();
    }

    @Test
    public void testGetRacesByRacerId() {
        long racerId = 1L;
        List<RaceDTO> raceDTOList = Arrays.asList();

        when(raceService.getByRacerId(racerId)).thenReturn(raceDTOList);

        List<RaceDTO> result = raceController.getRacesByRacerId(racerId);

        assertEquals(raceDTOList, result);

        verify(raceService).getByRacerId(racerId);
    }

    @Test
    public void testDeleteRacerRaces() {
        long racerId = 1L;
        List<RaceDTO> deletedRaceDTOList = Arrays.asList();

        when(raceService.deleteRacerRaces(racerId)).thenReturn(deletedRaceDTOList);

        List<RaceDTO> result = raceController.deleteRacerRaces(racerId);

        assertEquals(deletedRaceDTOList, result);

        verify(raceService).deleteRacerRaces(racerId);
    }

    @Test
    public void testDeleteRaceById() {
        long raceId = 1L;
        RaceDTO deletedRaceDTO = new RaceDTO();

        when(raceService.deleteById(raceId)).thenReturn(deletedRaceDTO);

        RaceDTO result = raceController.deleteRaceById(raceId);

        assertEquals(deletedRaceDTO, result);

        verify(raceService).deleteById(raceId);
    }
}
