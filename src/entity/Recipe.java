package entity;

import java.util.*;

/**
* <p>This represents the recipes</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/
public class Recipe {
    private String dishName;
    private Map<String,Integer> ingredientMap;
    private int cookingTime;
    private int XP;
    private double moneyEarned; 

    /**
     * Create a Recipe object with specific dishname, ingredientMap, cookingTime,XP and money earned
     * @param dishName the dishname
     * @param ingredientMap ingredient name and quantity required
     * @param cookingTime time required to cook the dish
     * @param XP experience point earned from the dish
     * @param moneyEarned money earned from the dish
     */
    public Recipe(String dishName, Map<String,Integer> ingredientMap, int cookingTime,int XP, double moneyEarned){
        this.dishName = dishName;
        this.ingredientMap = ingredientMap;
        this.cookingTime = cookingTime;
        this.XP = XP;
        this.moneyEarned = moneyEarned;
    }

    /**
     * Get name
     * @return the dish name
     */
    public String getName() {
        return dishName;
    }

    /**
     * Get the ingredient and quantity
     * @return a map of ingredient the its quantity
     */

    public Map<String,Integer> getIngredientMap() {
        return ingredientMap;
    }

    /**
     * Get experience point
     * @return the XP
     */
    public int getXP() {
        return XP;
    }

    /**
     * Get money earned of the dish
     * @return the amount earned from the dish
     */
    public double getMoneyEarned() {
        return moneyEarned;
    }

    /**
     * Get cooking time of the dish
     * @return the cooking time of the dish
     */

    public int getCookingTime() {
        return cookingTime;
    }
}