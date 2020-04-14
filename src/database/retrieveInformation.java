package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * TODO: Implement
 * @author Isabella Boone, Chase Banyai, Joel Gingrich, Joshua Jackson
 *
 */
public class retrieveInformation {

  // These credentials are to log into a locally hosted mysql server
  public static final String DB_LOCATION = "jdbc:mysql://localhost:3306/csc371";
  public static final String LOGIN_NAME = "root";
  public static final String PASSWORD = "Password02";
  
  /* If you want to connect to the schools db server, use these while on vpn
  public static final String DB_LOCATION = "jdbc:mysql://db.cs.ship.edu:3306/csc371_##";   
  public static final String LOGIN_NAME = "csc371_##";
  public static final String PASSWORD = "Password##"; */
  
  protected static Connection m_dbConn = null; 
  public boolean establishConnection() {
    // insert data into table. 
    try {
      m_dbConn = DriverManager.getConnection(DB_LOCATION,LOGIN_NAME,PASSWORD);
      return true; 
    } catch (SQLException e) {
      System.out.println("Error - Login attempt failed.  Check credentials and try again.");
      return false;
    }
  }
  
  
  public retrieveInformation() {
    if(!(establishConnection())) {
      System.out.println("CONNECTION FAILED");
      return;
    }
    insertInfo i = new insertInfo(m_dbConn);
    getAllPlayers(); 
    
    
  }

  // Get information for a single player. 
  public String[] getPlayer() { 
    return null; 
  }
  
  /*
   * Currently this gets all users in the database but does 
   * it twice so i gotta fix that i guess
   */
  public void getAllPlayers() {
    String selectData = new String("SELECT * FROM Player");
    System.out.println(selectData);
    String[] fetch = {"Username", "Email", "Password" }; 
    System.out.println("Players in database: ");
    try {
      for(int i = 0; i < fetch.length; i++) {
        PreparedStatement stmt = m_dbConn.prepareStatement(selectData);
        ResultSet rs = stmt.executeQuery(selectData);
        while (rs.next()) {
          String data = rs.getString("Username");
//          selectData[i][] = rs.getString("Username");
          System.out.print(data + "\t");
      }
      
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
  }
  
}
