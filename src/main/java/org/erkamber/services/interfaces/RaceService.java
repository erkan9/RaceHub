package org.erkamber.services.interfaces;

import org.erkamber.dtos.RaceDTO;
import org.erkamber.requestDtos.RaceRequestDTO;

import java.util.List;

public interface RaceService {

    RaceDTO save(RaceRequestDTO newRace);

    RaceDTO getById(long raceID);

    List<RaceDTO> getByUserId(long racerId);

    List<RaceDTO> deleteUserRaces(long racerId);

    RaceDTO deleteById(long raceId);
}
