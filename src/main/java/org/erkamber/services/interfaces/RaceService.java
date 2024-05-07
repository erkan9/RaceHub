package org.erkamber.services.interfaces;

import org.erkamber.dtos.RaceDTO;
import org.erkamber.dtos.RacerDTO;
import org.erkamber.requestDtos.RaceRequestDTO;

import java.util.List;

public interface RaceService {

    RaceDTO save(RaceRequestDTO newRace);

    RaceDTO getById(long raceID);

    List<RaceDTO> getByUserId(long userId);

    List<RaceDTO> deleteUserRaces(long userId);

    RaceDTO deleteById(long raceId);
}
