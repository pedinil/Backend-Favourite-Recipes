package com.interview.backendfavouriterecipes.unit;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.interview.backendfavouriterecipes.controller.RecipesController;
import com.interview.backendfavouriterecipes.dto.RecipesDTO;
import com.interview.backendfavouriterecipes.mapper.RecipesMapper;
import com.interview.backendfavouriterecipes.model.RecipesModel;
import com.interview.backendfavouriterecipes.service.RecipesService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(RecipesController.class)
public class RecipesControllerUnitTest {


    private static final String RECIPES_CONTROLLER_ENDPOINT = "/api/v1/recipes/";

    private static RecipesModel RECORD_1;
    private static RecipesModel RECORD_2;
    private static RecipesModel RECORD_3;

    ObjectMapper objectMapper= new ObjectMapper();
    ObjectWriter objectWriter=objectMapper.writer();

    @MockBean
    RecipesService recipesService;

    @MockBean
    RecipesMapper recipesMapper;

    @Autowired
    MockMvc mockMvc;


    @BeforeAll
    public static void initAll() {

        RECORD_1 = new RecipesModel();
        RECORD_1.setId("d25046a0-1a3d-11ed-861d-0242ac120002");
        RECORD_1.setTitle("homemade pizza");
        RECORD_1.setAuthor("pedi");
        RECORD_1.setInstructions("Pizza dough is a yeasted dough that requires active dry yeast. Make sure the check the expiration date on the yeast package! Yeast that is too old may be dead and won't work.");
        RECORD_1.setIsVegetarian(true);
        RECORD_1.setNumberServings(1);

        RECORD_2 = new RecipesModel();
        RECORD_2.setTitle("homemade pizza");
        RECORD_2.setAuthor("pedi");
        RECORD_2.setInstructions("Pizza dough is a yeasted dough that requires active dry yeast. Make sure the check the expiration date on the yeast package! Yeast that is too old may be dead and won't work.");
        RECORD_2.setIsVegetarian(true);
        RECORD_2.setNumberServings(1);


        RECORD_3 = new RecipesModel();
        RECORD_3.setTitle("homemade pizza");
        RECORD_3.setAuthor("pedi");
        RECORD_3.setInstructions("Pizza dough is a yeasted dough that requires active dry yeast. Make sure the check the expiration date on the yeast package! Yeast that is too old may be dead and won't work.");
        RECORD_3.setIsVegetarian(true);
        RECORD_3.setNumberServings(1);
    }

    @Test
    public void findById_WhenIdFound_StatusOk() throws Exception {

        Mockito.when(recipesService.findById("d25046a0-1a3d-11ed-861d-0242ac120002")).thenReturn(RECORD_1);

        mockMvc.perform(get(RECIPES_CONTROLLER_ENDPOINT + "d25046a0-1a3d-11ed-861d-0242ac120002")).andExpect(status().isOk());


    }

    @Test
    public void findAllRecipe_AllFound_StatusOk() throws Exception {


        List<RecipesModel> recipesModelList = Arrays.asList(RECORD_1, RECORD_2, RECORD_3);

        Mockito.when(recipesService.findAll()).thenReturn(recipesModelList);

        mockMvc.perform(get(RECIPES_CONTROLLER_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$[0].title", Matchers.is("homemade pizza")));

    }

    @Test
    public void deleteRecipe_WhenIdExits_StatusOk() throws Exception {
        Mockito.when(recipesService.findById(RECORD_1.getId())).thenReturn(RECORD_1);

        mockMvc.perform(delete(RECIPES_CONTROLLER_ENDPOINT + RECORD_1.getId())).andExpect(status().isOk());

    }


    @Test
    public void createRecord_success() throws Exception {

        RecipesDTO recipesDTO = new RecipesDTO();
        recipesDTO.setTitle("pizza");
        recipesDTO.setAuthor("pedi");
        recipesDTO.setInstructions("Pizza dough is a yeasted dough th");
        recipesDTO.setIsVegetarian(true);
        recipesDTO.setNumberServings(1);

        RecipesModel recipesModel=recipesMapper.toRecipesModel(recipesDTO);

        Mockito.when(recipesService.save(recipesDTO)).thenReturn(recipesModel);

        String content= objectWriter.writeValueAsString(RECORD_1);

        mockMvc.perform(post(RECIPES_CONTROLLER_ENDPOINT+"register").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content)).andExpect(status().isCreated());

    }


}
