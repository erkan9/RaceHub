package org.erkamber.requestDtos;

import lombok.Data;
import org.erkamber.entities.Race;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.Duration;
import java.time.LocalDate;

@Data
public class LapRequestDTO {

    @NotNull
    private Duration lapTime;
}
