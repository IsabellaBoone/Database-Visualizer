package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * TODO: Implement
 * 
 * @author Isabella Boone, Chase Banyai, Joel Gingrich, Joshua Jackson
 *
 */
public class retrieveInformation {
  static Connection m_dbConn = null;

  public retrieveInformation(Connection con) {
    this.m_dbConn = con;
  }
  
  public static Connection getConncetion() {
    return m_dbConn;
  }
  /**
   * Get the number of all items in database. 
   * @return int number of all items in DB. 
   */
  public int getNumItems() {
    try {
      Statement stmt = m_dbConn.createStatement();
      ResultSet r = stmt.executeQuery("SELECT COUNT(*) FROM Item");
      r.next();
      return r.getInt("count(*)");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("made an oopsie");
      return -1; 
    }
  }
  
  /**
   * Get the primary key of all items in the DB
   * @return an int array of all item ids in the 
   * database at the moment. 
   */
  public int[] getAllItems() {
    String selectData = new String("SELECT Id FROM Item");
    int[] ids = new int[getNumItems()];
    System.out.print(getNumItems() + " Items in database:\t");

    try {
      PreparedStatement stmt = m_dbConn.prepareStatement(selectData);
      ResultSet rs = stmt.executeQuery(selectData);

      // Each fetch for every player
      for (int i = 0; i < getNumItems(); i++) {
        if (!(rs.next())) {
          break;
        }
//        System.out.print(rs.getString("Username") + "\t");
//        ids[i] = Integer.parseInt(rs.getString("Id")); 
//        System.out.println(rs.getInt("Id"));
//        ids[i] = (Integer) rs.getObject("Id");
//        ids[i] = rs.getInt("Id");
        ids[i] = rs.getInt("id");
        System.out.print(rs.getInt("id") + "\t");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ids; 
  }

  /**
   * Get all attributes of a single player
   * @param username of the desired player
   * @return String array of the information, [0] = Username, [1] = Email, [2] = Password
   */
  public String[] getPlayer(String username) {
    String[] player = new String[3]; 
    String selectData = new String("SELECT * FROM Player WHERE Player.Username = '" + username + "';"); 
    try {
      PreparedStatement stmt = m_dbConn.prepareStatement(selectData);
      ResultSet rs = stmt.executeQuery(selectData);
      
      while (rs.next()) {
        for (int i = 1; i <= 3; i++) {
          player[i - 1] = rs.getString(i) + "";
        }
      }
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return player;
  }

  /**
   * Get the number of all existing players currently in the DB. 
   * @return number of players in DB. 
   */
  public int getNumPlayers() {
    try {
      Statement stmt = m_dbConn.createStatement();
      ResultSet r = stmt.executeQuery("SELECT COUNT(*) FROM Player");
      r.next();
      return r.getInt("count(*)");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("made an oopsie");
      return -1;
    }
    
  }

  /**
   * Get a list of all player's primary keys in the DB. 
   * @return all player usernames
   */
  public String[] getAllPlayerUsernames() {
    String selectData = new String("SELECT Username FROM Player");
    String[] usernames = new String[getNumPlayers()];
//    System.out.println(getNumPlayers() + " Players in database: ");

    try {
      PreparedStatement stmt = m_dbConn.prepareStatement(selectData);
      ResultSet rs = stmt.executeQuery(selectData);

      // Each fetch for every player
      for (int i = 0; i < getNumPlayers(); i++) {
        if (!(rs.next())) {
          break;
        }
//        System.out.print(rs.getString("Username") + "\t");
        usernames[i] = rs.getString("Username");
      }
      System.out.println();
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return usernames; 
  }

  /**
   * Get the number of locations currently in the DB. 
   * @return number of locations in DB
   */
  public int getNumLocations() {
    try {
      Statement stmt = m_dbConn.createStatement();
      ResultSet r = stmt.executeQuery("SELECT COUNT(*) FROM Location");
      r.next();
      return r.getInt("count(*)");
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("made an oopsie");
      return -1;
    }
  }
  
  /**
   * Get all Location primary keys currently in the DB
   * @return int array of all Location ids in DB
   */
  public int[] getAllLocationIds() {
    String selectData = new String("SELECT IdNumber FROM Location");
    int[] ids = new int[getNumLocations()];
//    System.out.println(getNumLocations() + " locations in database: ");

    try {
      PreparedStatement stmt = m_dbConn.prepareStatement(selectData);
      ResultSet rs = stmt.executeQuery(selectData);

      // Each fetch for every player
      for (int i = 0; i < getNumLocations(); i++) {
        if (!(rs.next())) {
          break;
        }
//        System.out.print(rs.getInt("IdNumber") + "\t");
        ids[i] = rs.getInt("IdNumber");
      }
      System.out.println(); 
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ids;
  }
}
