package org.erkamber.dtos;

import lombok.Data;

import java.util.List;

@Data
public class RaceDTO {

    private long raceId;

    private RacerDTO racer;

    private List<LapDTO> laps;

    private KartDTO raceKart;

    private TrackDTO track;
}
