package Gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import database.RetrieveManipulateInformation;

public class EditItem extends JFrame{
  
  private ResultSet rs;
  private RetrieveManipulateInformation rmi;
  private int oldId;
  private int id;
  private int volume;
  private int weight;
  private int locId;
  private String cName;
  
  
  JTextField wTxt;
  JTextField vTxt;
  JTextField locTxt;
  JTextField itemId;
  JTextField cNameTxt; 
  
  public EditItem(ResultSet rs, RetrieveManipulateInformation r) {
    this.rs = rs;
    rmi = r;
    try {
      
      id = rs.getInt("ItemId");
      oldId = id;
      volume = rs.getInt("volume");
      weight = rs.getInt("weight");
      try {
        locId = rs.getInt("LocationId");
      }catch(NullPointerException e) {
        locId = 0;
      }

        cName = rs.getString("cName");
        System.out.println(cName);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    initialize();
    
  }
  
  private void initialize() {
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new GridBagLayout());
    
    JLabel enterItemId = new JLabel ("Enter Item ID: ");
    itemId = new JTextField("" + id); 
    itemId.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        id = Integer.parseInt((itemId.getText())); 
      }
    });
    
    JLabel wLabel = new JLabel("Item weight: ");
    wTxt = new JTextField("" + weight + " "); 
    
    JLabel vLabel = new JLabel("Item volume: ");
    vTxt = new JTextField("" + volume + " ");
    JLabel locLabel;
   
   
    locLabel = new JLabel("Item location: ");
    locTxt = new JTextField("" + locId);
   
    
    
    JLabel cNameLabel = new JLabel("Item belongs to: ");
    cNameTxt = new JTextField((cName == null ? "null      " : cName));
    
    add(enterItemId);
    add(itemId);
    add(wLabel); 
    add(wTxt); 
    add(vLabel); 
    add(vTxt);
    add(locLabel);
    add(locTxt);
    add(cNameLabel); 
    add(cNameTxt); 
    
    JButton change = new JButton("Change"); 
    change.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) {
        change();
        dispose();
      }
    });
    
    add(change);
    pack();
    setVisible(true);
  }
  
  private void change() {
    id = Integer.parseInt(itemId.getText());
    volume = Integer.parseInt(vTxt.getText().substring(0, vTxt.getText().indexOf(" ")));
    weight = Integer.parseInt(wTxt.getText().substring(0, wTxt.getText().indexOf(" ")));
    cName = cNameTxt.getText();  
    String loc;
    try {
      locId = Integer.parseInt(locTxt.getText());
      loc = "" +locId;
    }catch(NullPointerException e) {
      loc = "null";
    }
    
    try {
      Statement stmt = rmi.getConncetion().createStatement();
      
      stmt.execute("UPDATE ITEM SET ITEMID = " + id + ", VOLUME = " + volume + ", WEIGHT = " + weight + ", LOCATIONID = " + loc + ", cName = " + 
      (cName.equals("") || cName.equals("null")? "null" : "\'" + cName + "\'")
      + " WHERE ITEMID = " + oldId + ";");
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }
}
  
