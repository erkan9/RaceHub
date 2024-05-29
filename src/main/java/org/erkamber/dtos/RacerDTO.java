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

    public RacerDTO(long racerId, String firstName, String lastName, String city, String ageRange, String email, String photo, Expertise expertise) {
        this.racerId = racerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.ageRange = ageRange;
        this.email = email;
        this.photo = photo;
        this.expertise = expertise;
    }
}