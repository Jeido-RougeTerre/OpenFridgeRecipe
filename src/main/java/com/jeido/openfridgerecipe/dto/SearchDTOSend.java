package com.jeido.openfridgerecipe.dto;

import com.jeido.openfridgerecipe.entity.Ingredient;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchDTOSend {
    private String searchedTerm;
    private int page;
    private int prevPage;
    private int nextPage;
    private int pageSize;
    private int count;
    private List<Ingredient> results;


}
