package Gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import database.RetrieveManipulateInformation;
import javax.swing.JPanel;

//Joel Gingrich
public class LocationLayout extends JPanel {

  private RetrieveManipulateInformation rmi;

  private JLabel selectedLocationJLabel;
  private JPanel curLocationPanel = null, curStatsPanel = null;
  private GridBagConstraints locPanel, statPanel;
  private String[] locationNames;

  public LocationLayout(RetrieveManipulateInformation rmi) {
    this.rmi = rmi;

    initializeJPanel();
  }

  private void initializeJPanel() {
    setBackground(Color.DARK_GRAY);
    setLayout(new GridBagLayout());

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 0.33;
    c.weighty = 0.95;
    add(buildLocationPanel(),c);
  }
  
  private JPanel buildLocationPanel() {
    JPanel panel = new JPanel();
    panel.setBackground(new Color(132, 132, 132));
    panel.setLayout(new GridBagLayout());
    
    locationNames = rmi.getAllAreaTypes();
    for(int i = 0; i < locationNames.length; i++) {
      System.out.println(locationNames[i]);
    }
    return panel;
    
  }
}
