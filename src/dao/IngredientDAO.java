package dao;

import utility.*;
import entity.*;
import java.util.*;
import java.sql.*;

/**
* <p>This class manages the Member objects.</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class IngredientDAO {

    private ConnectionMgr connectionMgr;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    /** 
     * Create a default ingredientDAO object
     */
    public IngredientDAO() {
        connectionMgr = new ConnectionMgr();
        conn = null;
        stmt = null;
        rs = null;
    }

    /**
     * Retrieve all ingredients in a map
     * @return a map contains the ingredient ID and the ingredient
     */

    public Map<Integer,Ingredient> retrieveIngredients() {
        //id => Ingredient
        Map<Integer, Ingredient> ingredientMap = new HashMap<>();
        int ingredientIndex = 1;
        
        try{
            conn = connectionMgr.connect();
            String sql = "select * from ingredient;";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                String ingredientName = rs.getString("ingredientName");
                double ingredientPrice = rs.getDouble("price"); 
                
                Ingredient temp = new Ingredient(ingredientName, ingredientPrice);
                ingredientMap.put(ingredientIndex, temp);

                ingredientIndex++;
            }   
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            try{

                if (stmt != null){
                    stmt.close();
                    stmt = null;
                    rs = null;
                }
                if (conn != null){
                    connectionMgr.disconnect();
                    conn = null;
                }
            }catch(SQLException e){
                e.printStackTrace();
            }   
        }

        return ingredientMap;
    }
        
}