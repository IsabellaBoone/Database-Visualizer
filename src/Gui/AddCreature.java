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
 * TODO: gridbaglayout on all frames, TODO: button functionality for all buttons
 * TODO: create item
 * 
 * @author Isabella (Joel)
 *
 */
public class AddCreature extends JFrame {
//(Id, curHP, MaxHP, Stamina, Strength, Protection, LocationId)
  RetrieveManipulateInformation rmi;
  int id, curHP, MaxHP, Stamina, Strength, Protection;

  String location = null;
  boolean locationGiven = false;
  // prompt for item type & item number
  // prompt for specifics of item type

  public AddCreature(RetrieveManipulateInformation rmi) {
    this.rmi = rmi;
    initializeP1();
  }

  public AddCreature(RetrieveManipulateInformation rmi, int location) {
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

    JLabel enterItemId = new JLabel("Enter Creature ID: ");
    JTextField itemId = new JTextField("Press enter to submit");
    itemId.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        id = Integer.parseInt((itemId.getText()));
      }
    });

    JLabel cLabel = new JLabel("Current HP: ");
    JTextField cTxt = new JTextField("000");

    JLabel mLabel = new JLabel("Max HP: ");
    JTextField mTxt = new JTextField("000");

    JLabel sLabel = new JLabel("Stamina: ");
    JTextField sTxt = new JTextField("000");

    JLabel sTLabel = new JLabel("Strength: ");
    JTextField sTTxt = new JTextField("000");

    JLabel pLabel = new JLabel("Protection: ");
    JTextField pTxt = new JTextField("000");

    JLabel locLabel;
    JTextField locTxt;

    if (!locationGiven) {
      locLabel = new JLabel("Item location: ");
      locTxt = new JTextField("000");
    } else {
      locLabel = new JLabel("Item location: ");
      locTxt = new JTextField(location);
    }

    add(enterItemId);
    add(itemId);
    add(cLabel);
    add(cTxt);
    add(mLabel);
    add(mTxt);
    add(sLabel);
    add(sTxt);
    add(sTLabel);
    add(sTTxt);
    add(pLabel);
    add(pTxt);
    add(locLabel);
    add(locTxt);

    JButton nextPanel = new JButton("Add");
    nextPanel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
//        System.out.println("itemType: " + itemType + " id: " + id + " itemIdExists: " + rmi.itemIdExists(id));
        // this if statement does not check to see if the id is already in the db, need
        // to do that and fix itemIdExists();
        id = Integer.parseInt(itemId.getText());

        curHP = Integer.parseInt(cTxt.getText());

        MaxHP = Integer.parseInt(mTxt.getText());

        Stamina = Integer.parseInt(sTxt.getText());

        Strength = Integer.parseInt(sTTxt.getText());

        Protection = Integer.parseInt(cTxt.getText());
        location = locTxt.getText();

//        rmi.itemIdExists(id);
        if ((id < 1) || curHP < 1 || MaxHP < 1 || Stamina < 1 || Strength < 1) {
          failedToSelect();
        } else {
//          rmi.createItem();
          addNewItem();
          dispose();
        }
      }
    });

    add(nextPanel);
    pack();
    setVisible(true);

  }

  /**
   * Will apear if user tries to continue without 1. entering a valid itemid, 2.
   * selecting item type
   */
  private void failedToSelect() {
    JFrame frame = new JFrame("Error");
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.setSize(200, 200);
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


  private void addNewItem() {
    try {
      Statement stmt = RetrieveManipulateInformation.getConncetion().createStatement();
      stmt.execute("INSERT INTO Creature VALUES (" + id + ", " + curHP + ", " + MaxHP + ", " + Stamina + ", " + Strength
          + ", " + Protection + ", " + (location.equals("(Optional)") ? "null" : location) + ");");
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
