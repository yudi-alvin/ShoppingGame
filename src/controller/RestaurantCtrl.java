package controller;

import entity.*;
import dao.*;
import utility.*;
import java.util.*;


/**
* <p>This controller class processes user's request for Restaurant</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class RestaurantCtrl{
    private Member member;
    private Map<Integer, Stove> stoveMap;
    private StoveDAO stoveDAO;
    private MemberDAO memberDAO;

    /**
     * Create a default RestaurantCtrl object
     */
    public RestaurantCtrl(){
        member = MemberUtility.getMember();
        stoveMap = MemberUtility.getStoves();
        stoveDAO = new StoveDAO();
        memberDAO = new MemberDAO();
    }
    
    /**
     * To serve the dish cooked on stove and add XP and money earned if any
     */

    // Serve all 100% cooked dishes
    public void serve() {
        int xp = 0;
        double moneyEarned = 0;
        int count = 0;
        for(int stoveid = 1; stoveid < 4; stoveid++) {
            Stove stove = stoveMap.get(stoveid);
            if (stove.getStatus() == 2) {
                count++;
                String dishName = stove.getDishName();
                Recipe recipe = CookingUtility.getRecipeByName(dishName);

                // Retrieve XP and balance from recipe
                xp += recipe.getXP();
                moneyEarned += recipe.getMoneyEarned();

                // Wipe and update stove in the database
                stove.wipeStove();
                stoveDAO.updateStove(stove, member.getUsername());

            }
        }
        if (count == 0){
            System.out.println("There are no dishes ready to be served!");
        } else {

            // Add XP and moneyEarned to the member
            member.addXP(xp);
            member.addBalance(moneyEarned);
            
            // Add XP and moneyEarned to the database
            String username = member.getUsername();
            int newXP = member.getXP();
            double newBalance = member.getBalance();
            memberDAO.setXP(username, newXP);
            memberDAO.setBalance(username, newBalance);

            // Check if member has uprank after serving food
            member.updateTitle(memberDAO.getTitle(username));

            System.out.println("You have earned " + xp + " XP and $" + String.format("%.2f", moneyEarned) + ".");
        }
    }

    /**
     * To wipe all spoilt stoves and deduct $1 for each dirty stove wiped. If member has no ingredients and no clean stoves, heaven will pity him and no money will be deducted.
     */
    public void wipe() {
        ArrayList<Integer> countList = new ArrayList<>();
        for(int stoveid = 1; stoveid < 4; stoveid++) {
            Stove stove = stoveMap.get(stoveid);
            int status = stove.getStatus();
            if (status == 3 || status == 4) {
                // Wipe the stove if it is dirty or eaten
                stove.wipeStove();
                stoveDAO.updateStove(stove, member.getUsername());
                countList.add(stove.getStoveID());
            }
        }
        // Member will not need to pay money
        if (countList.size() == 3 && MemberUtility.getInventory().size() == 0 && member.getBalance() < 3) {
            System.out.print("Heaven pity you.");
        
        // Deducting money for stoves that were wiped
        } else if (countList.size() != 0) {
            // $1 for every stove that is wiped
            int numDirtyStoves = countList.size();
            String username = member.getUsername();

            // Deducting money from member
            member.deductBalance(numDirtyStoves);
            double currentBalance = member.getBalance();
            
            // Updating new balance in database
            memberDAO.setBalance(username, currentBalance);
            
            // Printing of success message
            System.out.print("$" + numDirtyStoves + " has been deducted from your account.");
            if (countList.size() == 1){
                System.out.println(" Stove " + countList.get(0) + " has been cleaned.");
            } else if (countList.size() == 3){
                System.out.println(" Stove 1-3 have been cleaned.");
            } else {
                int firstStove = countList.get(0);
                int secondStove = countList.get(1);
                System.out.println(" Stove " + firstStove + " and " + secondStove + " have been cleaned.");
            }
        // No stoves to wipe
        } else {
            System.out.println("There are no dirty stoves.");
        }

    }
    /**
     * To cook a new dish on the available stove and deduct ingredients from the member inventory
     * @param recipeNum the recipe id
     * @return true if the dish starts to cook; otherwise return false if there is not enough ingredients in the member inventory or there are no available stoves.
     */
    public boolean cook(int recipeNum) { 
        if (recipeNum < 1 || !(MemberUtility.getRecipes().containsKey(recipeNum))) {
            System.out.println("Invalid input");
            return false;
        }
        Recipe recipe = MemberUtility.getRecipeByNum(recipeNum);
        for(int stoveid = 1; stoveid < 4; stoveid++) {
            Stove stove = stoveMap.get(stoveid);
            // If there is an empty stove, use it for cooking
            if (stove.getStatus() == 0) {
                // Checking whether user has enough ingredients to cook selected dish
                Map<String, Integer> ingredientMap = recipe.getIngredientMap();               
                for (String ingredientName: ingredientMap.keySet()) {
                    int quantity = ingredientMap.get(ingredientName);

                    if (!checkEnoughIngredients(ingredientName, quantity)) {
                        System.out.println("You do not have enough ingredients to cook the selected recipe.");
                        return false;
                    }
                }

                // If enough ingredients, cook recipe
            
                stove.cook(recipe);
                stoveDAO.updateStove(stove, member.getUsername());
                System.out.println(recipe.getName() + " has started cooking.");
                
                memberDAO.useIngredient(ingredientMap, member.getUsername());
                MemberUtility.useIngredients(ingredientMap);
                return true;
            }
        }
        System.out.println("You have no stoves available for cooking.");
        return false;
        
    }
    /**
     * Display recipes learned by the member if any
     * @return a boolean of whether there is recipe owned by the member
     */

    // Displays recipes member has learnt
    public boolean displayRecipes() {
        Map<Integer, String> recipeMap = MemberUtility.getRecipes();
        if (recipeMap.size() == 0){
            System.out.println("You have not learnt any recipes.");
            return false;
        } else {
            System.out.println("Recipes:");
            for (int index: recipeMap.keySet()){
                System.out.print(index + ". ");
                System.out.println(recipeMap.get(index));
            }
            return true;
        }
    }

    /** 
     * Displays all the stoves by a member
     */
    public void displayStoves() {
        System.out.println("My Stoves:");
        Map<Integer, Stove> stoveMap = MemberUtility.getStoves();
        for (int stoveID: stoveMap.keySet()) {
            Stove stove = stoveMap.get(stoveID);
            System.out.println(stove.toString());
        }
    }

    /**
     * Check whether there is enough ingredients in the member inventory to cook a dish
     * @param ingredientName the ingredient to be checked
     * @param quantity the quantity required of the ingredient to cook the dish
     * @return a boolean of whether there is sufficient stock in the member inventory
     */

    // Checks if there is enough ingredients in member's inventory
    public boolean checkEnoughIngredients(String ingredientName, int quantity) {
        Map<String, Integer> memberIngredientMap = MemberUtility.getInventory();
        if (memberIngredientMap.containsKey(ingredientName) && memberIngredientMap.get(ingredientName) >= quantity) {
            return true;
        }
        return false;
    }
}