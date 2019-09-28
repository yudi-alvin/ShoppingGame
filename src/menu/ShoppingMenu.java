package menu;

import controller.*;
import utility.MemberUtility;

import java.text.NumberFormat;
import java.util.*;

/**
* <p>This is the shopping menu class that interact with the user</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class ShoppingMenu {
    private ShoppingCtrl shoppingCtrl;
    
    /**
     * Create a default ShoppingMenu object 
     */
    public ShoppingMenu() { 
        shoppingCtrl = new ShoppingCtrl();
    }

    /**
     * Display the options if shopping is selected
     */
    public void display() { 
        System.out.println("\n== The Cooking Story :: Le Shoppe == ");
        MemberUtility.displayWelcome();
        System.out.println();

        // //print cart items
        shoppingCtrl.displayCart();
        System.out.println();
        // System.out.println();
        shoppingCtrl.displayIngredients();
        System.out.print("Add Item | (C)heckout | (M)ain > ");
    }
    /**
     * Read user's input
     */
    public void readOption() {
        Scanner sc = new Scanner(System.in);
        String choice;
        do{
            display();
            choice = sc.nextLine();
            
            //check out
            if (choice.equals("C")) {
                checkout();
            } 
            //back to main menu
            else if (choice.equals("M")) {
                System.out.println();
            } 
            else {
                //if input is an integer
                try {
                    int choiceInt = Integer.parseInt(choice);
                    //if input is within number of ingredients
                    if (choiceInt > 0 && choiceInt <= shoppingCtrl.getNumIngredients()) {
                        addCartIngredient(choiceInt);
                    }
                    else {
                        System.out.println("Please enter a valid input.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid input.");
                }
            }            
        } while (!choice.equals("M"));  
    }
    /**
     * Add the user's input of ingredient and quantity that the user wants to add to the cart
     * @param ingredientID the ingredient ID
     */

    public void addCartIngredient(int ingredientID) {
        Scanner sc = new Scanner(System.in);
        int quantity = 0;
        do {
            System.out.print("Enter Quantity > ");
            String line = sc.nextLine();
            if (line.length() != 0) {
                try {
                    quantity = Integer.parseInt(line);
                }
                catch (NumberFormatException e) {
                    System.out.println("Quantity entered should be a number.");
                }
                if (quantity <= 0) {
                    System.out.println("Please enter a positive quantity. \n");
                }
            }
        } while (quantity <= 0);
        
        shoppingCtrl.addCartIngredient(quantity, ingredientID);    
    }

    /**
     * Display whether checkout has been successful
     */
    public void checkout() {
        if (!shoppingCtrl.checkout()) {
            System.out.println("Checkout not successful");
        } else {
            System.out.println("Checkout successful.");
        }
    }
}