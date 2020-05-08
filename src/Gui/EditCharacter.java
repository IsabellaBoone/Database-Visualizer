package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import database.AccessDatabase;

/**
 * Edit a character in the database
 * @author Isabella Boone
 */
@SuppressWarnings("serial")
public class EditCharacter extends Panels {
  // Characters have 7 different fields, so we store
  // two arrays of [7] to hold old and new data.
  String[] oldInfo = new String[7], 
      newInfo = new String[7]; 
  
  public EditCharacter(AccessDatabase rmi, String name) {
    setAccess(rmi); 
    
    // Get information about 'name' 
    String select = "SELECT * FROM Characters WHERE Name = '" + name + "';",
          toGet[] = {"Name", "MaxHP", "CurrentHP", "Strength", 
              "Stamina", "LocationId", "pUserName"};
          
    oldInfo = fetchInfo(select, toGet);
    editCharacterPrompt(); 
  }

  private void editCharacterPrompt() {
    JLabel[] prompt = {
        new JLabel("Enter Character Name:", SwingConstants.CENTER),
        new JLabel("Enter MaxHP: ", SwingConstants.CENTER),
        new JLabel("Enter current HP: ", SwingConstants.CENTER),
        new JLabel("Enter strength: ", SwingConstants.CENTER),
        new JLabel("Enter stamina: ", SwingConstants.CENTER),
        new JLabel("Enter location: ", SwingConstants.CENTER),
        new JLabel("Enter username: ", SwingConstants.CENTER)
    };
    
    JTextField[] txt = {
        new JTextField(oldInfo[0]),
        new JTextField(oldInfo[1] + ""),
        new JTextField(oldInfo[2] + ""),
        new JTextField(oldInfo[3] + ""),
        new JTextField(oldInfo[4] + ""),
        new JTextField(oldInfo[5] + ""),
        new JTextField(oldInfo[6])
    };
    
    for(int i = 0; i < prompt.length; i++) {
      add(prompt[i]);
      add(txt[i]);
    }
    JButton cont = new JButton("Continue");
    add(cont); 
    
    addListeners(txt, cont);
    
    pack(); 
    setVisible(true);
    
  }
  
  private void addListeners(JTextField[] txt, JButton b) {
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < newInfo.length; i++) {
          newInfo[i] = txt[i].getText();
        }

        // check make sure each number is valid
        if (Integer.parseInt(newInfo[1]) < 0) {
          fail("Max HP value invalid");
          return;
        } else if (Integer.parseInt(newInfo[2]) < 0) {
          fail("Current HP value invalid");
          return;
        } else if (Integer.parseInt(newInfo[3]) < 0) {
          fail("Strength value invalid");
          return;
        } else if (Integer.parseInt(newInfo[4]) < 0) {
          fail("Stamina value invalid");
          return;
        } else if(Integer.parseInt(newInfo[1]) < Integer.parseInt(newInfo[2])) {
          fail("Current HP cannot be larger than Max HP.");
          return;
        }

        // Check to make sure player exists
        if (!(access.playerExists(newInfo[6]))) {
          fail("Username does not exist.  "
              + "Create a new player with that username before "
              + "linking characters to the username.");
          return;
        } else if (!(access.locationExists(newInfo[5]))) {
          fail("Location does not exist");
          return;
        } else if (!(oldInfo[0].contentEquals(newInfo[0])) && (access.characterExists(newInfo[0]))) {
          fail("Name does not exist");
          return;
        } else {
          // Finally, we've check all our failure conditions
          // and can properly add what the user inputs
          editCharacter();
          dispose();
          success("Character " + oldInfo[0] + " was successfully edited.");
        }
      }
    });
  }
  
  /**
   * Edit character, replacing old info with new info.  
   */
  private void editCharacter() {
    try {
      Statement stmt = AccessDatabase.getConnection().createStatement();
      String statement = "UPDATE Characters SET Name = \'" + newInfo[0] + "\'" +
                          ", MaxHP = " + newInfo[1] + 
                          ", CurrentHP = " + newInfo[2] + 
                          ", Strength = " + newInfo[3] + 
                          ", Stamina = " + newInfo[4] + 
                          ", LocationId = " + newInfo[5] + 
                          ", pUserName = \'" + newInfo[6] + "\'" +
                          " WHERE Name = \'" + oldInfo[0] + "\';";
      stmt.execute(statement); 
      
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
