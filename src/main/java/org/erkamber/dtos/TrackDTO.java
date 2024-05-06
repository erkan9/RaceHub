package org.erkamber.dtos;

import lombok.Data;

import java.time.Duration;


@Data
public class TrackDTO {

    private long trackId;

    private String trackName;

    private String city;

    private double trackLengthKms;

    private Duration bestTrackTime;

    private String trackPhoto;
}