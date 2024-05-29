package controllers;

import org.erkamber.controllers.RankingController;
import org.erkamber.dtos.RankingDTO;
import org.erkamber.services.interfaces.RankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RankingControllerTest {

    @Mock
    private RankingService rankingService;

    @InjectMocks
    private RankingController rankingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBestLapTimes() {

        int trackId = 1;
        int kartId = 1;
        List<RankingDTO> rankingDTOs = new ArrayList<>();

        when(rankingService.getRankingByLapAndKart(trackId, kartId)).thenReturn(rankingDTOs);

        ResponseEntity<List<RankingDTO>> responseEntity = rankingController.getAllBestLapTimes(trackId, kartId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(rankingDTOs, responseEntity.getBody());


        verify(rankingService).getRankingByLapAndKart(trackId, kartId);
    }

    @Test
    public void testGetBestLastSession() {

        long trackId = 1L;
        long kartId = 1L;
        long racerId = 1L;
        RankingDTO rankingDTO = new RankingDTO();

        when(rankingService.getBestLastSession(trackId, kartId, racerId)).thenReturn(rankingDTO);

        ResponseEntity<RankingDTO> responseEntity = rankingController.getBestLastSession(trackId, kartId, racerId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(rankingDTO, responseEntity.getBody());

        verify(rankingService).getBestLastSession(trackId, kartId, racerId);
    }
}
