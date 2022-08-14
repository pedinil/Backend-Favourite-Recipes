package com.interview.backendfavouriterecipes.unit;


import com.interview.backendfavouriterecipes.dto.RecipesDTO;
import com.interview.backendfavouriterecipes.handler.EntityNotFoundException;
import com.interview.backendfavouriterecipes.model.RecipesModel;
import com.interview.backendfavouriterecipes.service.RecipesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RecipesServiceUnitTest {

    @Autowired
    RecipesService recipesService;

    private static RecipesDTO RECORD_1_DTO;
    private static RecipesDTO RECORD_2_DTO;
    private static RecipesDTO RECORD_3_DTO;

    private static RecipesModel RECORD_1;
    private static RecipesModel RECORD_2;
    private static RecipesModel RECORD_3;


    @BeforeAll
    public void initAll() {

        RECORD_1_DTO = new RecipesDTO();
        RECORD_1_DTO.setTitle("homemade pizza");
        RECORD_1_DTO.setAuthor("pedi");
        RECORD_1_DTO.setInstructions("Pizza dough is a yeasted dough that requires active dry yeast. Make sure the check the expiration date on the yeast package! Yeast that is too old may be dead and won't work.");
        RECORD_1_DTO.setIsVegetarian(true);
        RECORD_1_DTO.setNumberServings(1);

        RECORD_2_DTO = new RecipesDTO();
        RECORD_2_DTO.setTitle("homemade pizza2");
        RECORD_2_DTO.setAuthor("pedi");
        RECORD_2_DTO.setInstructions("Pizza dough is a yeasted dough that requires active dry yeast. Make sure the check the expiration date on the yeast package! Yeast that is too old may be dead and won't work.");
        RECORD_2_DTO.setIsVegetarian(true);
        RECORD_2_DTO.setNumberServings(1);


        RECORD_3_DTO = new RecipesDTO();
        RECORD_3_DTO.setTitle("homemade pizza3");
        RECORD_3_DTO.setAuthor("pedi");
        RECORD_3_DTO.setInstructions("Pizza dough is a yeasted dough that requires active dry yeast. Make sure the check the expiration date on the yeast package! Yeast that is too old may be dead and won't work.");
        RECORD_3_DTO.setIsVegetarian(true);
        RECORD_3_DTO.setNumberServings(1);

        RECORD_1 = recipesService.save(RECORD_1_DTO);
        RECORD_2 = recipesService.save(RECORD_2_DTO);
        RECORD_3 = recipesService.save(RECORD_3_DTO);

    }

    @Test
    public void findAll() {
        Assertions.assertFalse(recipesService.findAll().isEmpty());
        Assertions.assertEquals(recipesService.findAll().size(), 3);

    }


    @Test
    public void findAllByid() {
        RecipesModel recipesModel = recipesService.findById(RECORD_1.getId());
        Assertions.assertEquals("homemade pizza", recipesModel.getTitle());

    }


    @Test
    public void deleteById() {
        recipesService.deleteById(RECORD_1.getId());

        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            recipesService.findById(RECORD_1.getId());
        });


    }

}
