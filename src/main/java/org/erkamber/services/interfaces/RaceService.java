package org.erkamber.services.interfaces;

import org.erkamber.dtos.RaceDTO;
import org.erkamber.requestDtos.RaceRequestDTO;

import java.util.List;

public interface RaceService {

    RaceDTO save(RaceRequestDTO newRace);

    RaceDTO getById(long raceId);

    List<RaceDTO> getAllRaces();

    RaceDTO getLastRaceOfRacer(long racerId);

    List<RaceDTO> getByRacerId(long racerId);

    List<RaceDTO> deleteRacerRaces(long racerId);

    RaceDTO deleteById(long raceId);
}
