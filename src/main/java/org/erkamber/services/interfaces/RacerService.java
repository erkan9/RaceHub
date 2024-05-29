package org.erkamber.services.interfaces;

import org.erkamber.dtos.RacerDTO;
import org.erkamber.requestDtos.RacerRequestDTO;

import java.util.List;

public interface RacerService {

    RacerDTO save(RacerRequestDTO newRacer);

    RacerDTO getById(long racerId);

    RacerDTO getByEmail(String racerEmail);

    RacerDTO getByEmailAndPassword(String email, String password);

    RacerDTO deleteById(long racerId);

    RacerDTO update(RacerRequestDTO updateRacer, long racerId);

    List<RacerDTO> getAll();
}
