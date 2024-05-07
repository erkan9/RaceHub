package org.erkamber.dtos;

import lombok.Data;

import java.time.Duration;

@Data
public class StatisticDTO {

    private long statisticId;

    private Double drivenKms;

    private Duration drivenTimeMinutes;

    private Long racerId;
}