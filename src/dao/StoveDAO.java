package dao;

import utility.*;
import entity.*;
import java.util.*;
import java.io.*;
import java.sql.*;

/**
* <p>This class manages the Stove objects.</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class StoveDAO {
    private ConnectionMgr connectionMgr;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    /**
     * Create a default StoveDAO object
     */
    public StoveDAO() {
        connectionMgr = new ConnectionMgr();
        conn = null;
        stmt = null;
        rs = null;
    }

    /**
     * Get three Stoves for the new member
     * @param username the username
     */

    public void newMemberStoves(String username) {
        try{
            conn = connectionMgr.connect();
            for (int stoveid =1; stoveid<4; stoveid++) {
                String sql = "INSERT INTO stove(stoveID, memberUsername, starttime, cookingtime, dishname) values" + 
                            "('" + stoveid + "','" + username + "'," + 0 + "," + 0 + ", '- AVAILABLE -');";
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
            }
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
     * Get stoves of a member
     * @param username the username
     * @return a map representing the stove id and the stove
     */

    public Map<Integer,Stove> getStoves(String username){
        Map<Integer,Stove> stoveMap = new HashMap<Integer,Stove>();
        try{
            conn = connectionMgr.connect();
            String sql = "SELECT * from stove" + 
            " WHERE memberusername = '" + username + "';";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            while (rs.next()){
                int stoveID = rs.getInt("stoveID");
                long startTime = rs.getLong("starttime");
                int cookingTime = rs.getInt("cookingtime");
                String dishName = rs.getString("dishname");
                boolean eaten = rs.getBoolean("eaten");
                Stove stove = new Stove(stoveID, startTime, cookingTime, dishName, eaten);
                stoveMap.put(stoveID, stove); 
            }
            
        } catch (SQLException e) {
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
        return stoveMap;
    }

    /**
     * Update the stove status
     * @param stove the stove 
     * @param username the username
     */

    public void updateStove(Stove stove, String username){
        
        int stoveID = stove.getStoveID();
        long startTime = stove.getStartTime();
        int cookingTime = stove.getCookingTime();
        String dishName = stove.getDishName();
        boolean eaten = stove.getEaten();

        try{
            conn = connectionMgr.connect();
            String sql = "UPDATE stove" + 
            " SET starttime =" + startTime +
            ", cookingtime =" + cookingTime +
            ", dishname = '" + dishName + 
            "', eaten = " + eaten +
            " WHERE memberusername = '" + username + "' and stoveid =" + stoveID + ";";
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
     * Set the member's stove(s) status to 'eaten', where the dish cooked is eaten
     * @param stoveList a list of stove id
     * @param username the username of the member to whom the stoves belong
     */
    public void setStovesEaten(List<Integer> stoveList, String username) {
        try{
            conn = connectionMgr.connect();
            
            for (int stoveID: stoveList) {
                String sql = "UPDATE stove "
                        + "SET eaten = true " 
                        + "WHERE memberUsername = '" + username + "' and stoveID = " + stoveID + ";";
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
}