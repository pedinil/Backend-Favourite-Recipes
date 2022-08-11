package com.interview.backendfavouriterecipes.dto;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class RecipesDTO {

    @NotBlank
    @Size(min = 0, max = 20)
    private String title;

    @Nullable
    @Size(min = 0, max = 30)
    private String author;

    private Boolean isVegetarian;

    private Integer numberServings;

    private String ingredients;

    private String instructions;
}
