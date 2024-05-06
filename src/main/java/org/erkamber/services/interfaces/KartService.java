package org.erkamber.services.interfaces;

import org.erkamber.dtos.KartDTO;
import org.erkamber.requestDtos.KartRequestDTO;

import java.util.List;

public interface KartService {

    KartDTO save(KartRequestDTO newKart);

    KartDTO getById(long kartId);

    List<KartDTO> getAll();

    KartDTO update(KartRequestDTO updateKart, long kartId);
}
