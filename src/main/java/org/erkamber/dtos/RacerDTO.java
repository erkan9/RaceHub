package org.erkamber.dtos;


import lombok.Data;
import org.erkamber.enums.Expertise;

@Data
public class RacerDTO {

    private long racerId;

    private String firstName;

    private String lastName;

    private String city;

    private String ageRange;

    private String email;

    private String photo;

    private Expertise expertise;

    private StatisticDTO racerStatistics;
}