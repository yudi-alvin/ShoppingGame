package entity;

import java.util.*;

/**
* <p>This represents the member </p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class Member {
    private String username;
    private String fullName;
    private double balance;
    private int XP;
    private String title;

    /**
     * Create a Member object with specfic username, fullname, balance, XP and title
     * @param username username
     * @param fullName fullname of the user
     * @param balance balance set to the member
     * @param XP XP of the member
     * @param title title of the member
     */

    public Member(String username, String fullName, double balance, int XP, String title) {
        this.username = username;
        this.fullName = fullName;
        this.XP = XP;
        this.balance = balance;
        this.title = title;
    }

    /**
     * Get username
     * @return the username
     */
    public String getUsername(){
        return username;
    }

    /**
     * Get fullName
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Get balance
     * @return the balance 
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Add to the balance
     * @param amt the amount to be added
     */

    public void addBalance(double amt) {
        balance+= amt;
    }

    /**
     * Deduct the current balance
     * @param amt the amount to be deducted
     */
    public void deductBalance(double amt) {
        balance-= amt;
    }

    /**
     * Get the experience point
     * @return the XP 
     */

    public int getXP() {
        return XP;
    }

    /**
     * Add to the XP
     * @param amt the amount of XP to be added 
     */
    public void addXP(int amt) {
        XP += amt;
    }

    /**
     * Get the title 
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Update the title
     * @param title the new title
     */
    public void updateTitle(String title) {
        this.title = title;
    }
}