package controller;


import entity.*;
import dao.*;
import utility.*;
import java.util.*;
/**
* <p>This controller class processes user's request for learning new recipes</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class TrainingCtrl {
    private Member member;
    // private Map<Character, String> selectionChar;
    private Map<String, Integer> selectionMap;
    //indexInventory stores the char as the key and member's inventory ingredient names as values.
    private Map<Character, String> indexInventory;
    private Map<Integer, String> memberRecipe;
    private RecipeDAO recipeDAO;

    /**
     * Create a default TrainingCtrl object 
     */
    public TrainingCtrl() {
        recipeDAO = new RecipeDAO();
        member = MemberUtility.getMember();
        selectionMap = new HashMap<>();
        indexInventory = new HashMap<>();
        memberRecipe = MemberUtility.getRecipes();
    }

    /**
     * Display the welcome message in the training centre menu
     */
    public void displayMenu() {
        MemberUtility.displayWelcome();
        System.out.println();
        displaySelection();
        System.out.println(); 
        displayInventory();
    }

    /**
     * Display the selected ingredients with quantity by the members
     */
    public void displaySelection() {
        System.out.println("Selected Ingredients: ");
        if (selectionMap.isEmpty()) {
            System.out.println("- NIL");
        }
        else {
            int index = 65;
            for (String name : selectionMap.keySet()) {
                int quantity = selectionMap.get(name);
                System.out.println((char)index + ". " + quantity + " x " + name);
                index++;
            }
        }
    }

     /**
     * Display all the learned recipes by the member; otherwise display "No learnt recipes available".
     */
    public void displayRecipes() {
        if (memberRecipe.isEmpty()) {
            System.out.println("You have not learnt any recipes.");
        } else {
            for (int count: memberRecipe.keySet()) {
                char index = (char)(count+64);
                String dishName = memberRecipe.get(count);
                System.out.println(index + ". " + dishName);
            }
        }
    }

    /**
     * Display member's inventory if any; otherwise prints "- NIL".
     */
    public void displayInventory() {
        System.out.println("Available Ingredients: ");
        Map<String, Integer> memberInventory = MemberUtility.getInventory();
        if (memberInventory.isEmpty()) {
            System.out.println("- NIL");
        } 
        else {
            //index count to get the ASCII char
            int indexCount = 65;
            for (String ingredient: memberInventory.keySet()) {
                char index = (char) indexCount;
                //input into indexInventory map
                indexInventory.put(index, ingredient);
                indexCount++;
    
                //printing out the display
                int quantity = memberInventory.get(ingredient);
                System.out.println(index + ". " + quantity + " x " + ingredient);
            }
        }
    }

    /**
    * Check ingredients needed for the selected recipe
    * @param input this is the String input by the member
    * @return a boolean of whether it is successful
    */
    public boolean checkRecipeIngredient(String input) {
        if (input.length() > 1) {
            return false;
        }
        char index = input.charAt(0);
        int recipeIndex = (int)index - 64;
        
        if (!(memberRecipe.containsKey(recipeIndex))) {
            System.out.println("false");;
            return false;
        }
        String dishName = memberRecipe.get(recipeIndex);
        Map<String, Integer> ingredientsMap = CookingUtility.getRecipeIngredients(dishName);

        int count = 65;
        System.out.println("\nIngredients needed for " + dishName + ":");
        for (String ingredientName : ingredientsMap.keySet()) {
            int quantity = ingredientsMap.get(ingredientName);
            System.out.println((char)count + ". " + quantity + " x " + ingredientName);
            count++;
        }

        return true;
    }

    /**
    * Check if input by the member is valid 
    * @param input this is the string input by the user
    * @return a boolean of the input's validity
    */
    public boolean validInput(String input) {
        int inputLength = input.length();
        //====== CHECK IF INPUT IS VALID =====
        if (inputLength <= 1 || indexInventory.isEmpty()) {
            return false;
        }
        char lastChar = input.charAt(inputLength - 1);
        if (!(indexInventory.containsKey(lastChar))) {
            return false;
        }

        try {
            //CHECK IF FRONT PART IS AN INTEGER
            String inputNum = input.substring(0, input.length() - 1); 
            int quantity = Integer.parseInt(inputNum);
            if (quantity == 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
    
    /**
    * Method to select / remove ingredients to selection
    * @param input this is the string input by the user
    * @return a boolean of the user's input validity, prompt user for valid input if false
    */
    public boolean selectIngredient(String input) {
        //checking for valid input
        if (!validInput(input)) {
            return false;
        }

        char selectedChar = input.charAt(input.length() -  1);
        String inputNum = input.substring(0, input.length() - 1); 
        int quantity = Integer.parseInt(inputNum);

        //if qty > 0 means that the user is adding ingredients into his selection
        if (quantity > 0) {
            addSelection(selectedChar, quantity);
            return true;
        } 
        
        //else the user is removing ingredients from his selection
        else {
            //returns false if selection is empty
            if (selectionMap.isEmpty()) {
                return false;
            }
            return removeSelection(selectedChar, -quantity);
        }
    }

    /**
    * Add selected ingredient into the member's selection
    * @param selection this is the index of the ingredients available (member's inventory) 
    * @param quantity this is the quantity to be added into the selection
    */
    public void addSelection(char selection, int quantity) {
        String ingredientName = indexInventory.get(selection);
        Map<String, Integer> memberInventory = MemberUtility.getInventory();

        int newQuantity = quantity;

        //checks if selected ingredient already exists in the existing solution
        if (selectionMap.containsKey(ingredientName)) {
            newQuantity += selectionMap.get(ingredientName);
        }

        //if member selects a higher quantity than what is available.
        if (newQuantity > memberInventory.get(ingredientName)) {
            //prints a more specific error message
            System.out.println("Insufficient quantity of " + ingredientName + ".");
        } 
        else {
            String grammar = " are";    
            if (quantity == 1) {
                grammar = " is";
            }
            System.out.println(quantity + " " + ingredientName + grammar + " added.");
            selectionMap.put(ingredientName, newQuantity);     
        }
    }
    /**
     * Remove selected item from current selection
     * @param selection the index representing the ingredient selected
     * @param quantity  the quantity of the removed ingredient
     * @return true if it is successfully removed; otherwise return false
     */

    public boolean removeSelection(char selection, int quantity) {
        //returns false if selected ingredients does not contains the Char
        int selectedIndex = (int)selection - 64;
        if (selectionMap.size() < selectedIndex) {
            return false;
        }

        /**
         * 
         */
        int index = 1;
        for (String name: selectionMap.keySet()) {
            if (index == selectedIndex) {
                int checkQty = selectionMap.get(name);
                if (checkQty < quantity) {
                    System.out.println("Insufficient " + name + " to remove from selection.");
                    return true;
                }
                else if (checkQty == quantity) {
                    System.out.println("hi");
                    selectionMap.remove(name);
                }
                else {
                    selectionMap.put(name, checkQty - quantity);
                }
                System.out.println(quantity + " " + name + " removed from selection.");
                return true;
            }
            index++;
        }
        return true;
    }

    /**
     * Learn a new recipe by the member and add that recipe to the member recipe list
     * @return true if the member successfully learns a new recipe; otherwise return false. 
     */
    public boolean learnRecipe() {
        if (selectionMap.isEmpty()) {
            System.out.println("You have no selected ingredients to learn a new recipe.");
            return false;
        }

        List<Recipe> totalRecipes = CookingUtility.getRecipes();
        //loop through all the recipes available
        for (Recipe recipe: totalRecipes) {
            Map<String, Integer> recipeIngredients= recipe.getIngredientMap();
            if (recipeIngredients.size() == selectionMap.size()) {
                int count = 0;
                for (String name: recipeIngredients.keySet()) {
                    if (recipeIngredients.get(name) == selectionMap.get(name)) {
                        count ++;
                    }
                }

                if (count == selectionMap.size()) {
                    String recipeName = recipe.getName();
                    if (memberRecipe.containsValue(recipeName)) {
                        System.out.println("You have already learnt how to cook " + recipeName);
                    }
                    else {
                        //Add's newly learnt recipe into the database
                        recipeDAO.learnRecipe(member.getUsername(), recipeName);

                        //
                        memberRecipe.put(memberRecipe.size()+1, recipeName);
                        System.out.println("You have successfully learnt the " + recipeName + " recipe.");
                        selectionMap.clear();
                    }
                    return true;
                }
            }
        }
        System.out.println("Selections do not match any existing recipes");
        return false;
    }
}