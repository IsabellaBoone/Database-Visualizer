// fuck

package Gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import database.RetrieveManipulateInformation;

public class CharactersLayout extends JPanel {
  private RetrieveManipulateInformation rmi;
  private JPanel characterPanel, statsPanel;
  private String[] characterNames;
  private String selectedName;
  private JLabel selectedNameJLabel = null;

  public CharactersLayout(RetrieveManipulateInformation rmi) {
    this.rmi = rmi;

    initializeJPanel();
  }

  private void initializeJPanel() {
    setBackground(Color.DARK_GRAY);
    setLayout(new GridBagLayout());
    characterNames = rmi.getAllCharacterNames();
    selectedName = characterNames[0]; 
    GridBagConstraints c = new GridBagConstraints();

    c.gridx = 0;
    c.gridy = 0;
    c.weighty = 0.33;
    c.weightx = 0.95;
    characterPanel = genCharacterPanel();
    add(characterPanel, c);
    c.gridx++;
    statsPanel = genStatsPanel(); 
    add(statsPanel);
    
  }

  private JPanel genCharacterPanel() {
    // Entire panel, that will have header and panel of names
    JPanel panel = new JPanel();
    panel.setBackground(Color.DARK_GRAY);
    panel.setLayout(new BorderLayout());
    
    // Size for each panel
    Dimension d = new Dimension(450, 700);
    panel.setMinimumSize(d);
    panel.setPreferredSize(d);
    panel.setMaximumSize(d);

    // Scrollbar 
    JScrollPane names = new JScrollPane();
    names.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    names.add(names.createVerticalScrollBar());

    // Panel of names
    JPanel characters = new JPanel();
    characters.setBackground(Color.LIGHT_GRAY);
    characters.setLayout(new GridLayout(rmi.getNumCharacters(), 1));

    // Initialize character names
    characterNames = rmi.getAllCharacterNames();

    panel.add(new JLabel("<html><H1 Style = \"color:white; font-size: 25px\">" + "Characters:" + "</H1></html>"),
        BorderLayout.NORTH);

    for (int i = 0; i < characterNames.length; i++) {
      final int x = i;
      String format = "<html><body style = \"color:black; font-size: 20px\">" + characterNames[i] + "</body></html>";
      JLabel label = new JLabel(format, SwingConstants.CENTER);
      label.setOpaque(true);
      label.setBackground(Color.LIGHT_GRAY);
      label.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
          removeSelectedBackgroundChars(); 
          label.setBackground(new Color(234,201,55));
          selectedNameJLabel = label; 
          selectedName = characterNames[x];
          refreshStatsPanel(); 
        }
      });
      characters.add(label); 
    }
    
    names.setViewportView(characters);
    panel.add(names, BorderLayout.CENTER);
    return panel;
  }

  private JPanel genStatsPanel() {
    // Entire panel, that will have header and panel of names
    JPanel panel = new JPanel();
    panel.setBackground(Color.DARK_GRAY);
    panel.setLayout(new BorderLayout());
    
    // Size for each panel
    Dimension d = new Dimension(450, 700);
    panel.setMinimumSize(d);
    panel.setPreferredSize(d);
    panel.setMaximumSize(d);

    // Panel of stats
    JPanel stats = new JPanel();
    stats.setBackground(Color.LIGHT_GRAY);
    stats.setLayout(new GridLayout(5, 1));

    // Fetch stats
    String[] charStats = rmi.getCharacterStats(selectedName);

    panel.add(new JLabel("<html><H1 Style = \"color:white; font-size: 25px\">" + "Stats:" + "</H1></html>"),
        BorderLayout.NORTH);
    
    // Format strings
    String HP = charStats[0] + "/" + charStats[1] + "HP";
    String strStam = charStats[2] + " Strength " + charStats[3] + " Stamina"; 
    String loc = rmi.getLocType(charStats[4]) + ": " + charStats[4];
    String user = charStats[5]; 

    // Add JLabel
    JLabel hpLabel = new JLabel(HP, SwingConstants.CENTER);
    JLabel strStamLabel = new JLabel(strStam, SwingConstants.CENTER);
    JLabel locLabel = new JLabel(loc, SwingConstants.CENTER);
    JLabel userLabel = new JLabel(user, SwingConstants.CENTER);
    
    stats.add(hpLabel);
    stats.add(strStamLabel);
    stats.add(locLabel);
    stats.add(userLabel);
    
    panel.add(stats, BorderLayout.CENTER);
    return panel;
  }
  
  private void refreshStatsPanel() {
    statsPanel = genStatsPanel();
    SwingUtilities.updateComponentTreeUI(statsPanel);

    validate();
    repaint();
  }

  private void removeSelectedBackgroundChars() {
    if (selectedNameJLabel != null)
      selectedNameJLabel.setBackground(Color.LIGHT_GRAY);
  } 
}
