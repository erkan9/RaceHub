package controllers;

import org.erkamber.controllers.KartController;
import org.erkamber.dtos.KartDTO;
import org.erkamber.requestDtos.KartRequestDTO;
import org.erkamber.services.interfaces.KartService;
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

public class KartControllerTest {

    @Mock
    private KartService kartService;

    @InjectMocks
    private KartController kartController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveKart() {
        KartRequestDTO testKartRequestDTO = new KartRequestDTO();

        testKartRequestDTO = new KartRequestDTO();
        testKartRequestDTO.setModel("SuperKart");
        testKartRequestDTO.setHorsePower(200);
        testKartRequestDTO.setKartNumber(5);
        testKartRequestDTO.setEngineCC(250);

        KartDTO savedKartDTO = new KartDTO(1L, "Kart A");

        when(kartService.save(testKartRequestDTO)).thenReturn(savedKartDTO);

        ResponseEntity<KartDTO> responseEntity = kartController.saveKart(testKartRequestDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(savedKartDTO, responseEntity.getBody());
        assertEquals("/karts/1", responseEntity.getHeaders().getLocation().getPath());

        verify(kartService, times(1)).save(testKartRequestDTO);
    }

    @Test
    public void testGetKartById() {
        long kartId = 1L;
        KartDTO kartDTO = new KartDTO(kartId, "Kart A");

        when(kartService.getById(kartId)).thenReturn(kartDTO);

        ResponseEntity<KartDTO> responseEntity = kartController.getKartById(kartId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(kartDTO, responseEntity.getBody());

        verify(kartService, times(1)).getById(kartId);
    }

    @Test
    public void testGetAllKarts() {
        List<KartDTO> kartDTOList = Arrays.asList(
                new KartDTO(1L, "Kart A"),
                new KartDTO(2L, "Kart B")
        );

        when(kartService.getAll()).thenReturn(kartDTOList);

        ResponseEntity<List<KartDTO>> responseEntity = kartController.getAllKarts();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(kartDTOList, responseEntity.getBody());

        verify(kartService, times(1)).getAll();
    }

    @Test
    public void testUpdateKart() {
        long kartId = 1L;
        KartRequestDTO updateKartRequestDTO = new KartRequestDTO();
        updateKartRequestDTO.setModel("SuperKart");
        updateKartRequestDTO.setHorsePower(200);
        updateKartRequestDTO.setKartNumber(5);
        updateKartRequestDTO.setEngineCC(250);

        KartDTO updatedKartDTO = new KartDTO(kartId, "Updated Kart A");

        when(kartService.update(updateKartRequestDTO, kartId)).thenReturn(updatedKartDTO);

        ResponseEntity<KartDTO> responseEntity = kartController.updateKart(updateKartRequestDTO, kartId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedKartDTO, responseEntity.getBody());

        verify(kartService, times(1)).update(updateKartRequestDTO, kartId);
    }
}
