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
  Connection m_dbConn = null;

  public retrieveInformation(Connection con) {
    this.m_dbConn = con;
    
//    getAllPlayerUsernames();
//    getAllLocationIds(); 
//    String[] items = getAllItems();
//    for(int i = 0; i < items.length; i++) {
//      System.out.print(items[i] + "\t");
//    }
  }

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

  // Get information for a single player.
  public String[] getPlayer(String username) {
    return null;
  }

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

  /*
   * Get a list of all players in a database, including their
   * username, email, and password in a 2d array. 
   * (2d array part is not yet working but we're getting there.)
   */
  public void getAllPlayerUsernames() {
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
  }

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
  
  /*
   * Get a list of all players in a database, including their
   * username, email, and password in a 2d array. 
   * (2d array part is not yet working but we're getting there.)
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
