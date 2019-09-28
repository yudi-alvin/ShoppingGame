package dao;

import utility.*;
import entity.*;
import java.util.*;
import java.io.*;
import java.sql.*;

/**
* <p>This class manages the Recipe objects.</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/


public class RecipeDAO {
    private ConnectionMgr connectionMgr;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    /**
     * Create a default RecipeDAO object
     */
    public RecipeDAO() {
        connectionMgr = new ConnectionMgr();
        this.conn = null;
        this.stmt = null;
        this.rs = null;
    }

    /**
     * Retrieve all the recipes available at the database
     * @return a list of recipes
     */
    public List<Recipe> retrieveAll() {
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

        try{
            conn = connectionMgr.connect();
            String sql = "SELECT * FROM RECIPE";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
    
            while(rs.next()) {
                String dishName = rs.getString("dishName");
                int cookingTime = rs.getInt("cookingtime");
                int XP = rs.getInt("XP");
                double moneyEarned = rs.getDouble("moneyearned");

                Map<String, Integer> recipeIngredients = new HashMap<>();
                
                for (int i = 2; i < 6; i++) {
                    int qty = 1;
                    String ingredientName = rs.getString(i);
                    if (ingredientName.length() > 0) {
                        if (recipeIngredients.containsKey(ingredientName)) {
                            qty += recipeIngredients.get(ingredientName);
                        }
                        recipeIngredients.put(ingredientName, qty);
                    }       
                }
                Recipe temp = new Recipe(dishName, recipeIngredients, cookingTime, XP, moneyEarned);
                recipeList.add(temp);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                    rs = null;
                }
                if (conn != null) {
                    connectionMgr.disconnect();
                    conn = null;
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return recipeList;
    }



    /**
     * Get the recipes that the member know how to cook 
     * @param username the member's username
     * @return a map that represents all the recipes
     */
    public Map<Integer,String> retrieveMemberRecipe(String username) {
        Map<Integer,String> memberRecipes = new HashMap<>();
        try{
            conn = connectionMgr.connect();
            String sql = "select dishName from memberRecipe where memberUsername = '" + username + "';";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            int count = 1;
            while(rs.next()) {
                
                String dishName = rs.getString("dishName");
                // Recipe recipe = getRecipeByName(dishName);
                memberRecipes.put(count,dishName);
                count ++;
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                    rs = null;
                }
                if (conn != null) {
                    connectionMgr.disconnect();
                    conn = null;
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return memberRecipes;
    }

    /**
    * Insert a new memberRecipe into the database
    * @param username the member's username
    * @param recipe the dishName of the recipe
    */
    public void learnRecipe(String username, String recipe) {
        try{
            conn = connectionMgr.connect();
            String sql = "insert into memberrecipe values('" + username + "','" + recipe + "')";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (conn != null) {
                    connectionMgr.disconnect();
                    conn = null;
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
           
    }

}