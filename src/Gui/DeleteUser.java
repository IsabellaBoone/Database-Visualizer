package Gui;

import javax.swing.JLabel;
import javax.swing.JTextField;

import database.RetrieveManipulateInformation;

public class DeleteUser extends Panels {
  public DeleteUser(RetrieveManipulateInformation rmi) {
    setRMI(rmi); 
    
    //prompt for username
    JLabel prompt = new JLabel("Enter the username of the player you"
        + " would like to delete: ");
    
    JTextField username = new JTextField("username");
    
    add(prompt);
    add(username);
  }
}
