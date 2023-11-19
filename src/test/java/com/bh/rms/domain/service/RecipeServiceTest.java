package com.bh.rms.domain.service;

import com.bh.rms.domain.aggregate.material.infra.MaterialRepository;
import com.bh.rms.domain.aggregate.recipe.Recipe;
import com.bh.rms.domain.aggregate.recipe.infra.RecipeRepository;
import com.bh.rms.domain.aggregate.recipe.service.RecipeService;
import com.bh.rms.domain.service.exception.MaterialMismatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecipeServiceTest {
    RecipeRepository recipeRepository;
    MaterialRepository materialRepository;
    RecipeService recipeService;
    @BeforeEach
    public void setup() {
        recipeRepository = mock(RecipeRepository.class);
        materialRepository = mock(MaterialRepository.class);
        recipeService = new RecipeService(recipeRepository, materialRepository);
    }

    @Test
    void createTest() {
        String createdRecipeId = recipeService.create("name", Collections.EMPTY_MAP);
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    void materialMismatchTest() {
        when(materialRepository.findByIds(anyList())).thenReturn(Collections.EMPTY_LIST);

        assertThrows(MaterialMismatchException.class, ()->{
            String createdRecipeId = recipeService.create("name", new HashMap<>(Map.of("m1", 1.0,"m2", 1.0)));
        });

        verify(materialRepository).findByIds(List.of("m1", "m2"));
    }

    @Test
    void getTest() {
        final String recipeId = "recipe-1";
        Recipe createdRecipe = recipeService.get(recipeId);
        verify(recipeRepository).findById(recipeId);
    }

//    @Test
//    public void getMaterialUnitPriceMapTest() {
//        final String recipeId = "recipe-1";
//        final List<Material> materials = List.of(new Material("").setId("m1"), new Material("").setId("m2"));
//        final List<String> materialIds = materials.stream()
//                .map(Material::getId)
//                .collect(Collectors.toList());
//
//        Recipe recipe = mock(Recipe.class);
//        when(recipeRepository.findById(recipeId)).thenReturn(recipe);
//        when(recipe.getContainedMaterialIds()).thenReturn(materialIds);
//        when(materialRepository.findByIds(materialIds)).thenReturn(materials);
//
//        RecipeService.RecipeCostResult result = recipeService.getCost(recipeId);
//
//        verify(recipe).getCost(Collections.EMPTY_MAP);
//        assertEquals(2, result.getUnknownPriceMaterials().size());
//        assertEquals("m1", result.getUnknownPriceMaterials().get(0).getId());
//        assertEquals("m2", result.getUnknownPriceMaterials().get(1).getId());
//    }

//    @Test
//    public void getUnknownPriceMaterialsTest() {
//
//        final String recipeId = "recipe-1";
//        final List<Material> materials = List.of(new Material("").setId("m1"), new Material("").setId("m2"));
//        final List<String> materialIds = materials.stream()
//                .map(Material::getId)
//                .collect(Collectors.toList());
//
//        Recipe recipe = mock(Recipe.class);
//        when(recipeRepository.findById(recipeId)).thenReturn(recipe);
//        when(recipe.getContainedMaterialIds()).thenReturn(materialIds);
//        when(materialRepository.findByIds(materialIds)).thenReturn(materials);
//
//        RecipeService.RecipeCostResult result = recipeService.getCost(recipeId);
//
//        verify(recipe).getCost(Collections.EMPTY_MAP);
//        assertEquals(materials.get(0), result.getUnknownPriceMaterials().get(0));
//        assertEquals(materials.get(1), result.getUnknownPriceMaterials().get(1));
//    }
}