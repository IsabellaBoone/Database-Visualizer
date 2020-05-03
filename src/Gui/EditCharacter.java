package Gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import database.RetrieveManipulateInformation;

public class EditCharacter extends JFrame {
  RetrieveManipulateInformation rmi = null;
  
  /*
   * To edit a character, we need to first fetch the character information. 
   * Then, we can pull up a panel that shows the characters current information
   * and also allow them to modify it. 
   */
  
  String name, user, 
    newName, newUser;
  
  int maxHP, curHP, str, stam, loc,
    newMax, newCur, newStr, newStam, newLoc;
  
  
  public EditCharacter(RetrieveManipulateInformation rmi, String name) {
    this.rmi = rmi; 
    this.name = name; 
    
    fetchInfo();
    editCharacterPrompt(); 
  }

  private void editCharacterPrompt() {
    setLayout(new GridLayout(0, 2)); 
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null); 
    Dimension d = new Dimension(500, 200);
    setMinimumSize(d);
    setPreferredSize(d);
    setMaximumSize(d);
    
    // Add buttons/prompts.  
    
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
        new JTextField(name),
        new JTextField(maxHP + ""),
        new JTextField(curHP + ""),
        new JTextField(str + ""),
        new JTextField(stam + ""),
        new JTextField(loc + ""),
        new JTextField(user)
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
        newName = txt[0].getText();
        newMax = Integer.parseInt(txt[1].getText());
        newCur = Integer.parseInt(txt[2].getText());
        newStr = Integer.parseInt(txt[3].getText());
        newStam = Integer.parseInt(txt[4].getText());
        newLoc = Integer.parseInt(txt[5].getText());
        newUser = txt[6].getText();
        
        // check make sure each number is valid
        if(newMax < 0) {
          fail("Max HP value invalid");
          return;
        } else if (newCur < 0) {
          fail("Current HP value invalid");
          return;
        } else if (newStr < 0) {
          fail("Strength value invalid"); 
          return;
        } else if(newStam < 0) {
          fail("Stamina value invalid");
          return;
        }
        
        if(!(rmi.playerExists(newUser))) {
          fail("Username does not exist. Create user before linking characters to that username.  ");
        } else if(!(rmi.locationExists(String.valueOf(loc)))) {
          // check if the location exists
          fail("Location does not exist");
          return;
        } else if(!(rmi.playerExists(user))) {
          // check if the user exists
          fail("Username does not exist");
          return;
        } else {
          // Finally, we've check all our failure conditions
          // and can properly add what the user inputs
          editCharacter(); 
          dispose(); 
          characterEditedSuccessfully(); 
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
  
  private void editCharacter() {
    try {
      Statement stmt = rmi.getConncetion().createStatement();
      String statement = "UPDATE Characters SET Name = \'" + newName + "\'" +
                          ", MaxHP = " + newMax + 
                          ", CurrentHP = " + newCur + 
                          ", Strength = " + newStr + 
                          ", Stamina = " + newStam + 
                          ", LocationId = " + newLoc + 
                          ", pUserName = \'" + newUser + "\'" +
                          " WHERE Name = \'" + name + "\';";
      stmt.execute(statement); 
      
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void fetchInfo() {
    try {
      String insert = "SELECT * FROM Characters WHERE Name = '" + name + "';";
      PreparedStatement stmt = rmi.getConncetion().prepareStatement(insert);
      stmt.execute();
      ResultSet rs = stmt.getResultSet(); 
      
      rs.next(); 
      
      user = rs.getString("pUserName"); 
      maxHP = rs.getInt("MaxHP"); 
      curHP = rs.getInt("CurrentHP"); 
      str = rs.getInt("Strength"); 
      stam = rs.getInt("Stamina"); 
      loc = rs.getInt("LocationId");
    } catch(SQLException e) { 
      fail("Fetch failed"); 
    }
  }
  private void characterEditedSuccessfully() {
    JFrame frame = new JFrame("Success");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(200,200);
    frame.setLocationRelativeTo(null);
    JLabel bad = new JLabel("Information successfully updated.");
    JButton cont = new JButton("Ok");
    cont.addActionListener(e -> frame.dispose());
    
    frame.setLayout(new FlowLayout());
    frame.add(bad);
    frame.add(cont);
    frame.pack();
    frame.setVisible(true);
  }
  private void fail(String reason) {
    JFrame frame = new JFrame("Error");
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.setSize(200,200);
    frame.setLocationRelativeTo(null);
    JLabel bad = new JLabel("Edit failed - " + reason);
    JButton cont = new JButton("Ok");
    cont.addActionListener(e -> frame.dispose());
    
    frame.setLayout(new FlowLayout());
    frame.add(bad);
    frame.add(cont);
    frame.pack();
    frame.setVisible(true);
    
  }
}
