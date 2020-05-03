package Gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import database.RetrieveManipulateInformation;

public class AddUser extends JFrame {
  RetrieveManipulateInformation rmi = null;
  private String username, email, password; 
  public AddUser(RetrieveManipulateInformation rmi) {
    this.rmi = rmi; 
    
    setLayout(new GridLayout(0, 2)); 
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null); 
    Dimension d = new Dimension(850, 200);
    setMinimumSize(d);
    setPreferredSize(d);
    setMaximumSize(d);
    
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
        username = txt[0].getText(); 
        email = txt[1].getText();
        password = txt[2].getText(); 
        
        if(rmi.playerExists(username)) {
          fail("Username already exists.");
        } else {
          addUser(); 
          dispose();
          userAddedSuccessfully();
        }
      }
    });
  }
  
  private void addUser() {
    try {
      String insert = "INSERT INTO Player (Username, Email, Password) "
          + "VALUES ('" + username + "', '" + email + "', '" + password + "');";
      java.sql.Statement stmt = rmi.getConnection().createStatement(); 
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
  
  private void userAddedSuccessfully() {
    JFrame frame = new JFrame("Success");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(200,200);
    frame.setLocationRelativeTo(null);
    JLabel bad = new JLabel(username + " was added successfully!");
    JButton cont = new JButton("Ok");
    cont.addActionListener(e -> frame.dispose());
    
    frame.setLayout(new FlowLayout());
    frame.add(bad);
    frame.add(cont);
    frame.pack();
    frame.setVisible(true);
  }
}
