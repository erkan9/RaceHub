package services;

import org.erkamber.dtos.KartDTO;
import org.erkamber.entities.Kart;
import org.erkamber.exceptions.ResourceNotFoundException;
import org.erkamber.repositories.KartRepository;
import org.erkamber.requestDtos.KartRequestDTO;
import org.erkamber.services.KartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class KartServiceImplTest {

    @InjectMocks
    private KartServiceImpl kartService;

    @Mock
    private KartRepository kartRepository;

    @Mock
    private ModelMapper mapper;

    private Kart testKart;
    private KartRequestDTO testKartRequestDTO;
    private KartDTO testKartDTO;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        testKart = new Kart();
        testKart.setKartId(1L);
        testKart.setModel("SuperKart");
        testKart.setHorsePower(200);
        testKart.setKartNumber(5);
        testKart.setEngineCC(250);

        testKartRequestDTO = new KartRequestDTO();
        testKartRequestDTO.setModel("SuperKart");
        testKartRequestDTO.setHorsePower(200);
        testKartRequestDTO.setKartNumber(5);
        testKartRequestDTO.setEngineCC(250);

        testKartDTO = new KartDTO(1L, "SuperKart");
        testKartDTO.setKartId(1L);
        testKartDTO.setModel("SuperKart");
        testKartDTO.setHorsePower(200);
        testKartDTO.setKartNumber(5);
        testKartDTO.setEngineCC(250);
    }

    @Test
    public void testSaveKart_Success() {
        when(mapper.map(testKartRequestDTO, Kart.class)).thenReturn(testKart);
        when(kartRepository.save(testKart)).thenReturn(testKart);
        when(mapper.map(testKart, KartDTO.class)).thenReturn(testKartDTO);

        KartDTO result = kartService.save(testKartRequestDTO);

        assertNotNull(result);
        assertEquals(testKartDTO.getKartId(), result.getKartId());
        assertEquals(testKartDTO.getModel(), result.getModel());
        assertEquals(testKartDTO.getHorsePower(), result.getHorsePower());
        assertEquals(testKartDTO.getKartNumber(), result.getKartNumber());
        assertEquals(testKartDTO.getEngineCC(), result.getEngineCC());
        verify(kartRepository).save(testKart);
        verify(mapper).map(testKartRequestDTO, Kart.class);
        verify(mapper).map(testKart, KartDTO.class);
    }

    @Test
    public void testGetKartById_Success() {
        when(kartRepository.findById(1L)).thenReturn(Optional.of(testKart));
        when(mapper.map(testKart, KartDTO.class)).thenReturn(testKartDTO);

        KartDTO result = kartService.getById(1L);

        assertNotNull(result);
        assertEquals(testKartDTO.getKartId(), result.getKartId());
        assertEquals(testKartDTO.getModel(), result.getModel());
        assertEquals(testKartDTO.getHorsePower(), result.getHorsePower());
        assertEquals(testKartDTO.getKartNumber(), result.getKartNumber());
        assertEquals(testKartDTO.getEngineCC(), result.getEngineCC());
        verify(kartRepository).findById(1L);
        verify(mapper).map(testKart, KartDTO.class);
    }

    @Test
    public void testGetKartById_NotFound() {
        when(kartRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            kartService.getById(1L);});

        assertEquals("Kart not found with id: 1", exception.getMessage());
        verify(kartRepository).findById(1L);
    }

    @Test
    public void testGetAllKarts_Success() {
        List<Kart> karts = Arrays.asList(testKart);
        when(kartRepository.findAll()).thenReturn(karts);
        when(mapper.map(testKart, KartDTO.class)).thenReturn(testKartDTO);

        List<KartDTO> result = kartService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testKartDTO.getKartId(), result.get(0).getKartId());
        assertEquals(testKartDTO.getModel(), result.get(0).getModel());
        assertEquals(testKartDTO.getHorsePower(), result.get(0).getHorsePower());
        assertEquals(testKartDTO.getKartNumber(), result.get(0).getKartNumber());
        assertEquals(testKartDTO.getEngineCC(), result.get(0).getEngineCC());
        verify(kartRepository).findAll();
        verify(mapper, times(1)).map(testKart, KartDTO.class);
    }

    @Test
    public void testUpdateKart_Success() {
        when(kartRepository.findById(1L)).thenReturn(Optional.of(testKart));
        when(kartRepository.save(testKart)).thenReturn(testKart);
        when(mapper.map(testKart, KartDTO.class)).thenReturn(testKartDTO);

        KartDTO result = kartService.update(testKartRequestDTO, 1L);

        assertNotNull(result);
        assertEquals(testKartDTO.getKartId(), result.getKartId());
        assertEquals(testKartDTO.getModel(), result.getModel());
        assertEquals(testKartDTO.getHorsePower(), result.getHorsePower());
        assertEquals(testKartDTO.getKartNumber(), result.getKartNumber());
        assertEquals(testKartDTO.getEngineCC(), result.getEngineCC());
        verify(kartRepository).findById(1L);
        verify(kartRepository).save(testKart);
        verify(mapper).map(testKart, KartDTO.class);
    }

    @Test
    public void testUpdateKart_NotFound() {
        when(kartRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            kartService.update(testKartRequestDTO, 1L);});

        assertEquals("Kart not found with id: 1", exception.getMessage());
        verify(kartRepository).findById(1L);
        verify(kartRepository, never()).save(any(Kart.class));
        verify(mapper, never()).map(any(Kart.class), eq(KartDTO.class));
    }
}
