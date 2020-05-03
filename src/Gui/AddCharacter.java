package Gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import database.RetrieveManipulateInformation;

public class AddCharacter extends Panels {
  String name, user;
  int maxHP, curHP, str, stam, loc;
  public AddCharacter(RetrieveManipulateInformation rmi) {
    setRMI(rmi); 
    
    // Add Labels & Buttons
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
    
    JButton cont = new JButton("Continue");
    add(cont); 
    
    addButtons(txt, cont);
    
    pack(); 
    setVisible(true);
    
  }
  
  private void addButtons(JTextField[] txt, JButton b) {
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
        
        if(rmi.characterExists(name)) {
          // check to make sure name is valid and doesn't exist
          fail("Player name already exists");
          return;
        } else if(!(rmi.locationExists(String.valueOf(loc)))) {
          // check if the location exists
          fail("Location does not exist");
          return;
        } else if(!(rmi.playerExists(user))) {
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
  
  private void addCharacter() {
    try {
      String insert = "INSERT INTO Characters (Name, MaxHP, CurrentHP, "
          + "Strength, Stamina, LocationId, pUserName) VALUES ('" + name + "', " +
        maxHP + ", " + curHP + ", " + str + ", " + stam + ", " + loc 
        + ", '" + user + "');";
        java.sql.Statement stmt = rmi.getConnection().createStatement(); 
        stmt.execute(insert);
    } catch(SQLException e) {
      e.printStackTrace();
    }
  }
  
}
