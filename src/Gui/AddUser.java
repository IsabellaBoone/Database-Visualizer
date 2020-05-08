package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import database.AccessDatabase;

/**
 * Add a user to the database
 * @author Isabella Boone
 */
@SuppressWarnings("serial")
public class AddUser extends Panels {
  private String username, email, password; 
  
  /**
   * Prompts the user for a username, email and 
   *   password and tries to add that information
   *   to the database. 
   * @param access access to database.
   */
  public AddUser(AccessDatabase access) {
    setAccess(access); 

    // Make the panel pop up in the center of the screen
    setLocationRelativeTo(null); 
    
    JLabel[] prompt = {
        new JLabel("Enter Username: ", SwingConstants.CENTER),
        new JLabel("Enter Email: ", SwingConstants.CENTER),
        new JLabel("Enter Password: ", SwingConstants.CENTER)
    };
    
    JTextField[] txt = {
        new JTextField("Name"),
        new JTextField("abc@123.com"),
        new JTextField("password")
    };
    
    // Add all prompts to JPanel
    for(int i = 0; i < prompt.length; i++) {
      add(prompt[i]);
      add(txt[i]);
    }
    
    // Continue button
    JButton cont = new JButton("Continue");
    add(cont); 
    
    addListeners(txt, cont);
    
    pack(); 
    setVisible(true);
    
  }

  /**
   * Add actionlisteners to buttons
   * @param txt - needed to get text from
   * @param b - button to add listener to
   */
  private void addListeners(JTextField[] txt, JButton b) {
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        username = txt[0].getText(); 
        email = txt[1].getText();
        password = txt[2].getText(); 
        
        if(access.playerExists(username)) {
          fail("Username already exists.");
        } else {
          addUser(); 
          dispose();
          success(username + " added succesfully!");
        }
      }
    });
  }
  
  private void addUser() {
    try {
      String insert = "INSERT INTO Player (Username, Email, Password) "
          + "VALUES ('" + username + "', '" + email + "', '" + password + "');";
      java.sql.Statement stmt = access.getConnection().createStatement(); 
      stmt.execute(insert);
    } catch(SQLException e) {
      e.printStackTrace();
    }
  }
  
}
