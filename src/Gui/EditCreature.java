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

import database.AccessDatabase;

public class EditCreature extends JFrame {

  private ResultSet rs;
  private AccessDatabase rmi;
  private int id, oldId, curHP, MaxHP, Stamina, Strength, Protection, locId;
  private String cName;

  JTextField cTxt;
  JTextField mTxt;
  JTextField sTxt;
  JTextField sTTxt;
  JTextField pTxt;
  JTextField locTxt;
  JTextField crTxt;

  public EditCreature(ResultSet rs, AccessDatabase r) {
    this.rs = rs;
    rmi = r;
    try {

      id = rs.getInt("IdNumber");
      oldId = id;
      curHP = rs.getInt("CurrentHP");
      MaxHP = rs.getInt("MaxHP");
      Stamina = rs.getInt("Stamina");
      Strength = rs.getInt("Strength");
      Protection = rs.getInt("Protection");
      try {
        locId = rs.getInt("LocationId");
      } catch (NullPointerException e) {
        locId = 0;
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    initialize();

  }

  private void initialize() {
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new GridBagLayout());

    JLabel enterItemId = new JLabel("Enter Creature ID: ");
    crTxt = new JTextField("" + id);
    crTxt.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        id = Integer.parseInt((crTxt.getText()));
      }
    });

    JLabel mLabel = new JLabel("Max HP: ");
    mTxt = new JTextField("" + MaxHP + " ");

    JLabel cLabel = new JLabel("Current HP: ");
    cTxt = new JTextField("" + curHP + " ");

    JLabel sLabel = new JLabel("Stamina: ");
    sTxt = new JTextField("" + Stamina + " ");

    JLabel sTLabel = new JLabel("Strength: ");
    sTTxt = new JTextField("" + Strength + " ");

    JLabel pLabel = new JLabel("Protection: ");
    pTxt = new JTextField("" + Protection + " ");

    JLabel locLabel = new JLabel("Location: ");
    locTxt = new JTextField("" + locId + " ");

    add(enterItemId);
    add(crTxt);
    add(mLabel);
    add(mTxt);
    add(cLabel);
    add(cTxt);
    add(sLabel);
    add(sTxt);
    add(sTLabel);
    add(sTTxt);
    add(pLabel);
    add(pTxt);
    add(locLabel);
    add(locTxt);

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
    id = Integer.parseInt(crTxt.getText().strip());

    curHP = Integer.parseInt(cTxt.getText().strip());

    MaxHP = Integer.parseInt(mTxt.getText().strip());

    Stamina = Integer.parseInt(sTxt.getText().strip());

    Strength = Integer.parseInt(sTTxt.getText().strip());

    Protection = Integer.parseInt(cTxt.getText().strip());

    locId = Integer.parseInt(locTxt.getText().strip());

    try {
      Statement stmt = AccessDatabase.getConnection().createStatement();

      stmt.execute("UPDATE Creature SET IdNumber = " + id + ", CurrentHP =" + curHP + ", MaxHP = " + MaxHP + ", Stamina = " + Stamina + ", Strength = " + Strength
          + ", Protection = " + Protection + ", LocationId = " + locId + " WHERE IdNumber = " + oldId + ";");
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
