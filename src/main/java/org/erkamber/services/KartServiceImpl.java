package org.erkamber.services;

import lombok.extern.slf4j.Slf4j;
import org.erkamber.dtos.KartDTO;
import org.erkamber.entities.Kart;
import org.erkamber.exceptions.ResourceNotFoundException;
import org.erkamber.repositories.KartRepository;
import org.erkamber.requestDtos.KartRequestDTO;
import org.erkamber.services.interfaces.KartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class KartServiceImpl implements KartService {

    private final KartRepository kartRepository;

    private final ModelMapper mapper;

    public KartServiceImpl(KartRepository kartRepository, ModelMapper mapper) {
        this.kartRepository = kartRepository;
        this.mapper = mapper;
    }

    @Override
    public KartDTO save(KartRequestDTO newKart) {

        Kart kart = mapper.map(newKart, Kart.class);

        kart = kartRepository.save(kart);

        return mapper.map(kart, KartDTO.class);
    }

    @Override
    public KartDTO getById(long kartId) {

        Kart kart = getKartById(kartId);

        return mapper.map(kart, KartDTO.class);
    }

    @Override
    public List<KartDTO> getAll() {
        List<Kart> karts = kartRepository.findAll();

        return karts.stream()
                .map(kart -> mapper.map(kart, KartDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public KartDTO update(KartRequestDTO updateKart, long kartId) {

        Kart kart = getKartById(kartId);

        if (updateKart.getModel() != null && !updateKart.getModel().isEmpty()) {
            kart.setModel(updateKart.getModel());
        }
        if (updateKart.getModel() != null && !updateKart.getModel().isEmpty()) {
            kart.setModel(updateKart.getModel());
        }
        if (updateKart.getHorsePower() > 0) {
            kart.setHorsePower(updateKart.getHorsePower());
        }
        if (updateKart.getKartNumber() > 0) {
            kart.setKartNumber(updateKart.getKartNumber());
        }
        if (updateKart.getEngineCC() > 0) {
            kart.setEngineCC(updateKart.getEngineCC());
        }

        kart = kartRepository.save(kart);

        log.info("Updated kart with id: {}", kartId);

        return mapper.map(kart, KartDTO.class);
    }

    private Kart getKartById(long kartId) {
        Optional<Kart> optionalKart = kartRepository.findById(kartId);
        return optionalKart.orElseThrow(() -> {
            String errorMessage = "Kart not found with id: " + kartId;
            log.warn(errorMessage);
            return new ResourceNotFoundException(errorMessage, "Kart");
        });
    }
}
