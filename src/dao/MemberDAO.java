package dao;

import utility.*;
import entity.*;
import java.util.*;
import java.io.*;
import java.sql.*;

/**
* <p>This class manages the Member objects.</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class MemberDAO {
    private ConnectionMgr connectionMgr;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    /**
     * Create a default MemberDAO object
     */
    public MemberDAO() { 
        connectionMgr = new ConnectionMgr();
        conn = null;
        stmt = null;
        this.rs = null;
    }

    /**
     * Get the member through the username
     * @param username the username
     * @return a memeber object 
     */

    public Member getMember(String username) {
        //to retrieve details from database
        Member member = null;
        String fullName = "";
        double balance = 0;
        int XP = 0;

        try{
            conn = connectionMgr.connect();
            String sql = "select * from member where username = '" + username + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                fullName = rs.getString("fullname");
                balance = rs.getDouble("balance"); 
                XP = rs.getInt("XP");
            }   
        } catch (SQLException e) {
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
        String title = getTitle(username);
        member = new Member(username, fullName, balance, XP, title);
        return member;
    }

    /**
     * Set the balance of the member
     * @param username the username
     * @param amount the new balance
     */

    public void setBalance(String username, double amount) {

        try{
            conn = connectionMgr.connect();
            String sql = "UPDATE member" + 
            " SET balance = " + amount + 
            " WHERE username = '" + username + "';";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if (stmt != null){
                    stmt.close();
                    stmt = null;
                }
                if (conn != null){
                    connectionMgr.disconnect();
                    conn = null;
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    
    /**
     * Register a new member by adding inserting into the database
     * @param username the member's username
     * @param fullName the member's full name
     * @param password the member's password
     */
    public void registerMember(String username, String fullName, String password) {
        
        String hashPassword = PasswordUtility.getSaltedHash(password);

        try{
            conn = connectionMgr.connect();
            String sql = "insert into member values ('" + username + "', '" + hashPassword + "', '" + fullName + "', 10, 0)";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if (stmt != null){
                    stmt.close();
                    stmt = null;
                }
                if (conn != null){
                    connectionMgr.disconnect();
                    conn = null;
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Check if the username exists in the database
     * @param username the username to check
     * @return a boolean represents whether the username exists in the database
     */
    public boolean usernameExist(String username) {
        boolean alreadyExist = false;

        try{
            conn = connectionMgr.connect();
            String sql = "select count(*) as \"result\" from member where username = '" + username + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            if (rs.next()){
                if (rs.getInt("result") == 1){
                    alreadyExist = true;
                } 
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
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return alreadyExist;
    }

    /**
     * Check whether login is successful
     * @param username the username
     * @param password the password
     * @return whether it is successful login
     */
    public boolean correctLoginDetails(String username, String password) {
        boolean loginSuccess = false;
        try{
            conn = connectionMgr.connect();
            String sql = "select * from member where username = '" + username + "';";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                String storedPwd = rs.getString("pwd");
                if (PasswordUtility.check(password, storedPwd)) {
                    loginSuccess = true;
                }
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

        return loginSuccess;
    }

    /**
     * Set the experience point of the member
     * @param username the username
     * @param amount the new XP 
     */
    public void setXP(String username, int amount) {

        try{
            conn = connectionMgr.connect();
            String sql = "UPDATE member" + 
            " SET XP =" + amount +
            " WHERE username = '" + username + "';";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                if (stmt != null){
                    stmt.close();
                    stmt = null;
                }
                if (conn != null){
                    connectionMgr.disconnect();
                    conn = null;
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    // ======= INVENTORY ======= 
    /**
     * Retrieve the member's inventory (all the ingredients the member owns) 
     * @param username the username of the member to retrieve
     * @return a map of ingredient name and quantity owned 
     */
    public Map<String, Integer> getMemberInventory(String username) {
        HashMap<String, Integer> inventory = new HashMap<>();

        try{
            conn = connectionMgr.connect();
            String sql = "select * from inventory where memberUsername = '" + username + "';";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            while(rs.next()){
                String ingredientName = rs.getString("ingredientName");
                int quantity = rs.getInt("quantity"); 
                
                inventory.put(ingredientName, quantity);
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

        return inventory;
    }

    /**
     * Add the ingredients bought to the member inventory
     * @param username the username
     * @param cartMap the cart with items purchased
     * @param memberInventory the existing member inventory
     */

    public void addToInventory(String username, Map<Ingredient, Integer> cartMap, Map<String, Integer> memberInventory) {

        try{
            conn = connectionMgr.connect();
            
            for (Ingredient temp: cartMap.keySet()) {
                int quantity = cartMap.get(temp);
                String ingredientName = temp.getName();
                String sql;
                //if temp exists in member inventory
                if (memberInventory.containsKey(ingredientName)) {

                    sql = "UPDATE inventory "
                        + "SET quantity = quantity + " + quantity
                        + " WHERE memberUsername = '" + username + "' and ingredientName = '" + ingredientName + "'; ";
                }
                else{
                    sql = "INSERT INTO inventory (memberUsername, ingredientName, quantity) VALUES "
                        + "('" + username + "', '" + ingredientName + "', " + quantity + "); ";
                }
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
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
    }

    /**
     * Deduct ingredient from member inventory from cooking
     * @param ingredientMap a map of used ingredient and the corresponding quantities
     * @param username username of the member whose inventory is being updated
     */

    public void useIngredient(Map<String, Integer> ingredientMap, String username){
        Map <String, Integer> memberInventory = MemberUtility.getInventory();
        
        try{
            conn = connectionMgr.connect();
        
            for (String ingredientName: ingredientMap.keySet()) {
                int quantity = ingredientMap.get(ingredientName);
                String sql = "";                
                //if quantity to be deducted is all the member has
                if (memberInventory.get(ingredientName) == quantity) {

                    sql = "DELETE FROM inventory "
                        + " WHERE memberUsername = '" + username + "' and ingredientName = '" + ingredientName + "'; ";
                }
                else{
                    sql = "UPDATE inventory " +
                          "SET quantity = quantity - " + quantity +
                          " WHERE memberUsername = '" + username + 
                          "' and ingredientName = '" + ingredientName + "'";
                }
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
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
    }

    /**
     * Get the member's rank
     * @param username the username
     * @return the rank of the member
     */
    public String getTitle(String username) {
        String title = "";

        try {
            conn = connectionMgr.connect();
            String sql = "SELECT title FROM rank "
            + "WHERE XP <= (SELECT XP FROM member WHERE username = '" + username + "') "
            + "ORDER BY XP desc "
            + "LIMIT 0,1;";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                title = rs.getString("title");  
            }
            
        } catch (SQLException e) {
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
    
        return title;
    }

} 