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

/**
 * 
 * @author Isabella
 *
 */
public class AddUser extends Panels {
  private String username, email, password; 
  public AddUser(RetrieveManipulateInformation rmi) {
    setRMI(rmi); 
    
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
          success(username + " added succesfully!");
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
  
}
