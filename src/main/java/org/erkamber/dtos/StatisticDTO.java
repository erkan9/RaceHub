package org.erkamber.dtos;

import lombok.Data;

@Data
public class StatisticDTO {

    private long statisticId;

    private Double drivenKms;

    private Integer drivenTimeMinutes;

    private Long racerId;
}