package org.erkamber.services.interfaces;

import org.erkamber.dtos.RacerDTO;
import org.erkamber.requestDtos.RacerRequestDTO;

public interface RacerService {

    RacerDTO save(RacerRequestDTO newRacer);

    RacerDTO getById(long racerId);

    RacerDTO getByEmailAndPassword(String email, String password);

    RacerDTO deleteById(long racerId);

    RacerDTO update(RacerRequestDTO updateRacer, long racerId);
}
