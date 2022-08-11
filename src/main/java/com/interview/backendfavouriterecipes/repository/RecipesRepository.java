package com.interview.backendfavouriterecipes.repository;

import com.interview.backendfavouriterecipes.model.RecipesModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecipesRepository extends JpaRepository<RecipesModel,String> {
}
