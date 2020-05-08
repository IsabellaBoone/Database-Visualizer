package Gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import database.AccessDatabase;

/**
 * Contains essentials for adding, removing, and deleting 
 * information from the database.  
 * 
 * Contains fail, success, and fetchInfo.
 * @author Isabella Boone
 *
 */
@SuppressWarnings("serial")
public abstract class Panels extends JFrame{
  AccessDatabase access = null;
  
  /*
   * Initialize the panel
   */
  public Panels() {
    setLayout(new GridLayout(0, 2)); 
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null); 
    Dimension d = new Dimension(500, 200);
    setMinimumSize(d);
    setPreferredSize(d);
    setMaximumSize(d);
  }
  
  /**
   * Set access to database
   * @param access = connection to database
   */
  public void setAccess(AccessDatabase access) {
    this.access = access; 
  }
  
  /**
   * If a user enters some sort of invalid input, 
   * calling this function with a reason will
   * display to the user that they have input 
   * incorrect information and tell them why
   * it failed.  
   * @param reason - reason for fail, ex primary key already exists
   */
  public void fail(String reason) {
    JFrame frame = new JFrame("Error");
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.setSize(200,200);
    frame.setLocationRelativeTo(null);
    JLabel bad = new JLabel("Failed - " + reason);
    JButton cont = new JButton("Ok");
    cont.addActionListener(e -> frame.dispose());
    
    frame.setLayout(new FlowLayout());
    frame.add(bad);
    frame.add(cont);
    frame.pack();
    frame.setVisible(true);
  }
  
  /**
   * If a user has input correct input and we were successfully
   * able to add the information to the database, call this
   * with "statement" to display exactly what they added.  
   * @param statement - how they succeeded.  ex - "Username's Character succesfully added." 
   */
  public void success(String statement) {
    JFrame frame = new JFrame("Success");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(200,200);
    frame.setLocationRelativeTo(null);
    JLabel success = new JLabel(statement);
    JButton ok = new JButton("Ok");
    ok.addActionListener(e -> frame.dispose());
    
    frame.setLayout(new FlowLayout());
    frame.add(success);
    frame.add(ok);
    frame.pack();
    frame.setVisible(true);
  }
  
  /**
   * Fetch info from the database.  Very versatile function.
   * @param select Select statement to query the database with.
   * @param toGet Column names to get, ex {"Username", "Email", "Password"}
   * @return String[] of every result from what toGet gave. 
   */
  String[] fetchInfo(String select, String[] toGet) {
    String[] ret = new String[toGet.length];
    try {
      ResultSet rs = AccessDatabase.getConnection().prepareStatement(select).executeQuery(select);
      rs.next(); 
      for(int i = 0; i < toGet.length; i++) {
        ret[i] = "" + rs.getObject(toGet[i]);
      }
    } catch(SQLException e) {
      e.printStackTrace();
      fail("Fetch Failed"); 
    }
    return ret;
  }
  
}
