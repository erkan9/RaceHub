package controllers;

import org.erkamber.controllers.TrackController;
import org.erkamber.dtos.TrackDTO;
import org.erkamber.requestDtos.TrackRequestDTO;
import org.erkamber.services.interfaces.TrackService;
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

public class TrackControllerTest {

    @Mock
    private TrackService trackService;

    @InjectMocks
    private TrackController trackController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveTrack() {
        TrackRequestDTO newTrackRequestDTO = new TrackRequestDTO();
        TrackDTO savedTrackDTO = new TrackDTO();

        when(trackService.save(newTrackRequestDTO)).thenReturn(savedTrackDTO);

        ResponseEntity<TrackDTO> responseEntity = trackController.saveTrack(newTrackRequestDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(savedTrackDTO, responseEntity.getBody());

        verify(trackService).save(newTrackRequestDTO);
    }

    @Test
    public void testGetTrackById() {
        long trackId = 1L;
        TrackDTO trackDTO = new TrackDTO();

        when(trackService.getById(trackId)).thenReturn(trackDTO);

        ResponseEntity<TrackDTO> responseEntity = trackController.getTrackById(trackId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(trackDTO, responseEntity.getBody());

        verify(trackService).getById(trackId);
    }

    @Test
    public void testGetAllTracks() {
        List<TrackDTO> trackDTOList = Arrays.asList();

        when(trackService.getAll()).thenReturn(trackDTOList);

        ResponseEntity<List<TrackDTO>> responseEntity = trackController.getAllTracks();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(trackDTOList, responseEntity.getBody());

        verify(trackService).getAll();
    }

    @Test
    public void testGetMostPreferredTrack() {
        long racerId = 1L;
        TrackDTO preferredTrackDTO = new TrackDTO();

        when(trackService.findMostPreferredTrackForRacer(racerId)).thenReturn(preferredTrackDTO);

        ResponseEntity<TrackDTO> responseEntity = trackController.getMostPreferredTrack(racerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(preferredTrackDTO, responseEntity.getBody());

        verify(trackService).findMostPreferredTrackForRacer(racerId);
    }

    @Test
    public void testUpdateTrack() {
        long trackId = 1L;
        TrackRequestDTO updateTrackRequestDTO = new TrackRequestDTO();
        TrackDTO updatedTrackDTO = new TrackDTO();

        when(trackService.updateTrack(updateTrackRequestDTO, trackId)).thenReturn(updatedTrackDTO);

        ResponseEntity<TrackDTO> responseEntity = trackController.updateTrack(updateTrackRequestDTO, trackId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedTrackDTO, responseEntity.getBody());

        verify(trackService).updateTrack(updateTrackRequestDTO, trackId);
    }
}
