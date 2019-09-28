package entity;

/**
* <p>This represents the ingredients </p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/


public class Ingredient {

    private String name;
    private double price;

    /**
     * Create an ingredient object with specific name and price
     * @param name name of the ingredient
     * @param price price of the ingredient
     */
    public Ingredient(String name, double price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Get name of the ingredient
     * @return the name of the ingredient
     */
    public String getName(){
        return name;
    }

    /**
     * Get price of the ingredient
     * @return the price of the ingredient
     */
    public double getPrice(){
        return price;
    }
    
}