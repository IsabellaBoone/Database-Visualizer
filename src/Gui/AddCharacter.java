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

public class AddCharacter extends JFrame{
  RetrieveManipulateInformation rmi = null;
  String name, user;
  int maxHP, curHP, str, stam, loc;
  public AddCharacter(RetrieveManipulateInformation rmi) {
    this.rmi = rmi; 
    // JFrame settings
    setLayout(new GridLayout(0, 2)); 
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null); 
    Dimension d = new Dimension(500, 200);
    setMinimumSize(d);
    setPreferredSize(d);
    setMaximumSize(d);
    
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
          characterAddedSuccessfully(); 
        }
      }
    });
    
    
    
    // These action listeners just print whenever you
    // press enter on a text field
    
    // name
    txt[0].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println(txt[0].getText());
      }
    });
    
    // max hp
    txt[1].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println(txt[1].getText());
      }
    });
    
    // current hp
    txt[2].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println(txt[2].getText());
      }
    });
    
    // strength
    txt[3].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println(txt[3].getText());
      }
    });
    
    // stamina
    txt[4].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println(txt[4].getText());
      }
    });
    
    // location
    txt[5].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println(txt[5].getText());
      }
    });
    
    // username
    txt[6].addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println(txt[6].getText());
      }
    });
  }
  
  private void addCharacter() {
    try {
      String insert = "INSERT INTO Characters (Name, MaxHP, CurrentHP, "
          + "Strength, Stamina, LocationId, pUserName) VALUES ('" + name + "', " +
        maxHP + ", " + curHP + ", " + str + ", " + stam + ", " + loc 
        + ", '" + user + "');";
        java.sql.Statement stmt = rmi.getConncetion().createStatement(); 
        stmt.execute(insert);
    } catch(SQLException e) {
      e.printStackTrace();
    }
  }
  
  private void fail(String reason) {
    JFrame frame = new JFrame("Error");
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.setSize(200,200);
    frame.setLocationRelativeTo(null);
    JLabel bad = new JLabel("Add failed - " + reason);
    JButton cont = new JButton("Ok");
    cont.addActionListener(e -> frame.dispose());
    
    frame.setLayout(new FlowLayout());
    frame.add(bad);
    frame.add(cont);
    frame.pack();
    frame.setVisible(true);
    
  }
  
  private void characterAddedSuccessfully() {
    JFrame frame = new JFrame("Success");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(200,200);
    frame.setLocationRelativeTo(null);
    JLabel bad = new JLabel(user + "'s character '" + name + "' was added successfully!");
    JButton cont = new JButton("Ok");
    cont.addActionListener(e -> frame.dispose());
    
    frame.setLayout(new FlowLayout());
    frame.add(bad);
    frame.add(cont);
    frame.pack();
    frame.setVisible(true);
  }
}
