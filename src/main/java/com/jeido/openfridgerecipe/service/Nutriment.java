package com.jeido.openfridgerecipe.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Nutriment {
    @JsonProperty("energy-kcal")
    private double energyKcal;
}
