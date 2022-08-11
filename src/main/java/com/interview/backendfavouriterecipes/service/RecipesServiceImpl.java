package com.interview.backendfavouriterecipes.service;

import com.interview.backendfavouriterecipes.handler.EntityNotFoundException;
import com.interview.backendfavouriterecipes.mapper.RecipesMapper;
import com.interview.backendfavouriterecipes.model.RecipesModel;
import com.interview.backendfavouriterecipes.dto.RecipesDTO;
import com.interview.backendfavouriterecipes.repository.RecipesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
public class RecipesServiceImpl implements RecipesService{



    private final RecipesRepository recipesRepository;

    private final RecipesMapper recipesMapper;


    @Override
    public RecipesModel save(RecipesDTO recipesDTO) {
     RecipesModel recipesModel  = recipesMapper.toRecipesModel(recipesDTO);
       return recipesRepository.save(recipesModel);
    }

    @Override
    public RecipesModel findById(String Id) {
      return  recipesRepository.findById(Id).orElseThrow(() -> {throw new EntityNotFoundException(RecipesService.class,"id",Id);});
    }

    @Override
    public void deleteById(String Id) {
        recipesRepository.findById(Id).orElseThrow(() -> {throw new EntityNotFoundException(RecipesService.class,"id",Id);});
        recipesRepository.deleteById(Id);
    }

    @Override
    public RecipesModel update(String Id, RecipesDTO recipesDTO) {

      return  recipesRepository.findById(Id).map
              (recipes -> {
                RecipesModel recipesModelUpdate  = recipesMapper.toRecipesModel(recipesDTO);
                recipesModelUpdate.setId(recipes.getId());
                recipesModelUpdate.setCreatedDate(recipes.getCreatedDate());

                return recipesRepository.save(recipesModelUpdate);
              } )
                .orElseThrow(() -> {throw new EntityNotFoundException(RecipesService.class,"id",Id);});
    }
}
