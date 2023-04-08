package com.hojun.service.domain.cost;

import java.util.Collections;
import java.util.List;

public record RecipePrice(int price, List<Material> unknownMaterials) {
    public final static RecipePrice EMPTY_RECIPE_PRICE = new RecipePrice(0, Collections.emptyList());
}
