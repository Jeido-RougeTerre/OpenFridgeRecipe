package com.jeido.openfridgerecipe.service.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientSearchAPIResponse {

    @JsonProperty("products")
    private ProductSearch[] products;

    @JsonProperty("count")
    private int count;

    @JsonProperty("page")
    private int page;

    @JsonProperty("page_count")
    private int pageCount;

    @JsonProperty("page_size")
    private int pageSize;
}
