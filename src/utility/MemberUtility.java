package utility;

import entity.*;
import java.util.*;

/**
* <p>This class stores the member related data of a user</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class MemberUtility {

    private static Member member;
    private static Map<String, Integer> inventoryMap;
    private static Map<Integer, String> recipeMap;
    private static Map<Integer, Stove> stoveMap;

    //== GETTER & SETTER ==
    /**
     * Set a member
     * @param member the member to be set
     */
    public static void setMember(Member member) {
        MemberUtility.member = member;
    }
    /**
     * Get the member object set
     * @return a member object 
     */
    public static Member getMember() {
        return member;
    }

    /**
     * Set the inventory of a member
     * @param inventoryMap all the inventory in a map with ingredients and quantities
     */
    public static void setInventory(Map<String, Integer> inventoryMap) {
        MemberUtility.inventoryMap = inventoryMap;
    }

    /**
     * Get the inventory of the member
     * @return a map of the ingredients with quantities owned by the member
     */
    public static Map<String, Integer> getInventory() {
        return inventoryMap;
    }
    
    /**
     * Set the recipes learned by the member
     * @param recipeMap the recipe id and its name in a map
     */
    public static void setMemberRecipe(Map<Integer, String> recipeMap) {
        MemberUtility.recipeMap = recipeMap;
    }

    /**
     * Get recipe by its id
     * @param recipeNum the id of the recipe
     * @return a recipe with the input recipe id
     */
    public static Recipe getRecipeByNum(int recipeNum) {
        String dishName = recipeMap.get(recipeNum);
        return CookingUtility.getRecipeByName(dishName);
    }
    /**
     * Get the recipes owned by the member
     * @return a map of recipe id and the dishname
     */
    public static Map<Integer, String> getRecipes() {
        return recipeMap;
    }

    /**
     * Set the stoves for a member
     * @param stoveMap a map of stove id and the stove
     */
    public static void setStoves(Map<Integer, Stove> stoveMap) {
        MemberUtility.stoveMap = stoveMap;
    }

    /**
     * Get the stoves in a map
     * @return a map of stove id and stove
     */
    public static Map<Integer, Stove> getStoves() {
        return stoveMap;
    }

    /**
     * Add an ingredient with specified quantity to the member's cart
     * @param cartMap the shopping cart with ingredients and quantity for a member
     * @param ingredient the ingredient
     * @param quantity the quantity of the ingredient
     */
    public static void addCartIngredient(Map<Ingredient, Integer> cartMap, Ingredient ingredient, int quantity){

        if (cartMap.containsKey(ingredient)){
            quantity += cartMap.get(ingredient);
        } 
        cartMap.put(ingredient, quantity);
    }    

    /**
     * Gets total price of the cart
     * @param cartMap the shopping cart with ingredients and quantity for a member
     * @return the total price of the cart 
     */
    public static double getCartPrice(Map<Ingredient, Integer> cartMap) {
        double totalPrice = 0;
        for (Ingredient ingredient: cartMap.keySet()) {
            int quantity = cartMap.get(ingredient);
            totalPrice += ingredient.getPrice() * quantity;
        }
        return totalPrice;
    }

    /**
     * Display the cart
     * @param cartMap map of ingredients and quantities inside
     */
    public static void displayCart(Map<Ingredient, Integer> cartMap) {
        //== Header ==
        System.out.println("My Cart(Total: $" +  String.format("%.2f", getCartPrice(cartMap))+ ")");
        //if cart is empty
        if (cartMap.isEmpty()) {
            System.out.println("- NIL");
        } 
        else {
            int index = 1;
            for (Ingredient ingredient: cartMap.keySet()) {
                int quantity = cartMap.get(ingredient);
                double price = ingredient.getPrice();
                System.out.printf("%2d. %-6s (%d x $%.2f = $%.2f)%n", index, ingredient.getName(), quantity, price, price*quantity);
                index++;
            }
        }
    }

    /**
     * Add ingredients into member's inventoryMap
     * @param cart map of ingredients and quantities inside
     */
    public static void checkoutCart(Map<Ingredient, Integer> cart) {

        for (Ingredient ingredient: cart.keySet()) {
            String ingredientName = ingredient.getName();
            int quantity = cart.get(ingredient);

            //adds existing quantity to the quantity
            if (inventoryMap.containsKey(ingredientName)) {
                quantity += inventoryMap.get(ingredientName);
            }

            //adds into inventoryMap;
            inventoryMap.put(ingredientName, quantity); 
        }
    }

    /**
     * Remove the ingredients with the corresponding quantities to be used from the member's inventory
     *@param ingredientMap the ingredients with their corresponding quantities
     */
    public static void useIngredients(Map<String, Integer> ingredientMap) {
        for (String name: ingredientMap.keySet()) {
            int inventoryQty = inventoryMap.get(name);
            int newQty = inventoryQty - ingredientMap.get(name);

            if (newQty == 0) {
                inventoryMap.remove(name);
            } else {
                inventoryMap.put(name, newQty);
            }
        }
    }

    /**
     * Display welcome message with balance when entered
     */
    public static void displayWelcome() {
        //display welcome message
        //E.g. "Welcome, Novice Kenny (Balance: $7.20)"
        String title = member.getTitle();
        String fullName = member.getFullName();
        double balance = member.getBalance();
        System.out.println("Welcome, " + title + " " + fullName + " (Balance: $" + String.format("%.2f", balance) + ")");
    }


}