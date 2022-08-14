package com.interview.backendfavouriterecipes.integration;

import com.interview.backendfavouriterecipes.model.RecipesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface H2RecipesRepository extends JpaRepository<RecipesModel, String> {


    @Query("SELECT t1 FROM RecipesModel as t1 WHERE t1.numberServings = ?1 and t1.ingredients like %?2%")
    List<RecipesModel> findAllRecipeBynumberServingAndIngredient(Integer numberServing, String ingredient);

}
