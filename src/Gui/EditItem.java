package Gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import database.RetrieveManipulateInformation;

public class EditItem extends JFrame{
  
  private ResultSet rs;
  
  public EditItem(ResultSet rs) {
    rs = this.rs;
    
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent arg0) {
        System.out.print(newItem());
        System.exit(0);
      }
    });
    
    pack();
    setVisible(true);
  }
  
  private ResultSet newItem() {
    ResultSet newRS = null;
    return newRS;
  }
}
