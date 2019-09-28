package app;

import menu.*;

/**
* <p>This allows users to login, register or exit the Cooking App</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/


public class CookingApp {
    /**
   * This is the main method which starts the Cooking App .
   * @param args Unused.
   */

    public static void main(String[] args) {
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.readOption();
    }
}