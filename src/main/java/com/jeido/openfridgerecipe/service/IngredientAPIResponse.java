package com.jeido.openfridgerecipe.service;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientAPIResponse {

    @JsonProperty("code")
    private String code;

    @JsonProperty("product")
    private Product product;
}
