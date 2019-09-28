package controller;

import entity.*;
import dao.*;
import utility.*;
import java.util.*;

/**
* <p>This controller class processes user's request for visiting friend's place</p>
* @author  BB - BB Coder, BB No Sleep
* @since   2018-04
*/

public class VisitCtrl {

    private Member member;
    private Member friend;
    private MemberDAO memberDAO;
    private StoveDAO stoveDAO;
    private Map<Integer, Stove> stoveMap;
    
    /**
     * Create a default VisitCtrl object
     */
    public VisitCtrl() {
        member = MemberUtility.getMember();
        memberDAO = new MemberDAO();
        stoveDAO = new StoveDAO();
    }

    /**
    * Check if username entered is the member's own username
    * @param username this is username to check
    * @return a boolean, return true if the username is the member's (not neighbour's)    
    */
    public boolean isMemberUsername(String username) {
        if (username.equals(member.getUsername())) {
            return true;
        }
        return false;
    }
    
    /**
    * Check if a member with that particular username exists
    * @param username this is username to check
    * @return a boolean, return true if the member exists    
    */
    public boolean neighbourExist(String username) {
        if (memberDAO.usernameExist(username)) {
            friend = memberDAO.getMember(username);
            setStoves(username);
            return true;
        }
        return false;
    }

    /**
    * Set all three stoves with friend's username.
    * @param username friend's username  
    */
    public void setStoves(String username) {
        stoveMap = stoveDAO.getStoves(username);
    }

    /**
    * Display the status bar of friend's stoves  
    */
    public void displayStoves() {
        System.out.println("Name: " + friend.getFullName());
        
        for (int stoveID: stoveMap.keySet()) {
            Stove stove = stoveMap.get(stoveID);
            System.out.println(stove.toString());
        }
    }

    /**
     * Get the money earned of the dish(es) cooked at friend's restaurant.
     * @return a list of money earned of the dish(es)
     */
    public List<Double> getFeastableMoney() {
        List<Double> output = new ArrayList<>();

        for (Stove temp: stoveMap.values()) {
            if (temp.getStatus() == 2) {
                String recipeName = temp.getDishName(); 
                Recipe recipe = CookingUtility.getRecipeByName(recipeName);
                output.add(recipe.getMoneyEarned());
            }
        }
        return output;
    }

    //when user selects feast
    /**
     * Display what the visiting member has feasted at friend's restaurant.
     * @return true is the visiting member has feasted at least one dish; otherwise return false.
     */
    public boolean feastOption() {
        List<Double> feastableList = getFeastableMoney();        
        // int feastable = numberStovesFeastable();
        if (feastableList.isEmpty()) {
            return false;
        }

        if (feastableList.size() == 3) {//sort
            feastableList.sort(Comparator.naturalOrder());
            feastableList.remove(0);
        }
        
        Map<String, Integer> feastedRecipes = feast(feastableList);
        String output = "You have eaten ";

        int count = 1;
        for (String name: feastedRecipes.keySet()) {
            if (count ==  2) {
                output += " and ";
            }
            int qty = feastedRecipes.get(name);
            if (qty > 1) {
                output += qty + " ";
            }
            output += name;
        }
        output += ".";
        System.out.println(output);
        return true;
    }

    /**
     * To feast on up to 3 most expensive ready dishes
     * @param feastableList a list of money earned from the feastable dishes at friend's restaurant
     * @return a map of the dishname eaten with quantity
     */
    public Map<String, Integer> feast(List<Double> feastableList) {
        int totalXP = 0;
        Map<String, Integer> recipesEaten = new HashMap<>();

        int toFeast = feastableList.size();
        List<Integer> eatenStoveIDs = new ArrayList<>();

        int count = 0;
        for (Stove temp: stoveMap.values()) {
            if (temp.getStatus() == 2 && count < toFeast) {
                String recipeName = temp.getDishName(); 
                Recipe recipe = CookingUtility.getRecipeByName(recipeName);
                double money = recipe.getMoneyEarned();
                
                if (feastableList.contains(money)) {
                    totalXP += (int)(recipe.getXP()/7);

                    temp.setEaten(true);
                    eatenStoveIDs.add(temp.getStoveID());
                
                    //adding to recipesEaten
                    int qty = 1;
                    if (recipesEaten.containsKey(recipeName)) {
                        qty += recipesEaten.get(recipeName);
                    }
                    recipesEaten.put(recipeName, qty);
                    count++;
                }
            }
        }
        String memberUsername = member.getUsername();
        int newXP = member.getXP() + totalXP;
        member.addXP(totalXP);
        memberDAO.setXP(memberUsername, newXP);
        stoveDAO.setStovesEaten(eatenStoveIDs, friend.getUsername());
        member.updateTitle(memberDAO.getTitle(memberUsername));
        return recipesEaten;
    }

}