package com.jeido.openfridgerecipe.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Product {

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("quantity")
    private String quantity;

    @JsonProperty("nutriments")
    private Nutriment nutriments;
}
