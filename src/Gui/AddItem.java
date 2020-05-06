package Gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Window.*; 
import java.awt.Frame.*;

import javax.swing.*;

import database.RetrieveManipulateInformation;

/**
 * TODO: gridbaglayout on all frames,
 * TODO: button functionality for all buttons
 * TODO: create item 
 * @author Isabella
 *
 */
public class AddItem extends JFrame{
  RetrieveManipulateInformation rmi;
  int itemType = -1; 
  int id; 
  int volume;
  int weight;
  String character = null;
  String location = null;
  boolean locationGiven = false;
  // prompt for item type & item number
  // prompt for specifics of item type
  
  public AddItem(RetrieveManipulateInformation rmi) {
    this.rmi = rmi;
    initializeP1();
  }
  
  public AddItem(RetrieveManipulateInformation rmi, int location) {
    this.rmi = rmi;
    this.location = Integer.toString(location);
    locationGiven = true;
    initializeP1();
  }
  
  /**
   * TODO: gridbaglayout, button functionality
   */
  private void initializeP1() {
    
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new GridBagLayout());
    
    JLabel enterItemId = new JLabel ("Enter Item ID: ");
    JTextField itemId = new JTextField("Press enter to submit"); 
    itemId.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        id = Integer.parseInt((itemId.getText())); 
      }
    });
    
    JLabel enterType = new JLabel("Enter item type: ");
    JRadioButton wepRb = new JRadioButton("Weapon");
    wepRb.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        itemType = 0;
        System.out.println("Selected Weapon");
      }
    });
    JRadioButton armRb = new JRadioButton("Armor");
    armRb.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        itemType = 1;
        System.out.println("Selected Armor");
      }
    });
    JRadioButton contRb = new JRadioButton("Container");
    contRb.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        itemType = 2;
        System.out.println("Selected Container");
      }
    });
    
    JLabel wLabel = new JLabel("Item weight: ");
    JTextField wTxt = new JTextField("000"); 
    
    JLabel vLabel = new JLabel("Item volume: ");
    JTextField vTxt = new JTextField("000");
    JLabel locLabel;
    JTextField locTxt;
    
    if(!locationGiven) {
      locLabel = new JLabel("Item location: ");
       locTxt = new JTextField("(Optional)");
    } else {
      locLabel = new JLabel("Item location: ");
      locTxt = new JTextField(location);
    }
    
    
    
    JLabel cNameLabel = new JLabel("Item belongs to: ");
    JTextField cNameTxt = new JTextField("(Optional)");
    
    add(enterItemId);
    add(itemId);
    add(enterType); 
    add(wepRb);
    add(armRb);
    add(contRb); 
    add(wLabel); 
    add(wTxt); 
    add(vLabel); 
    add(vTxt);
    add(locLabel);
    add(locTxt);
    add(cNameLabel); 
    add(cNameTxt); 
    
    JButton nextPanel = new JButton("Next"); 
    nextPanel.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) {
//        System.out.println("itemType: " + itemType + " id: " + id + " itemIdExists: " + rmi.itemIdExists(id));
        // this if statement does not check to see if the id is already in the db, need to do that and fix itemIdExists(); 
        id = Integer.parseInt(itemId.getText());

        volume = Integer.parseInt(vTxt.getText());

        weight = Integer.parseInt(wTxt.getText());
        
        character = cNameTxt.getText();
        
        location = locTxt.getText();
        
//        rmi.itemIdExists(id);
        if((itemType == -1) || (id < 1) || volume < 1 || weight < 1) {
          failedToSelect(); 
        } else {
//          rmi.createItem();
          switch (itemType) {
          case (0):
            try{
              location = "" + Integer.parseInt(location); 
            }catch(NumberFormatException nfe){
              location = "null";
            } 
            initWepFrame();
            addNewItem();
            
            break;
          case (1):
            try{
              location = "" + Integer.parseInt(location); 
            }catch(NumberFormatException nfe){
              location = "null";
            } 
            initArmFrame();
            addNewItem();
            break;
          case (2):
            try{
              location = "" + Integer.parseInt(location); 
            }catch(NumberFormatException nfe){
              location = "null";
            } 
            initContFrame();
            addNewItem();
            break;
          }
          dispose();
        }
      }
    });
    
    add(nextPanel);
    pack();
    setVisible(true); 
    
  }
  
  /**
   * Will apear if user tries to continue without 
   * 1. entering a valid itemid, 
   * 2. selecting item type 
   */
  private void failedToSelect() {
    JFrame frame = new JFrame("Error");
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
    frame.setSize(200,200);
    frame.setLocationRelativeTo(null);
    JLabel bad = new JLabel("Failed to enter valid item id, or select item type.");
    JButton cont = new JButton("Ok");
    cont.addActionListener(e -> frame.dispose()); 
    frame.setLayout(new FlowLayout());
    frame.add(bad); 
    frame.add(cont); 
    frame.pack();
    frame.setVisible(true);
  }
  
  /**
   * Initialize create weapon frame. 
   */
  private void initWepFrame() {
    JFrame frame = new JFrame(); 
    frame.setSize(200,100);
    frame.setLayout(new FlowLayout());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    frame.add(new JLabel("Weapon created."));
    JButton b = new JButton("Ok");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
      }
    });
    frame.add(b);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
//    rmi.createWeapon(id); 
    
  }
  
  /**
   * Initialize add armor frame
   */
  private void initArmFrame() {
    JFrame frame = new JFrame(); 
    frame.setSize(200,100);
    frame.setLayout(new FlowLayout());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    frame.add(new JLabel("Armor Created."));
    JButton b = new JButton("Ok");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
      }
    });
    frame.add(b);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
  
  /**
   * Initialize add container frame
   */
  private void initContFrame() {
    JFrame frame = new JFrame(); 
    frame.setSize(200,100);
    frame.setLayout(new FlowLayout());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    frame.add(new JLabel("Container created."));
    JButton b = new JButton("Ok");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
      }
    });
    frame.add(b);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
  
  private void addNewItem() {
    try {
      Statement stmt = RetrieveManipulateInformation.getConnection().createStatement();
      stmt.execute("INSERT INTO ITEM VALUES (" + id + ", " + weight + ", " + volume + ", " + location + ", "
          + (character.equals("(Optional)") ? "null" : character)  + ");");
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
}
