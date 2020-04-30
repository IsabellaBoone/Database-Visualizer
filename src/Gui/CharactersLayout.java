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
import javax.swing.SwingConstants;

import database.RetrieveManipulateInformation;

public class CharactersLayout extends JPanel {
  private RetrieveManipulateInformation rmi;// used for replacing manipulating information ya know
  private JLabel playerJLabel[], charactersJLabel[], // hold every single label in characters n players
  
  selectedPlayerJLabel, // Needed for highlighting selected player
  selectedCharacterJLabel; // Needed for highlighting selected character
  private String selectedPlayerUsername = null, selectedCharacterName = null; // maybe not needed
  
  private JPanel userPanel, charPanel, statPanel; 
  private String[] playerUsernames, charNames;
  
  public CharactersLayout(RetrieveManipulateInformation rmi) {
    this.rmi = rmi;
    
    initializeJPanel(); 
  }
  
  private void initializeJPanel() {
	playerUsernames = rmi.getAllPlayerUsernames();
	  
    setBackground(Color.DARK_GRAY );
    setLayout(new GridLayout(3, 1));
    
    userPanel = genUsernamePanel(); 
    add(userPanel); 

    charPanel = genCharacterPanel(); 
    add(charPanel);
    
    statPanel = genStatsPanel(); 
    add(statPanel);
    
  }
  
  private void refreshJPanel() {
	  remove(userPanel);
	  remove(charPanel); 
	  remove(statPanel); 
	  
	  userPanel = genUsernamePanel(); 
	  add(userPanel);
	  
	  charPanel = genCharacterPanel(); 
	  add(charPanel); 
	  
	  statPanel = genStatsPanel(); 
	  add(statPanel); 
  }
  
  private JPanel genUsernamePanel() {
    JPanel panel = new JPanel(); 
    panel.setBackground(Color.DARK_GRAY);
    panel.setLayout(new GridBagLayout()); 
    Dimension d = new Dimension(375, 600);
    panel.setMinimumSize(d);
    panel.setPreferredSize(d);
    panel.setMaximumSize(d);
    
    playerJLabel = new JLabel[rmi.getNumPlayers()];
    
    GridBagConstraints c = new GridBagConstraints(); 
    c.gridx = 0;
    c.gridy = 0; 
    c.weightx = 0.95;
    c.ipady = 20; 
    panel.add(new JLabel("<html><H1 Style = \"color:#808080; font-size: 25px\">" + "Players:" + "</H1></html>"));
    
    c.gridy++; 
    
    // Add all usernames
    for(int i = 0; i < playerUsernames.length; i++) {
      // HTML Formatting for JLabel, then add mouse listener.
      String format = "<html><body style = \"color:white; font-size: 20px\">" + playerUsernames[i] + "</body></html>"; // text with html formatting
      playerJLabel[i] = new JLabel(format, SwingConstants.CENTER);
      playerJLabel[i].addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
        	if(selectedPlayerJLabel != null)
        		removeSelectedBackgroundPlayer(); 
          
          for(int j = 0; j < playerJLabel.length; j++) {
            if(e.getSource() == playerJLabel[j]) {
              selectedPlayerJLabel = playerJLabel[j]; 
              selectedPlayerUsername = playerUsernames[j]; // Store the string for later
              playerJLabel[j].setOpaque(true);
              playerJLabel[j].setBackground(new Color(234, 201, 55));
              break;
            }
          }

          refreshCharacterPanel(); 
        }
      });
      panel.add(playerJLabel[i], c);
      c.gridy++; 
    }
    
    if(selectedPlayerJLabel == null) {
    	selectedPlayerJLabel = playerJLabel[0]; 
    	selectedPlayerUsername = playerUsernames[0]; // Store the string for later
        playerJLabel[0].setOpaque(true);
        playerJLabel[0].setBackground(new Color(234, 201, 55));
    }
    
    return panel; 
  }
  
  /**
   * Generates characters JPanel from selectedPlayer variable.
   * @return JPanel of all characters under the selected username. 
   */
  private JPanel genCharacterPanel() {
    JPanel panel = new JPanel(); 
    
    panel.setBackground(Color.DARK_GRAY);
    setLayout(new GridBagLayout());
    
    Dimension d = new Dimension(375, 600);
    panel.setMinimumSize(d);
    panel.setPreferredSize(d);
    panel.setMaximumSize(d);
    
    if(selectedPlayerUsername == null) {
      charNames = rmi.getAllCharacterNamesFromUser(playerUsernames[0]);
      charactersJLabel = new JLabel[rmi.getNumChars(playerUsernames[0])];
    } else {
      charNames = rmi.getAllCharacterNamesFromUser(selectedPlayerUsername);
      charactersJLabel = new JLabel[rmi.getNumChars(selectedPlayerUsername)];
    }
    
    GridBagConstraints c = new GridBagConstraints(); 
    c.fill = GridBagConstraints.RELATIVE;
    c.gridx = 0;
    c.gridy = 0; 
    c.weightx = 0.95;
    
    c.gridy++; 
    
    if(selectedPlayerUsername == null) {
      panel.add(new JLabel("<html><H1 Style = \"color:#808080; font-size: 25px\">" + playerUsernames[0] + "'s Characters:" + "</H1></html>"));
      System.out.print(playerUsernames[0] + ": ");
    } else {
      panel.add(new JLabel("<html><H1 Style = \"color:#808080; font-size: 25px\">" + selectedPlayerUsername + "'s Characters:" + "</H1></html>"));
      System.out.print(selectedPlayerUsername + ": ");
    }
    
    for(int i = 0; i < charNames.length; i++) {
   // HTML Formatting for JLabel, then add mouse listener.
      System.out.print(charNames[i] + "\t");
      String format = "<html><body style = \"color:white; font-size: 20px\">" + charNames[i] + "</body></html>"; // text with html formatting
      charactersJLabel[i] = new JLabel(format, SwingConstants.CENTER);
      charactersJLabel[i].addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          // Remove background from selected player
          removeSelectedBackgroundChars(); 
          
          // Find what label the user selected
          for(int j = 0; j < charactersJLabel.length; j++) {
            if(e.getSource() == charactersJLabel[j]) {
              selectedCharacterJLabel = charactersJLabel[j]; 
              selectedCharacterName = charNames[j]; // Store the string for later
              charactersJLabel[j].setOpaque(true);
              charactersJLabel[j].setBackground(new Color(234, 201, 55));
              break;
            }
          }
          refreshJPanel(); 
        }
      });
    
      if(selectedCharacterJLabel == null) {
    	  selectedCharacterJLabel = charactersJLabel[0]; 
          selectedCharacterName = charNames[0]; // Store the string for later
          charactersJLabel[0].setOpaque(true);
          charactersJLabel[0].setBackground(new Color(234, 201, 55));
      }
      
      panel.add(charactersJLabel[i], c);
      c.gridy++; 
    }
    
    return panel;
  }
  
  private JPanel genStatsPanel() {
    JPanel panel = new JPanel(); 
    Dimension d = new Dimension(375, 600);
    panel.setMinimumSize(d);
    panel.setPreferredSize(d);
    panel.setMaximumSize(d);
    panel.setLayout(new GridBagLayout());
    
    String[] charStats;
    if(selectedCharacterName == null) {
    	charStats = rmi.getCharacterStats(charNames[0]);
    } else {
    	charStats = rmi.getCharacterStats(selectedCharacterName);
    }
    
    String hp = charStats[0] + "/" + charStats[1] + "HP";
    String loc = rmi.getLocType(charStats[4]); 
    String str = charStats[3] + " Strength"; 
    String stam = charStats[2] + " Stamina"; 
    
    JLabel hpJLabel = new JLabel(hp); 
    JLabel locJLabel = new JLabel(loc); 
    JLabel strJLabel = new JLabel(str); 
    JLabel stamJLabel = new JLabel(stam); 
    
    GridBagConstraints c = new GridBagConstraints(); 
    c.gridx = 0;
    c.gridy = 0;
    panel.add(hpJLabel, c);
    c.gridy++; 
    panel.add(locJLabel, c);
    c.gridy++; 
    panel.add(strJLabel, c);
    c.gridy++; 
    panel.add(stamJLabel, c);
    
    return panel;
  }
  
  private void refreshCharacterPanel() { 
    remove(charPanel);
    charPanel = genCharacterPanel();
    add(charPanel);
    
    validate(); 
    repaint();
  }
  
  private void refreshStatPanel() {
    remove(statPanel);
    statPanel = genCharacterPanel();
    add(statPanel);
    
    validate();
    repaint();
  }
  
  private void removeSelectedBackgroundPlayer() {
    if(selectedPlayerJLabel != null)
      selectedPlayerJLabel.setBackground(Color.DARK_GRAY);
  }
  private void removeSelectedBackgroundChars() {
    if(selectedCharacterJLabel != null)
      selectedCharacterJLabel.setBackground(Color.DARK_GRAY);
  }
}
