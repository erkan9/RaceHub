package org.erkamber.dtos;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class LapDTO {

    private int lapId;

    private Duration lapTime;

    private boolean isBestTime;

    private LocalDate lapDate;
}
