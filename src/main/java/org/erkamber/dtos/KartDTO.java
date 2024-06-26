package org.erkamber.dtos;

import lombok.Data;

@Data
public class KartDTO {

    private long kartId;

    private String model;

    private double horsePower;

    private int kartNumber;

    private double engineCC;

    private String kartPhoto;

    public KartDTO() {
    }

    public KartDTO(long kartId, String model) {
        this.kartId = kartId;
        this.model = model;
    }
}
