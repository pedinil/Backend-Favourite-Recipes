package com.interview.backendfavouriterecipes.service;

import com.interview.backendfavouriterecipes.model.RecipesModel;
import com.interview.backendfavouriterecipes.dto.RecipesDTO;

public interface RecipesService {

     RecipesModel save(RecipesDTO recipesDTO);

     RecipesModel findById(String Id);

     void deleteById(String Id);

     RecipesModel update(String Id,RecipesDTO recipesDTO);


}
