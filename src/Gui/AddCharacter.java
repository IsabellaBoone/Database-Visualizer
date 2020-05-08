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
 * Add a character to the database
 * @author Isabella Boone
 *
 */
@SuppressWarnings("serial")
public class AddCharacter extends Panels {
  String name, user;
  int maxHP, curHP, str, stam, loc;
  public AddCharacter(AccessDatabase rmi) {
    setAccess(rmi); 
    
    setLocationRelativeTo(null); 
    
    // Add Labels & Buttons
    
    // All prompts
    JLabel[] prompt = {
        new JLabel("Enter Character Name:", SwingConstants.CENTER),
        new JLabel("Enter MaxHP: ", SwingConstants.CENTER),
        new JLabel("Enter current HP: ", SwingConstants.CENTER),
        new JLabel("Enter strength: ", SwingConstants.CENTER),
        new JLabel("Enter stamina: ", SwingConstants.CENTER),
        new JLabel("Enter location: ", SwingConstants.CENTER),
        new JLabel("Enter username: ", SwingConstants.CENTER)
    };
    
    // Textfields with default values
    JTextField[] txt = {
        new JTextField("Name"),
        new JTextField("000"),
        new JTextField("000"),
        new JTextField("000"),
        new JTextField("000"),
        new JTextField("000000000"),
        new JTextField("Username")
    };
    
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
   * Add actionlistener to the buttons
   * @param txt - needed to get text from textfields
   * @param b - button to add actionlistener to
   */
  private void addListeners(JTextField[] txt, JButton b) {
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        name = txt[0].getText();
        maxHP = Integer.parseInt(txt[1].getText());
        curHP = Integer.parseInt(txt[2].getText());
        str = Integer.parseInt(txt[3].getText());
        stam = Integer.parseInt(txt[4].getText());
        loc = Integer.parseInt(txt[5].getText());
        user = txt[6].getText();
        
        // check make sure each number is valid
        if(maxHP < 0) {
          fail("Max HP value invalid");
          return;
        } else if (curHP < 0) {
          fail("Current HP value invalid");
          return;
        } else if (str < 0) {
          fail("Strength value invalid"); 
          return;
        } else if(stam < 0) {
          fail("Stamina value invalid");
          return;
        }
        
        if(maxHP < curHP) {
          fail("Current HP cannot be greater than Max HP");
          return;
        }
        
        if(access.characterExists(name)) {
          // check to make sure name is valid and doesn't exist
          fail("Player name already exists");
          return;
        } else if(!(access.locationExists(String.valueOf(loc)))) {
          // check if the location exists
          fail("Location does not exist");
          return;
        } else if(!(access.playerExists(user))) {
          // check to see if the user exists
          fail("Username does not exist");
          return;
        } else {
          // Finally, we've check all our failure conditions
          // and can properly add what the user inputs
          addCharacter(); 
          dispose(); 
          success("Succesfully added " + user + "'s character " + name);
        }
      }
    });
  }
  
  /**
   * Add the character to the database after we
   * get input from the user that is valid.
   */
  private void addCharacter() {
    try {
      String insert = "INSERT INTO Characters (Name, MaxHP, CurrentHP, "
          + "Strength, Stamina, LocationId, pUserName) VALUES ('" + name + "', " +
        maxHP + ", " + curHP + ", " + str + ", " + stam + ", " + loc 
        + ", '" + user + "');";
        java.sql.Statement stmt = AccessDatabase.getConnection().createStatement(); 
        stmt.execute(insert);
    } catch(SQLException e) {
      e.printStackTrace();
      System.out.println("Error adding character");
    }
  }
  
}
