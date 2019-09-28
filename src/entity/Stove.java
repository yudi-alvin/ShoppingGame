package entity;

import java.lang.System;

/**
* <p>This represents the stove each chef has in his restaurant </p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class Stove {
    private int stoveID;
    private String statusBar;
    // Status is an int;
    // 0 is available;
    // 1 is cooking;
    // 2 is done;
    // 3 is spoilt;
    // 4 is eaten;
    private int status;
    private long startTime;
    private int cookingTime;
    private String dishName;
    private boolean eaten;

    /**
     * Create a Stove object with specific stove id for a member
     * @param stoveID the stove id 
     */
    // public Stove(int stoveID) {
    //     this.stoveID = stoveID;
    //     wipeStove();
    // }

    /**
     *  Create a Stove object with specific stove id, start time, cooking time, and the dish being cooked on it
     * @param stoveID the id of the stove
     * @param startTime the start time of the stove when it is cooking
     * @param cookingTime the time it needs to cook for the dish
     * @param dishName the dish being cooked on it
     * @param eaten true if the dish on the stove is eaten. Otherwise false
     */
    public Stove(int stoveID, long startTime, int cookingTime, String dishName, boolean eaten) {
        this.stoveID = stoveID;
        this.startTime = startTime;
        this.cookingTime = cookingTime;
        this.dishName = dishName;
        this.eaten = eaten;
        if (dishName.equals("- AVAILABLE -")){
            status = 0;
            statusBar = "[ ---------- ]";
        } else {
            status = 1;
        }
        updateStatus();
    }

    /**
     * Get stove id
     * @return the id of the stove
     */

    public int getStoveID() {
        return stoveID;
    }

    /**
     * Get the start time of the dish cooked 
     * @return the start time
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Get the cooking time of the dish
     * @return the cooking time
     */
    public int getCookingTime() {
        return cookingTime;
    }

    /**
     * Get the status bar that represents the progress of the dish cooked
     * @return the status bar [ ########## ] of the stove
     */
    public String getStatusBar() {
        return statusBar;
    }

    /**
     * Get the status of the stove
     * @return 0 is empty, 1 is cooking, 2 is done, 3 is spoilt, 4 is eaten
     */

    public int getStatus() {
        return status;
    }

    /**
     * Get the dish name on the stove
     * @return the dish name as a String
     */

    public String getDishName() {
        return dishName;
    }
    
    /**
     * To cook a dish
     * @param recipe recipe
     */
    public void cook(Recipe recipe){
        this.dishName = recipe.getName();
        setStartTime();
        status = 1;
        cookingTime = recipe.getCookingTime();
        dishName = recipe.getName();
        updateStatus();
    }

    /**
     * Set the start time as the current time when the member starts to cook the dish 
     */
    public void setStartTime(){
        startTime = System.currentTimeMillis();
    }

    /**
     * Update the status bar of the stove to the current time, based on the startTime and cookingTime.
     */
    public void updateStatus() {
        if (eaten) {
            setStatusBar("[ ########## ] EATEN");
            status = 4;
        } else if (status != 0) {
            long duration = cookingTime * 60 * 1000;
            long currentTime = System.currentTimeMillis();
            long timeElapsed = currentTime - startTime;
            
            if ( timeElapsed > (duration * 2)) {
                setStatusBar("[ ########## ] SPOILT");
                status = 3;
            } else if (timeElapsed >= duration ) {
                setStatusBar("[ ########## ] 100%");
                status = 2;
            } else if (timeElapsed < duration) {
                int percentDone = (int)(timeElapsed * 100 / duration);
                setStatusBar(generateStatusBar(percentDone));
                status = 1;
            }
        }
    }

    /**
     * Set the status bar of the stove
     * @param statusBar the statusbar
     */
    public void setStatusBar(String statusBar) {
        this.statusBar = statusBar;
    }

    /**
     * Reset the stove
     */
    public void wipeStove() {
            startTime = 0;
            cookingTime = 0;
            statusBar = "[ ---------- ]";
            status = 0;
            dishName = "- AVAILABLE -";
            eaten = false;
    }

    // Generating the status bar 
    /**
     * Generate the status bar of the stove
     * @param percentDone the percentage cooked of the dish according to the cooking time
     * @return the percentage as a String
     */
    public String generateStatusBar(int percentDone) {
        int tensDigit = percentDone / 10;
        String statusBar = "";
        for (int i=0; i<10; i++){
            if (i < tensDigit){
                statusBar += "#";
            } else { 
                statusBar += "-";
            }
        }
        return "[ " + statusBar + " ] " + percentDone + "%";
    }
    /**
     * Display a stove
     */
    public String toString() {
        updateStatus();
        return String.format("%-1d. %-13s %-18s", stoveID, dishName, statusBar);
    }

    public boolean getEaten() {
        return eaten;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }
}
