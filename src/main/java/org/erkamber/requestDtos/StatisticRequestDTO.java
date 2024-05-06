package org.erkamber.requestDtos;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class StatisticRequestDTO {

    @NotNull(message = "Driven kilometers cannot be null")
    @Min(value = 0, message = "Driven kilometers must be greater than or equal to 0")
    private Double drivenKms;

    @NotNull(message = "Driven time in minutes cannot be null")
    @Min(value = 0, message = "Driven time in minutes must be greater than or equal to 0")
    private Integer drivenTimeMinutes;
}
