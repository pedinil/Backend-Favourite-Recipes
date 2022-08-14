package com.interview.backendfavouriterecipes.service;

import com.interview.backendfavouriterecipes.handler.EntityNotFoundException;
import com.interview.backendfavouriterecipes.mapper.RecipesMapper;
import com.interview.backendfavouriterecipes.model.RecipesModel;
import com.interview.backendfavouriterecipes.dto.RecipesDTO;
import com.interview.backendfavouriterecipes.repository.RecipesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class RecipesServiceImpl implements RecipesService {


    private final RecipesRepository recipesRepository;

    private final RecipesMapper recipesMapper;

    @Override
    public RecipesModel save(RecipesDTO recipesDTO) {
        RecipesModel recipesModel = recipesMapper.toRecipesModel(recipesDTO);
        return recipesRepository.save(recipesModel);
    }

    @Override
    public RecipesModel findById(String Id) {
        return recipesRepository.findById(Id).orElseThrow(() -> {
            throw new EntityNotFoundException(RecipesService.class, "id", Id);
        });
    }

    @Override
    public List<RecipesModel> findAll() {
        return recipesRepository.findAll();
    }

    @Override
    public List<RecipesModel> findAllRecipeBynumberServingAndIngredient(Integer numberServing, String ingredient) {

        List<RecipesModel> recipesModelList = recipesRepository.findAllRecipeBynumberServingAndIngredient(numberServing, ingredient);

        if (recipesModelList.isEmpty()) {
            throw new EntityNotFoundException(RecipesService.class, "numberServing", numberServing.toString(), "ingredient", ingredient);
        }

        return recipesModelList;
    }

    @Override
    public List<RecipesModel> findAllRecipeByInstructionsAndIngredient(boolean includeIngredient, String ingredient, boolean includeInstructions, String instructions) {
        List<RecipesModel> recipesModelList = recipesRepository.findAll().stream().filter(recipesModel -> {
                    boolean finalIncludeIngredient;
                    boolean finalIncludeInstructions;
                    if (includeIngredient) {
                        finalIncludeIngredient = recipesModel.getIngredients().contains(ingredient);
                    } else {
                        finalIncludeIngredient = !recipesModel.getIngredients().contains(ingredient);
                    }
                    if (includeInstructions) {
                        finalIncludeInstructions = recipesModel.getInstructions().contains(instructions);
                    } else {
                        finalIncludeInstructions = !recipesModel.getInstructions().contains(instructions);
                    }
                    return finalIncludeIngredient && finalIncludeInstructions;
                }

        ).collect(Collectors.toList());

        if (recipesModelList.isEmpty()) {
            throw new EntityNotFoundException(RecipesService.class, "includeIngredient", String.valueOf(includeIngredient), "ingredient", String.valueOf(includeInstructions), "instructions", instructions, "ingredient",ingredient);
        }

        return recipesModelList;

    }

    @Override
    public void deleteById(String Id) {
        recipesRepository.findById(Id).orElseThrow(() -> {
            throw new EntityNotFoundException(RecipesService.class, "id", Id);
        });
        recipesRepository.deleteById(Id);

    }

    @Override
    public RecipesModel update(String Id, RecipesDTO recipesDTO) {

        return recipesRepository.findById(Id).map
                        (recipes -> {
                            RecipesModel recipesModelUpdate = recipesMapper.toRecipesModel(recipesDTO);
                            recipesModelUpdate.setId(recipes.getId());
                            recipesModelUpdate.setCreatedDate(recipes.getCreatedDate());


                            return recipesRepository.save(recipesModelUpdate);
                        })
                .orElseThrow(() -> {
                    throw new EntityNotFoundException(RecipesService.class, "id", Id);
                });
    }
}
