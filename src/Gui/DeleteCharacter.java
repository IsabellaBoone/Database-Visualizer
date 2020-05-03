package Gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import database.RetrieveManipulateInformation;

public class DeleteCharacter extends JPanel{
  private RetrieveManipulateInformation rmi = null;
  private String name = null; 
  public DeleteCharacter(RetrieveManipulateInformation rmi, String name) {
    this.rmi = rmi;
    this.name = name; 
    
    confirmation(); 
  }

  private void confirmation() {
    JFrame frame = new JFrame("Confirmation");
    frame.setSize(200, 200);
    frame.setLocationRelativeTo(null);
    JLabel bad = new JLabel("Are you sure you want to delete '" + name + "'?");
    JButton cont = new JButton("Yes");
    cont.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
        delete();
      }
    });
    JButton no = new JButton("No");
    no.addActionListener(e -> frame.dispose());
  
    
    frame.setLayout(new FlowLayout());
    frame.add(bad);
    frame.add(cont);
    frame.add(no);
    frame.pack();
    frame.setVisible(true);
  }
  
  private void delete() {
    try {
      String statement = "DELETE FROM Characters WHERE Name = \'" + name + "\';";
      rmi.getConncetion().prepareStatement(statement).execute(statement);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
