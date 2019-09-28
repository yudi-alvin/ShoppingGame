package utility;

import entity.*;
import java.util.*;

/**
* <p>This class stores the cooking related data of a user that does not cover in MemberUtility class</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class CookingUtility {

    private static Map<Integer, Ingredient> ingredientMap;
    private static List<Recipe> recipeList;

    /**
     * Set all the ingredients available in the game
     * @param ingredients the ingredients and IDs in a map
     */
    public static void setIngredients(Map<Integer, Ingredient> ingredients) {
        CookingUtility.ingredientMap = ingredients;
    }

    /**
     * Get all the ingredients 
     * @return a map of ingredients and the IDs
     */
    public static Map<Integer, Ingredient> getIngredients() {
        return ingredientMap;
    }

    /**
     * Set all the recipes 
     * @param recipes a list of recipe to be set
     */
    public static void setRecipes(List<Recipe> recipes) {
        CookingUtility.recipeList = recipes;
    }

    /**
     * Get all the available recipes
     * @return a list of all the recipes available in the game
     */
    public static List<Recipe> getRecipes() {
        return recipeList;
    }

    /**
     * Get the ingrediets needed for a particular recipe 
     * @param dishName the dish name of the recipe
     * @return a map where the string is the ingredient name and integer is the quantity
     */
    public static Map<String, Integer> getRecipeIngredients(String dishName) {

        for (Recipe recipe : recipeList) {
            if (recipe.getName().equals(dishName)) {
                return recipe.getIngredientMap();
            }
        }
        return new HashMap<>();
    }

    /**
     * Get a recipe by the dishname
     * @param dishName the dishname
     * @return a recipe object
     */
    public static Recipe getRecipeByName(String dishName) {

        for (Recipe recipe : recipeList) {
            if (recipe.getName().equals(dishName)) {
                return recipe;
            }
        }
        return null;
    }

}