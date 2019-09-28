package menu;

import controller.*;
import utility.MemberUtility;
import java.util.*;

/**
* <p>This is the Training menu class that interact with the user</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class TrainingMenu {
    private TrainingCtrl trainingCtrl;

    /**
     * Create a default TrainingMenu object
     */
    public TrainingMenu() {
        trainingCtrl = new TrainingCtrl();
    }
    /**
     * Display options if training centre is selected
     */
    public void display() {
        System.out.println("\n== The Cooking Story :: My Training Center == ");
        trainingCtrl.displayMenu();
        System.out.print("Use ingredient | (L)earn | (R)ecipes | (M)ain> ");
    }
    
    /**
     * Read user's input 
     */
    public void readOption() {
        Scanner sc = new Scanner(System.in);
        String choice;
        boolean exitTraining = false;
        boolean validInput = true;
        do {
            display();
            choice = sc.nextLine();
            switch (choice) {
                case "R" :
                    //My Recipe Book
                    exitTraining = recipeBook();
                    break;

                case "L" :
                    //Learn Option
                    trainingCtrl.learnRecipe();
                    break;

                case "M" :
                    //back to main menu
                    break;

                default :
                    validInput = trainingCtrl.selectIngredient(choice);
                    if (!validInput) {
                        System.out.println("Please enter a valid input.");   
                    }
                    
                }
        } while (!(choice.equals("M")) && !exitTraining);
    }
    /**
     * Display the options available in training centre
     */
    public void displayRecipeBook() {
        //DISPLAY HEADER
        System.out.println("\n== The Cooking Story :: My Recipe Book ==");
        MemberUtility.displayWelcome();
        //DISPLAY recipes
        trainingCtrl.displayRecipes();
        System.out.print("View recipe | (T)raining Center | (M)ain> ");
    }

    /**
     * Read user's input 
     * @return true if the user wants to go back to main menu. False if the user stays at training centre
     */

    public boolean recipeBook() {
        Scanner sc = new Scanner(System.in);
        String recipeChoice;

        do {
            displayRecipeBook();
            recipeChoice = sc.nextLine();
            
            switch (recipeChoice) {
                case "T" :
                    // returns to TrainingCentre
                    // false -> do not want to exit trainingMenu
                    return false;

                case "M" :
                    // returns to MainMenu
                    // true -> exit TrainingMenu to go back to MainMenu
                    return true;

                default :
                    boolean validInput = trainingCtrl.checkRecipeIngredient(recipeChoice);
                    if (!validInput) {
                        System.out.println("Please enter a valid input.");
                    }
            }
                
        } while (!(recipeChoice.equals("T")) && !(recipeChoice.equals("M")));

        return false;
    }

}