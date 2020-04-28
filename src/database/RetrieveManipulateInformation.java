package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Retrieve and manipulate information that is currently in the database.
 * @author Isabella Boone, Chase Banyai, Joel Gingrich, Joshua Jackson
 *
 */
public class RetrieveManipulateInformation {
  static Connection m_dbConn = null;
  final int playerUsernames = 0, allItems = 1, charNames = 2, 
            locIdNums = 3, locAreaTypes = 4;
  String[] procedures = {
      "CREATE PROCEDURE get_all_player_usernames()"
        + " SELECT Username FROM Player;",

      "CREATE PROCEDURE get_all_items() "
        + "SELECT ItemId from Item;",

      "CREATE PROCEDURE get_character_names(IN n varchar(20))"
        + "SELECT Name FROM Characters;",
          
      "CREATE PROCEDURE get_all_location_idNumbers() "
        + "SELECT IdNumber from Location;",
        
      "CREATE PROCEDURE get_all_location_AreaTypes() "
        + "SELECT AreaType from Location;"
          
  }, procedureNames = { 
      "get_all_player_usernames", "get_all_items", 
      "get_character_names", "get_all_location_idNumbers", 
      "get_all_location_AreaTypes,"
  };
  
  public RetrieveManipulateInformation(Connection con) {
    this.m_dbConn = con;
    createProcedures(); 
  }
  
  public static Connection getConncetion() {
    return m_dbConn;
  }

  /**
   * Create all procedures
   */
  public void createProcedures() {
    // Create Procedure
    String delimSlash = "DELIMITER //", delimSemi = "DELIMITER ;";
    
//    try {
      // Set delimiter for create procedures 
//      PreparedStatement stmt = m_dbConn.prepareStatement(delimSlash);
//      try {
//        stmt.execute(); // Execute statement 
//      } catch(SQLException e) {
//        e.printStackTrace();
//        System.out.println(delimSlash);
//      }
      
      PreparedStatement stmt;
      // Call statement to create procedures
      for(int i = 0; i < procedures.length; i++) {
        try {
          stmt = m_dbConn.prepareStatement(procedures[i]);
          stmt.execute();
        } catch (SQLException e) {
          e.printStackTrace();
          System.out.println("Invalid SQL Statement -- " + procedures[i]);
        }
      }
      
      // Fix delimiter back to normal
//      stmt = m_dbConn.prepareStatement(delimSemi);
//      stmt.execute();
      
//    } catch (SQLException e) {
//      e.printStackTrace();
//      System.out.println("fucc");
//    }
  }

  /**
   * Drop all procedures 
   * maybe
   */
  public void dropProcedures() {
    for(int i = 0; i < procedures.length; i++) {
      String drop = "DROP PROCEDURE IF EXISTS" + procedures[i] + ";";
      try {
        PreparedStatement stmt = m_dbConn.prepareStatement(drop);
        stmt.execute(); 
        
      } catch (SQLException e) {
        e.printStackTrace();
        
      }
    }
  }
  
  
  // Location Methods
  /**
   * Get the number of total locations in the database.  
   * @return int of how many total locations exist in database.
   */
  public int getNumLocations() {
    try {
      Statement stmt = m_dbConn.createStatement();
      ResultSet r = stmt.executeQuery("SELECT COUNT(*) FROM Location;");
      // Count how many locations are in the database
      
      r.next();
      return r.getInt("count(*)"); 
      
    } catch (SQLException e) {
      e.printStackTrace();
      return -1; // If this number is actually accidentally used, it will throw an error. 
    }
  }
  
  /**
   * Get every IdNumber of every Location in the database.
   * @return int[] of all Location IdNumbers 
   */
  public int[] getAllLocationIds() {
    String call = "CALL " + procedureNames[2] + "();";
    int[] locationIds = new int[getNumLocations()]; 
    try {
      CallableStatement stmt = m_dbConn.prepareCall(call);
      stmt.execute();
      
      ResultSet rs = stmt.getResultSet(); 
      
      int i = 0;
      while(rs.next()) {
        locationIds[i] = rs.getInt("IdNumber");
        i++; 
      }
      
    } catch (SQLException e1) {
      e1.printStackTrace();
      System.out.println("Syntax error, maybe");
    }
    
    return locationIds; 
  }
  
  /**
   * Retrieve all location AreaTypes, utilizing stored procedure
   * @return String array of all location AreaTypes
   */
  public String[] getAllAreaTypes() {
    String call = "CALL " + procedureNames[locAreaTypes] + "();";
    String[] types = new String[getNumLocations()]; 
    try {
      CallableStatement stmt = m_dbConn.prepareCall(call);
      stmt.execute();
      
      ResultSet rs = stmt.getResultSet(); 
      
      int i = 0;
      while(rs.next()) {
        types[i] = rs.getString("AreaTypes");
        i++; 
      }
      
    } catch (SQLException e1) {
      e1.printStackTrace();
      System.out.println("Syntax error (probably, dunno)");
    }
    
    return types; 
  }
  
  
  
  
  // Item Manipulation Methods
  /**
   * Get the ItemId of every item in the database , utilizing stored procedure
   * @return int array of every item id 
   */
  public int[] getAllItems() {
    String call = "CALL " + procedureNames[1] + "();";
    int[] itemIds = new int[getNumItems()]; 
    try {
      CallableStatement stmt = m_dbConn.prepareCall(call);
      boolean x = stmt.execute();
      System.out.println(x);
      ResultSet rs = stmt.getResultSet(); 
      
      int i = 0;
      while(rs.next()) {  
        itemIds[i] = rs.getInt("ItemId");
        i++; 
      }
      
    } catch (SQLException e1) {
      e1.printStackTrace();
      System.out.println("Syntax error (probably, dunno)");
    }
    
    return itemIds; 
  }
  
  /**
   * Retrieve number of all items in the database
   * @return int of all items in database
   */
  public int getNumItems() {
    try {
      Statement stmt = m_dbConn.createStatement();
      ResultSet r = stmt.executeQuery("SELECT COUNT(*) FROM Item");
      // Count how many items we have
      
      r.next();
      return r.getInt("count(*)"); 
      
    } catch (SQLException e) {
      e.printStackTrace();
      return -1; // If this number is actually accidentally used, it will throw an error. 
    }
  }
  
  /**
   * Determine what the type of an item is based on its itemId.
   * @param itemId of an existing item
   * @return "Armor", "Container", or "Weapon"
   */
  public String getItemType(String itemId) {
    try {
      Statement stmt = m_dbConn.createStatement();
      ResultSet[] rs = { stmt.executeQuery("SELECT COUNT(*) FROM Armor WHERE ArmorId = " + itemId + ";"),
         stmt.executeQuery("SELECT COUNT(*) FROM Container WHERE ContId = " + itemId + ";"),
         stmt.executeQuery("SELECT COUNT(*) FROM Weapon WHERE WepId = " + itemId + ";") };
      
      for(int i = 0; i< rs.length; i++) {
        rs[i].next(); 
        if(rs[i].getInt("count(*)") > 0) {
          // If our count is > 0, we found what type our item is.  
          switch(i) {
          case(0):
            return "Armor"; 
          case(1):
            return "Container"; 
          case(2):
            return "Weapon";
          }
        }
      }
      return null; // shouldnt get here but ya know
      
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Error in syntax or ");
      return null; // If there is an error we return null to exit, and if it is used it will cause an error
    }
  }
  
// TODO: Implement these methods
//  pubilc String[] getArmorInfo(String itemId) {
//    
//  }
//  
//  pubilc String[] getWeaponInfo(String itemId) {
//    
//  }
//  
//  pubilc String[] getContainerInfo(String itemId) {
//    
//  }
  
  // Character Layout Information
  
  /**
   * Determine how many characters a user has
   * @param username whose characters we are counting 
   * @return int of characters from user
   */
  public int getNumChars(String username) {
    try {
      Statement stmt = m_dbConn.createStatement();
      ResultSet r = stmt.executeQuery("SELECT COUNT(*) FROM Characters WHERE pUserName = '" + username + "'; ");
      // Count how many characters a user has 
      
      r.next();
      return r.getInt("count(*)"); 
      
    } catch (SQLException e) {
      e.printStackTrace();
      return -1; // If this number is actually accidentally used, it will throw an error. 
    }
  }
  
  /**
   * Retrieve number of total players in the database.
   * @return number of players
   */
  public int getNumPlayers() {
    try {
      Statement stmt = m_dbConn.createStatement();
      ResultSet r = stmt.executeQuery("SELECT COUNT(*) FROM Player");
      // Count how many players we have
      
      r.next();
      return r.getInt("count(*)"); 
      
    } catch (SQLException e) {
      e.printStackTrace();
      return -1; // If this number is actually accidentally used, it will throw an error. 
    }
  }
  
  /**
   * Retrieve all characters from a specific user given a username.
   * Utilizes store procedure. 
   * 
   * @param username of desired player
   * @return String[] of all character names that username has. 
   */
  public String[] getAllCharacterNamesFromUser(String username) {
    String call = "CALL " + procedureNames[2] + "(?);";
    String[] names = new String[getNumChars(username)]; 
    try {
      CallableStatement stmt = m_dbConn.prepareCall(call);
      stmt.setString(1, username);
      stmt.execute();
      
      ResultSet rs = stmt.getResultSet(); 
      
      int i = 0;
      while(rs.next()) {
        names[i] = rs.getString("Name");
        i++; 
      }
      
    } catch (SQLException e1) {
      e1.printStackTrace();
      System.out.println("Syntax error (probably, dunno)");
    }
    
    return names; 
  }

  /**
   * Retrieve all player usernames, utilizing stored procedure
   * @return String array of all player usernames
   */
  public String[] getAllPlayerUsernames() {
    String call = "CALL " + procedureNames[0] + "();";
    String[] usernames = new String[getNumPlayers()]; 
    try {
      CallableStatement stmt = m_dbConn.prepareCall(call);
      stmt.execute();
      
      ResultSet rs = stmt.getResultSet(); 
      
      int i = 0;
      while(rs.next()) {
        usernames[i] = rs.getString("Username");
        i++; 
      }
      
    } catch (SQLException e1) {
      e1.printStackTrace();
      System.out.println("Syntax error (probably, dunno)");
    }
    
    return usernames; 
  }

  public String[][] getCharacterStats(String characterName) {
    
    return null;
  }
}