package Gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
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
public class AddItem {
  RetrieveManipulateInformation rmi;
  int itemType = -1; 
  String id = null; 
  // prompt for item type & item number
  // prompt for specifics of item type
  
  public AddItem(RetrieveManipulateInformation rmi) {
    this.rmi = rmi;
    initializeP1();
  }
  
  /**
   * TODO: gridbaglayout, button functionality
   */
  private void initializeP1() {
    JFrame frame1 = new JFrame("Add Item");
    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame1.setSize(800,250);
    frame1.setLocationRelativeTo(null);
    frame1.setLayout(new GridBagLayout());
    
    JLabel enterItemId = new JLabel ("Enter Item ID: ");
    JTextField itemId = new JTextField("Press enter to submit"); 
    itemId.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        id = (itemId.getText()); 
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
    
    JLabel locLabel = new JLabel("Item location: ");
    JTextField locTxt = new JTextField("(Optional)");
    
    JLabel cNameLabel = new JLabel("Item belongs to: ");
    JTextField cNameTxt = new JTextField("(Optional)");
    
    frame1.add(enterItemId);
    frame1.add(itemId);
    frame1.add(enterType); 
    frame1.add(wepRb);
    frame1.add(armRb);
    frame1.add(contRb); 
    frame1.add(wLabel); 
    frame1.add(wTxt); 
    frame1.add(vLabel); 
    frame1.add(vTxt);
    frame1.add(locLabel);
    frame1.add(locTxt);
    frame1.add(cNameLabel); 
    frame1.add(cNameTxt); 
    
    JButton nextPanel = new JButton("Next"); 
    nextPanel.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent e) {
//        System.out.println("itemType: " + itemType + " id: " + id + " itemIdExists: " + rmi.itemIdExists(id));
        // this if statement does not check to see if the id is already in the db, need to do that and fix itemIdExists(); 
        id = itemId.getText(); 
//        rmi.itemIdExists(id);
        if((itemType == -1) || (id == null)) {
          failedToSelect(); 
        } else {
//          rmi.createItem();
          switch (itemType) {
          case (0):
            initWepFrame();
            frame1.dispose();
            break;
          case (1):
            initArmFrame();
            frame1.dispose();
            break;
          case (2):
            initContFrame();
            frame1.dispose();
            break;
          }
        }
      }
    });
    
    frame1.add(nextPanel);
    
    frame1.setVisible(true); 
    
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
    rmi.createWeapon(id); 
    
  }
  
  /**
   * Initialize add armor frame
   */
  private void initArmFrame() {

  }
  
  /**
   * Initialize add container frame
   */
  private void initContFrame() {

  }
  
}
