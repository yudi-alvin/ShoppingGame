package menu;

import controller.*;
import utility.MemberUtility;
import java.util.*;

/**
* <p>This is the Restaurant menu class that interact with the user</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class RestaurantMenu{
    private RestaurantCtrl restCtrl;

    /**
     * Create a default RestaurantMenu object 
     */
    public RestaurantMenu() {
        restCtrl = new RestaurantCtrl();
    }

    /**
     * Display the options if restaurant is selected
     */

    public void display() { 
        System.out.println("\n== The Cooking Story :: My Restaurant ==");
        MemberUtility.displayWelcome();
        System.out.println();
        restCtrl.displayStoves();
        System.out.println();
        System.out.print("(S)erve | (W)ipe | (C)ook | (M)ain > ");
    }

    /**
     * Read user's input
     */
    public void readOption() {
        Scanner sc = new Scanner(System.in);
        String choice;
        boolean exitRestMenu = false;
        do{
            display();
            choice = sc.nextLine();
            System.out.println();
            switch (choice) {
                case "S":
                    restCtrl.serve();
                    break;
                case "W":
                    restCtrl.wipe();
                    break;
                case "C":
                    exitRestMenu = cook();
                    break;
                default :
                    if (!(choice.equals("M"))) {
                        System.out.println("Please enter a valid input.");
                    }
                    break;
    
            }
        }while(!choice.equals("M") && !exitRestMenu);
    }

    /**
     * To read user's input to cook a dish, go back to kitchen or the main menu
     * @return true if the user wants to go back to main
     */
    public boolean cook(){
        boolean invalidOption = false; 
        boolean result = false;
        do {
            invalidOption = false; 
            result = false;
            if (restCtrl.displayRecipes()) {
                System.out.print("Enter number | (K)itchen | (M)ain> ");
                Scanner sc = new Scanner(System.in);
                String selectedOption = sc.nextLine();

                
                if (selectedOption.equals("M")){
                    result = true;
                } 
                else if (selectedOption.equals("K")){
                } else {
                    try {
                        int recipeNum = Integer.parseInt(selectedOption);
                        if (! restCtrl.cook(recipeNum)) {
                            
                        }
                    } catch (NumberFormatException e){
                        System.out.println();
                        System.out.println("Please enter a valid input.");
                        invalidOption = true;
                    } 
                }
            }
            
        } while ( invalidOption );
        return result;
    }
}