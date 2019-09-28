package controller;

import entity.*;
import dao.*;
import utility.*;
import java.util.*;
/**
* <p>This controller class processes user's request for Shoping</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class ShoppingCtrl {
    private MemberDAO memberDAO;
    private Member member;
    private Map<Ingredient, Integer> cart;
    private Map<Integer, Ingredient> ingredientMap;

    /**
    * <p>Create a default ShoppingCtrl</p>
    */
    public ShoppingCtrl() {
        memberDAO = new MemberDAO();
        member = MemberUtility.getMember();
        cart = new HashMap<>();
        ingredientMap = CookingUtility.getIngredients();
    }

    /**
     * Display the shopping cart 
     */
    public void displayCart() {
        MemberUtility.displayCart(cart);
    }

    /**
     * Display all the ingredient available at the store with its index, name and price
     */
    public void displayIngredients() {
        System.out.println("Ingredients available:");
        for (int index: ingredientMap.keySet()) {            
            Ingredient tempIngredient = ingredientMap.get(index);
            String name = tempIngredient.getName();
            double price = tempIngredient.getPrice();
            System.out.printf("%2d. %-9s ($%.2f)%n", index, name, price);
        }
        System.out.println();
    }

    /**
     * Add ingredient to the cart
     * @param quantity the quantity to be added
     * @param ingredientIndex the id of the ingredient added
     */

    public void addCartIngredient(int quantity, int ingredientIndex){
        Ingredient ingredient = ingredientMap.get(ingredientIndex);
        MemberUtility.addCartIngredient(cart, ingredient, quantity);
        System.out.println(ingredient.getName() + "(Qty: " + quantity + ") has been added.");
    }

    /**
     * Get number of ingredients avaliable at the store
     * @return the number of ingredients available
     */

    public int getNumIngredients() {
        return ingredientMap.size();
    }

     /**
     * Checkout, deduct money from the balance, add the purchased ingredients to member inventory and empty the cart
     * @return a boolean of whether the transaction is successful
     */
    
    public boolean checkout() {
        double cartPrice = MemberUtility.getCartPrice(cart);
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return false;
        }
        double memberBalance = member.getBalance();
        if (memberBalance < cartPrice) {
            System.out.println("You have insufficient balance to check out.");
            return false;
        }
        String username = member.getUsername();
        
        //========= DAO ===========
        memberDAO.addToInventory(username, cart, MemberUtility.getInventory());
        //set new balance in database to current balance - cartPrice
        memberDAO.setBalance(username, memberBalance - cartPrice);
        
        //=== Member class ===
        MemberUtility.checkoutCart(cart);
        member.deductBalance(cartPrice);

        //empty cart
        cart.clear();
        return true;
    }
}