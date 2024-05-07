package org.erkamber.dtos;

import lombok.Data;
import org.erkamber.enums.Expertise;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class RankingDTO {

    private int position;

    private String racerPhoto;

    private String racerFirstName;

    private String racerLastName;

    private LocalDate raceDate;

    private Duration bestTime;
}
