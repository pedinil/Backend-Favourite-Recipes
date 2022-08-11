package com.interview.backendfavouriterecipes.model;

import com.interview.backendfavouriterecipes.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



@Entity
@Table(name = "t_recipes")
@Setter
@Getter
public class RecipesModel extends BaseEntity {

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
