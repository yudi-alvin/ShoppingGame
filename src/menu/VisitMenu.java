package menu;

import utility.MemberUtility;
import java.util.*;
import controller.*;

/**
* <p>This is the visit menu class that interacts with the user</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class VisitMenu{
    private VisitCtrl visitCtrl;

    /**
     * Create a default VisitMenu object
     */
    public VisitMenu(){
        visitCtrl = new VisitCtrl();

    }
    /**
     * Read user's input
     */
    public void readOption(){
        Scanner sc = new Scanner(System.in);
        String neighbourUser;
        boolean validUsername;
        boolean exitVisit;
        
        do {
            validUsername = false;
            exitVisit = false;
            display();
            neighbourUser = sc.nextLine();

            switch (neighbourUser) {
                case "M" :
                    //back to main menu
                    break;
                    
                default:
                    if (visitCtrl.isMemberUsername(neighbourUser)) {
                        System.out.println("Please do not enter your own username.");
                    }
                    //if member with specified username exists
                    else if (visitCtrl.neighbourExist(neighbourUser)) {
                        exitVisit = neighbourMenu();
                    } else {
                        System.out.println(neighbourUser + " does not exist");
                    }
                    break;
            }
        } while (!(neighbourUser.equals("M")) && !validUsername && !exitVisit);
        
    }
    /**
     * Display the interface for user to enter the username he/she would like to visit
     */
    public void display() {
        System.out.println("\n== The Cooking Story :: Visit Friend ==");
        MemberUtility.displayWelcome();
        System.out.print("(M)ain | Enter neighbour's username> ");
    }

    /**
     * Display neighbour's stove and menu for choice
     * @return a boolean, return true if the member's input is "M"    
     */
    public boolean neighbourMenu() {
        String choice;
        do {
            visitCtrl.displayStoves();
            System.out.print("(F)east | (V)isit | (M)ain > ");
            Scanner sc = new Scanner(System.in);
            choice = sc.nextLine();
            
            switch (choice) {
                case "M" :
                    return true;

                case "V" :
                    return false;

                case "F" :
                    if (!(visitCtrl.feastOption())) {
                        System.out.println("There are no dishes ready to be feasted on.\n");
                        break;
                    }
                    //back to main menu after feasting
                    return true;
                
                default:
                    System.out.println();
                    System.out.println("Please enter a valid input.");
                    break;
            }
        } while (!(choice.equals("M")));
        return true;
    }

}