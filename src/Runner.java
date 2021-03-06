import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Gui.Gui;
import database.*;

public class Runner {

  // These credentials are to log into a locally hosted mysql server
  public static final String DB_LOCATION = "jdbc:mysql://localhost:3306/csc371";
  public static final String LOGIN_NAME = "root";
  public static final String PASSWORD = "password";

  /* These credentials log into schools locally hosted server
   * public static final String DB_LOCATION =
   * "jdbc:mysql://db.cs.ship.edu:3306/csc371_##"; public static final String
   * LOGIN_NAME = "csc371_##"; public static final String PASSWORD = "Password##";
   */

  protected static Connection m_dbConn = null;

  /**
   * Establish a connection to the database
   * @return whether or not the connection was successfully established
   */
  public static boolean establishConnection() {
    try {
      DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver()); // activate jdbc line
      m_dbConn = DriverManager.getConnection(DB_LOCATION, LOGIN_NAME, PASSWORD);
      return true;
    } catch (SQLException e) {
      System.out.println("Error - Login attempt failed.  Check credentials and try again.");
      return false;
    }
  }

  public static void main(String[] args) {
    
    if (!(establishConnection())) {
      return;
    }
    
    GenerateDatabase insert = new GenerateDatabase(m_dbConn); // insert randomly generated information
    AccessDatabase getInfo = AccessDatabase.createAccessDatabase(m_dbConn); // manipulate information in database
    Gui gui = new Gui(getInfo); // display 
    
  }
  
}
