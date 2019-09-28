package controller;

import entity.*;
import dao.*;
import utility.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
* <p>This controller class processes user's request for login</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class LoginCtrl {
    private MemberDAO memberDAO;

    /**
    * <p>Create a default LoginCtrl</p>
    */
    public LoginCtrl() {
        memberDAO = new MemberDAO();
        IngredientDAO IngredientDAO = new IngredientDAO();
        RecipeDAO recipeDAO = new RecipeDAO();

        CookingUtility.setIngredients(IngredientDAO.retrieveIngredients());
        CookingUtility.setRecipes(recipeDAO.retrieveAll());
    }
    
    /**
    * Get current time
    * @return String to represent the current time
    */
    public String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(cal.getTime()).toString().replace(":","");
        
        int timeInt = Integer.parseInt(time);
        return getGreeting(timeInt);
    }

    /**
    * Get the correct greetings with regard to the current time
    * @param time the current time
    * @return the appropriate greetings
    */

    public String getGreeting(int time) {
        if (time <= 1159) {
            return "Good morning";
        } else if (time <= 1759) {
            return "Good afternoon";
        } else {
            return "Good evening";
        }
    }

    /**
     * Check whether the username entered is unique in the database
    * @param username username of the user
    * @return a boolean of whether the username is unique
    */

    public boolean checkUniqueUsername (String username) {
        //if username already exists
        if (memberDAO.usernameExist(username)) {
            System.out.println("Username already exist, please choose another!");
            return false;
        }
        //return true if username is unique
        return true;
    }

    /**
     * Register a new member for the user
    * @param username this is the username
    * @param fullName this is the fullname
    * @param password this is the password
    */
    public void registerMember (String username, String fullName, String password) {
        //DAO will add the member to the database
        StoveDAO stoveDAO = new StoveDAO();
        memberDAO.registerMember(username, fullName, password);
        stoveDAO.newMemberStoves(username);
    }
    /**
     * Show whether the user has successfully logged in
     * @param username this is the username
     * @param password this is the password
     * @return a boolean of whether it is successful
     */
    public boolean loginMember (String username, String password) {
        return memberDAO.correctLoginDetails(username, password);
    }
}