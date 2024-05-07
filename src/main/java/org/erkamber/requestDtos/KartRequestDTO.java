package org.erkamber.requestDtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class KartRequestDTO {

    @NotEmpty(message = "Model cannot be empty")
    private String model;

    @Positive(message = "Horse power must be positive")
    private double horsePower;

    private int kartNumber;

    @Positive(message = "Engine CC must be positive")
    private double engineCC;

    @NotNull
    @NotEmpty
    @NotBlank
    private String kartPhoto;
}
