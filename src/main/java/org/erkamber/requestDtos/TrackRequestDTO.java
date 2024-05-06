package org.erkamber.requestDtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Duration;

@Data
public class TrackRequestDTO {

    @NotBlank(message = "Track name is required")
    private String trackName;

    @NotBlank(message = "City is required")
    private String city;

    @Positive(message = "Track length must be positive")
    private double trackLengthKms;

    @NotNull(message = "Best track time is required")
    private Duration bestTrackTime;

    private String trackPhoto;
}
