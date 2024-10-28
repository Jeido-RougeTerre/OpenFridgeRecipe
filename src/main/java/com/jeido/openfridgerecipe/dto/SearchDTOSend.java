package com.jeido.openfridgerecipe.dto;

import com.jeido.openfridgerecipe.entity.Ingredient;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchDTOSend {
    private String searchedTerm;
    private int page; // page actuelle
    private int prevPage;
    private int nextPage;
    private int pageSize; //taille de la page
    private int pageCount; // nb element de la page
    private int count; // nb de resultat
    private int totalPages; // nb de page
    private List<Ingredient> results; //liste des resultat sur la page


}
