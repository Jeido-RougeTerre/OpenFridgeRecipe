package com.jeido.openfridgerecipe.service.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductSearch {

    @JsonProperty("_id")
    private String id;
}
