package com.interview.backendfavouriterecipes.service;

import com.interview.backendfavouriterecipes.model.RecipesModel;
import com.interview.backendfavouriterecipes.dto.RecipesDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface RecipesService {

    RecipesModel save(RecipesDTO recipesDTO);

    RecipesModel findById(String Id);

    List<RecipesModel> findAll();

    List<RecipesModel> findAllRecipeBynumberServingAndIngredient(Integer numberServing, String ingredient);

    List<RecipesModel> findAllRecipeByInstructionsAndIngredient(boolean includeIngredient, String ingredient, boolean includeInstructions, String instructions);

    void deleteById(String Id);

    RecipesModel update(String Id, RecipesDTO recipesDTO);


}
