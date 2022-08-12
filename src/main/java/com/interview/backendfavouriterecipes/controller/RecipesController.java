package com.interview.backendfavouriterecipes.controller;


import com.interview.backendfavouriterecipes.model.RecipesModel;
import com.interview.backendfavouriterecipes.dto.RecipesDTO;
import com.interview.backendfavouriterecipes.service.RecipesService;
import io.swagger.v3.oas.annotations.Operation;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;


@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
@Validated
public class RecipesController {


    private final RecipesService recipesService;

    /**
     * @param recipes information need to save in DB
     * @return Saved object into database
     */
    @Operation(summary = "Register Recipes")
    @PostMapping(path = "/register")
    public ResponseEntity<RecipesModel> registerRecipes(@RequestBody RecipesDTO recipes) {
        return new ResponseEntity<RecipesModel>(recipesService.save(recipes), HttpStatus.CREATED);
    }

    /**
     * @param id Recipes id which need to be find
     * @return one record matching the Recipes ID
     * @throws EntityNotFoundException exception if id is not found
     */
    @Operation(summary = "get Recipes by ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<RecipesModel> findOneRecipes(@PathVariable String id) {
        return ResponseEntity.ok(recipesService.findById(id));
    }

    /**
     * @return Will return list of all Recipes in database
     */
    @Operation(summary = "get all Recipes")
    @GetMapping(path = "")
    public ResponseEntity<List<RecipesModel>> findAllRecipe() {
        return ResponseEntity.ok(recipesService.findAll());
    }


    /**
     * @param numberServing number of people can be serve by this recipe
     * @param ingredient    ingredient name which needed to be include
     * @return will return list of recipes contains information
     */
    @Operation(summary = "search by number of serving and ingredient")
    @GetMapping(path = "/by-number-serving/{numberServing}/include-ingredient/{ingredient}")
    public ResponseEntity<List<RecipesModel>> findAllRecipeByRecipeAndIngredient(@PathVariable Integer numberServing, @PathVariable String ingredient) {

        return ResponseEntity.ok(recipesService.findAllRecipeBynumberServingAndIngredient(numberServing, ingredient));
    }

    /**
     * @param includeIngredient   boolean value which indicate if search string needed to be include or not in Ingredient
     * @param ingredient          search string for ingredient
     * @param includeInstructions boolean value which indicate if search string needed to be include or not in Instructions
     * @param instructions        search string for instructions
     * @return
     */
    @Operation(summary = "search by instructions and ingredient ")
    @GetMapping(path = "/by-instructions-ingredient")
    public ResponseEntity<List<RecipesModel>> findAllRecipeByInstructionsAndIngredient(@RequestParam(defaultValue = "true") boolean includeIngredient,
                                                                                       @RequestParam String ingredient, @RequestParam(defaultValue = "true") boolean includeInstructions, @RequestParam String instructions) {

        return ResponseEntity.ok(recipesService.findAllRecipeByInstructionsAndIngredient(includeIngredient, ingredient, includeInstructions, instructions));
    }


    /**
     * @param id Recipes id which need to be deleted
     * @throws EntityNotFoundException exception if id is not found
     */
    @Operation(summary = "Delete Recipe by ID")
    @DeleteMapping(path = "/{id}")
    public void deleteRecipe(@PathVariable String id) {
        recipesService.deleteById(id);
    }

    /**
     * @param id      Recipes id which need to be updated
     * @param recipes EntityNotFoundException exception if id is not found
     */
    @Operation(summary = "Update Recipe by ID")
    @PutMapping(path = "/{id}")
    public void updateRecipe(@PathVariable String id, @RequestBody RecipesDTO recipes) {
        recipesService.update(id, recipes);
    }


}
