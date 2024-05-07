package org.erkamber.requestDtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RaceRequestDTO {

    @NotNull
    private long racerId;

    @NotNull
    private List<LapRequestDTO> laps;

    @NotNull
    private long kartId;

    @NotNull
    private long trackId;
}
