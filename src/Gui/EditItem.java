package Gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;

import database.RetrieveManipulateInformation;

public class EditItem extends JFrame{
  public EditItem() {
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent arg0) {
        
        System.exit(0);
      }
    });
    
    pack();
    setVisible(true);
  }
}
