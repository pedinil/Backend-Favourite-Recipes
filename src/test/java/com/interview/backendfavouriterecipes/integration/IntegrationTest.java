package com.interview.backendfavouriterecipes.integration;

import com.interview.backendfavouriterecipes.dto.RecipesDTO;

import com.interview.backendfavouriterecipes.model.RecipesModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IntegrationTest {

    @LocalServerPort
    private int port;

    private static final String RECIPES_CONTROLLER_ENDPOINT = "/api/v1/recipes/";

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private H2RecipesRepository h2RecipesRepository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat(RECIPES_CONTROLLER_ENDPOINT);
    }

    @Test
    public void registerRecipes() {
        baseUrl = baseUrl.concat("/register");
        RecipesDTO recipesDTO = new RecipesDTO();
        recipesDTO.setTitle("pizza");
        recipesDTO.setAuthor("pedi");
        recipesDTO.setInstructions("Pizza dough is a yeasted dough th");
        recipesDTO.setIsVegetarian(true);
        recipesDTO.setNumberServings(1);

        RecipesModel response = restTemplate.postForObject(baseUrl, recipesDTO, RecipesModel.class);

        Assertions.assertEquals("pizza", response.getTitle());

    }

    @Test
    @Sql(statements = "DELETE FROM t_recipes", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO t_recipes (created_date, deleted, last_modified_date, author, ingredients, instructions, is_vegetarian, number_servings, title, id) " +
            " VALUES(1660377394301,false,1660377394301,'Pedram','Eggs rice Pasta','Remove pizza from all packaging and shrink wrap',false,4,'pizza','c211555c-05f4-4559-8fd1-4b26dae5ae61')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getRecipes_When_SomeRecordsExists() {

        List<RecipesModel> recipesModelList = restTemplate.getForObject(baseUrl, List.class);
        Assertions.assertEquals(1, recipesModelList.size());

    }

    @Test
    @Sql(statements = "DELETE FROM t_recipes", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO t_recipes (created_date, deleted, last_modified_date, author, ingredients, instructions, is_vegetarian, number_servings, title, id) " +
            " VALUES(1660377394301,false,1660377394301,'Pedram','Eggs rice Pasta','Remove pizza from all packaging and shrink wrap',false,4,'pizza','c211555c-05f4-4559-8fd1-4b26dae5ae61')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getRecipesById_When_RecordExists() {

        String id = "c211555c-05f4-4559-8fd1-4b26dae5ae61";

        RecipesModel response = restTemplate.getForObject(baseUrl.concat("/{id}"), RecipesModel.class, id);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(response),
                () -> Assertions.assertEquals("c211555c-05f4-4559-8fd1-4b26dae5ae61", response.getId())
        );
    }

    @Test
    @Sql(statements = "DELETE FROM t_recipes", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO t_recipes (created_date, deleted, last_modified_date, author, ingredients, instructions, is_vegetarian, number_servings, title, id) " +
            " VALUES(1660377394301,false,1660377394301,'Pedram','Eggs rice Pasta','Remove pizza from all packaging and shrink wrap',false,4,'pizza','c211555c-05f4-4559-8fd1-4b26dae5ae61')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getRecipesById_When_RecordDoesNotExists() {

        String notFoundId = "c211555c-05f4-4559-8fd1-4b26dae5ae62";

        Assertions.assertThrows(HttpClientErrorException.NotFound.class, () -> {
            restTemplate.getForObject(baseUrl.concat("/{id}"), RecipesModel.class, notFoundId);
        });
    }


    @Test
    @Sql(statements = "DELETE FROM t_recipes", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO t_recipes (created_date, deleted, last_modified_date, author, ingredients, instructions, is_vegetarian, number_servings, title, id) " +
            " VALUES(1660377394301,false,1660377394301,'Pedram','Eggs rice Pasta','Remove pizza from all packaging and shrink wrap',false,4,'pizza','c211555c-05f4-4559-8fd1-4b26dae5ae61')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void updateRecipes_When_RecordExists() {

        String Id = "c211555c-05f4-4559-8fd1-4b26dae5ae61";

        RecipesDTO recipesDTO = new RecipesDTO();
        recipesDTO.setTitle("pizza veg");
        recipesDTO.setAuthor("pedi update");
        recipesDTO.setInstructions("Pizza mixing");
        recipesDTO.setIsVegetarian(false);
        recipesDTO.setNumberServings(1);

        restTemplate.put(baseUrl.concat("/{id}"), recipesDTO, Id);

        RecipesModel recipesModel = h2RecipesRepository.findById(Id).get();

        Assertions.assertAll(
                () -> Assertions.assertNotNull(recipesModel),
                () -> Assertions.assertEquals("c211555c-05f4-4559-8fd1-4b26dae5ae61", recipesModel.getId()),
                () -> Assertions.assertEquals(recipesDTO.getTitle(), recipesModel.getTitle()),
                () -> Assertions.assertEquals(recipesDTO.getAuthor(), recipesModel.getAuthor()),
                () -> Assertions.assertEquals(recipesDTO.getIngredients(), recipesModel.getIngredients()),
                () -> Assertions.assertEquals(recipesDTO.getNumberServings(), recipesModel.getNumberServings())
        );
    }

    @Test
    @Sql(statements = "DELETE FROM t_recipes", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO t_recipes (created_date, deleted, last_modified_date, author, ingredients, instructions, is_vegetarian, number_servings, title, id) " +
            " VALUES(1660377394301,false,1660377394301,'Pedram','Eggs rice Pasta','Remove pizza from all packaging and shrink wrap',false,4,'pizza','c211555c-05f4-4559-8fd1-4b26dae5ae61')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void updateRecipes_When_RecordDoesNotExists() {
        String notFoundId = "c211555c-05f4-4559-8fd1-4b26dae5ae62";

        RecipesDTO recipesDTO = new RecipesDTO();
        recipesDTO.setTitle("pizza veg");
        recipesDTO.setAuthor("pedi update");
        recipesDTO.setInstructions("Pizza mixing");
        recipesDTO.setIsVegetarian(false);
        recipesDTO.setNumberServings(1);

        Assertions.assertThrows(HttpClientErrorException.NotFound.class, () -> {
            restTemplate.put(baseUrl.concat("/{id}"), recipesDTO, notFoundId);
        });

    }


    @Sql(statements = "INSERT INTO t_recipes (created_date, deleted, last_modified_date, author, ingredients, instructions, is_vegetarian, number_servings, title, id) " +
            " VALUES(1660377394301,false,1660377394301,'Pedram','Eggs rice Pasta','Remove pizza from all packaging and shrink wrap',false,4,'pizza','c211555c-05f4-4559-8fd1-4b26dae5ae61')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteRecipesById_When_RecordExists() {
        String id = "c211555c-05f4-4559-8fd1-4b26dae5ae61";

        Assertions.assertEquals(1, h2RecipesRepository.findAll().size());

        restTemplate.delete(baseUrl.concat("/{id}"), id);

        Assertions.assertEquals(0, h2RecipesRepository.findAll().size());

    }

    @Test
    @Sql(statements = "DELETE FROM t_recipes", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO t_recipes (created_date, deleted, last_modified_date, author, ingredients, instructions, is_vegetarian, number_servings, title, id) " +
            " VALUES(1660377394301,false,1660377394301,'Pedram','Eggs rice Pasta','Remove pizza from all packaging and shrink wrap',false,4,'pizza','c211555c-05f4-4559-8fd1-4b26dae5ae61')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void searchByNumberServing_And_Ingredient() {
        String ingredient = "rice";
        Integer numberServing = 4;

        List<RecipesModel> recipesModelList = restTemplate.getForObject(baseUrl.concat("/by-number-serving/{numberServing}/include-ingredient/{ingredient}"),
                List.class, numberServing, ingredient);

        Assertions.assertEquals(1, recipesModelList.size());
    }

    @Test
    @Sql(statements = "DELETE FROM t_recipes", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO t_recipes (created_date, deleted, last_modified_date, author, ingredients, instructions, is_vegetarian, number_servings, title, id) " +
            " VALUES(1660377394301,false,1660377394301,'Pedram','Eggs rice Pasta','Remove pizza from all packaging and shrink wrap',false,4,'pizza','c211555c-05f4-4559-8fd1-4b26dae5ae61')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void searchByNumberServing_And_Ingredient_NumberServingDoesNotExists() {
        String ingredient = "rice";
        Integer numberServing = 3;

        Assertions.assertThrows(HttpClientErrorException.NotFound.class, () -> {
            restTemplate.getForObject(baseUrl.concat("/by-number-serving/{numberServing}/include-ingredient/{ingredient}"),
                    List.class, numberServing, ingredient);
        });
    }

    @Test
    @Sql(statements = "DELETE FROM t_recipes", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO t_recipes (created_date, deleted, last_modified_date, author, ingredients, instructions, is_vegetarian, number_servings, title, id) " +
            " VALUES(1660377394301,false,1660377394301,'Pedram','Eggs rice Pasta','Remove pizza from all packaging and shrink wrap',false,4,'pizza','c211555c-05f4-4559-8fd1-4b26dae5ae61')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void searchByNumberServing_And_Ingredient_IngredientDoesNotExists() {
        String ingredient = "potato";
        Integer numberServing = 4;

        Assertions.assertThrows(HttpClientErrorException.NotFound.class, () -> {
            restTemplate.getForObject(baseUrl.concat("/by-number-serving/{numberServing}/include-ingredient/{ingredient}"),
                    List.class, numberServing, ingredient);
        });
    }


    @Test
    @Sql(statements = "DELETE FROM t_recipes", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO t_recipes (created_date, deleted, last_modified_date, author, ingredients, instructions, is_vegetarian, number_servings, title, id) " +
            " VALUES(1660377394301,false,1660377394301,'Pedram','Eggs rice Pasta','Remove pizza from all packaging and shrink wrap',false,4,'pizza','c211555c-05f4-4559-8fd1-4b26dae5ae61')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void searchBy_InstructionsIngredient_When_RecordExists() {
        Boolean includeIngredient = true;
        String ingredient = "rice";
        Boolean includeInstructions = true;
        String instructions = "shrink";


        baseUrl = baseUrl.concat("/by-instructions-ingredient?")
                .concat("includeIngredient={includeIngredient}&")
                .concat("ingredient={ingredient}&")
                .concat("includeInstructions={includeInstructions}&")
                .concat("instructions={instructions}");

        List<RecipesModel> recipesModelList = restTemplate.getForObject(baseUrl, List.class,
                includeIngredient, ingredient, includeInstructions, instructions);

        Assertions.assertEquals(1, recipesModelList.size());
    }


    @Test
    @Sql(statements = "DELETE FROM t_recipes", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO t_recipes (created_date, deleted, last_modified_date, author, ingredients, instructions, is_vegetarian, number_servings, title, id) " +
            " VALUES(1660377394301,false,1660377394301,'Pedram','Eggs rice Pasta','Remove pizza from all packaging and shrink wrap',false,4,'pizza','c211555c-05f4-4559-8fd1-4b26dae5ae61')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void searchBy_InstructionsIngredient_When_Ingredient_NotInclude_RecordExists() {
        Boolean includeIngredient = false;
        String ingredient = "potato";
        Boolean includeInstructions = true;
        String instructions = "shrink";


        baseUrl = baseUrl.concat("/by-instructions-ingredient?")
                .concat("includeIngredient={includeIngredient}&")
                .concat("ingredient={ingredient}&")
                .concat("includeInstructions={includeInstructions}&")
                .concat("instructions={instructions}");

        List<RecipesModel> recipesModelList = restTemplate.getForObject(baseUrl, List.class,
                includeIngredient, ingredient, includeInstructions, instructions);

        Assertions.assertEquals(1, recipesModelList.size());
    }

    @Test
    @Sql(statements = "DELETE FROM t_recipes", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO t_recipes (created_date, deleted, last_modified_date, author, ingredients, instructions, is_vegetarian, number_servings, title, id) " +
            " VALUES(1660377394301,false,1660377394301,'Pedram','Eggs rice Pasta','Remove pizza from all packaging and shrink wrap',false,4,'pizza','c211555c-05f4-4559-8fd1-4b26dae5ae61')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void searchBy_InstructionsIngredient_When_Instructions_NotInclude_RecordExists() {
        Boolean includeIngredient = true;
        String ingredient = "rice";
        Boolean includeInstructions = false;
        String instructions = "mix";


        baseUrl = baseUrl.concat("/by-instructions-ingredient?")
                .concat("includeIngredient={includeIngredient}&")
                .concat("ingredient={ingredient}&")
                .concat("includeInstructions={includeInstructions}&")
                .concat("instructions={instructions}");

        List<RecipesModel> recipesModelList = restTemplate.getForObject(baseUrl, List.class,
                includeIngredient, ingredient, includeInstructions, instructions);

        Assertions.assertEquals(1, recipesModelList.size());
    }


    @Test
    @Sql(statements = "DELETE FROM t_recipes", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO t_recipes (created_date, deleted, last_modified_date, author, ingredients, instructions, is_vegetarian, number_servings, title, id) " +
            " VALUES(1660377394301,false,1660377394301,'Pedram','Eggs rice Pasta','Remove pizza from all packaging and shrink wrap',false,4,'pizza','c211555c-05f4-4559-8fd1-4b26dae5ae61')"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void searchBy_InstructionsIngredient_When_Instructions_And_Ingredient_NotInclude_RecordExists() {
        Boolean includeIngredient = false;
        String ingredient = "potato";
        Boolean includeInstructions = false;
        String instructions = "mix";


        baseUrl = baseUrl.concat("/by-instructions-ingredient?")
                .concat("includeIngredient={includeIngredient}&")
                .concat("ingredient={ingredient}&")
                .concat("includeInstructions={includeInstructions}&")
                .concat("instructions={instructions}");

        List<RecipesModel> recipesModelList = restTemplate.getForObject(baseUrl, List.class,
                includeIngredient, ingredient, includeInstructions, instructions);

        Assertions.assertEquals(1, recipesModelList.size());
    }

}
