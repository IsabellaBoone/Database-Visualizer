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

public class CharactersLayout extends JPanel {
  private RetrieveManipulateInformation rmi;
  private JLabel playersJLabel[], charactersJLabel[],
  
  playerSelected, // Needed for highlighting selected player
  characterSelected; // Needed for highlighting selected character
  
  private JPanel curCharactersPanel, curStatsPanel; 
  private GridBagConstraints charPanel, statPanel; 
  private String selectedPlayer = null, selectedCharacter = null; // maybe not needed
  
  private String[] playerUsernames, charNames;
//  private int playerSelcted, charSelected; 
  
  public CharactersLayout(RetrieveManipulateInformation rmi) {
    this.rmi = rmi;
    
    initializeJPanel(); 
  }

  private void initializeJPanel() {
    setBackground(Color.DARK_GRAY );
    setLayout(new GridBagLayout());
    
    GridBagConstraints c = new GridBagConstraints(); 
    c.gridx = 0; 
    c.gridy = 0;
    c.weightx = 0.33;
    c.weighty = 0.95; 
    
    add(genUsernamePanel(), c);
    c.gridx++;
    
    curCharactersPanel = genCharacterPanel(); 
    add(curCharactersPanel, c);
    charPanel = c; 
    c.gridx++;
    
    curStatsPanel = genCharStatsPanel(); 
    add(curStatsPanel, c);
    statPanel = c; 
    c.gridx++;
    
  }
  
  private JPanel genUsernamePanel() {
    JPanel panel = new JPanel(); 
    
    panel.setBackground(Color.DARK_GRAY);
    panel.setLayout(new GridBagLayout()); 
    
    playersJLabel = new JLabel[rmi.getNumPlayers()];
    playerUsernames = rmi.getAllPlayerUsernames();
    
    GridBagConstraints c = new GridBagConstraints(); 
    c.gridx = 0;
    c.gridy = 0; 
    c.weightx = 0.95;
    
    panel.add(new JLabel("<html><H1 Style = \"color:#808080; font-size: 30px\">" + "Players:" + "</H1></html>"));
    
    c.gridy++; 
    
    // Add all usernames
    for(int i = 0; i < playerUsernames.length; i++) {
      // HTML Formatting for JLabel, then add mouse listener.
      String format = "<html><body style = \"color:white; font-size: 22px\">" + playerUsernames[i] + "</body></html>"; // text with html formatting
      playersJLabel[i] = new JLabel(format, SwingConstants.CENTER);
      playersJLabel[i].addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          // Remove background from selected player
          removeSelectedBackgroundPlayer(); 
          
          // Find what label the user selected
          for(int j = 0; j < playersJLabel.length; j++) {
            if(e.getSource() == playersJLabel[j]) {
              /*
               * When we find what player was selected, 
               * change playerSelected variable to keep 
               * track for future references, and change
               * the background color to yellow.
               * 
               * Then, break out because there is no point to
               * the loop anymore. 
               */
              playerSelected = playersJLabel[j]; 
              selectedPlayer = playerUsernames[j]; // Store the string for later
              playersJLabel[j].setOpaque(true);
              playersJLabel[j].setBackground(new Color(234, 201, 55));
              break;
            }
          }
          refreshCharacterPanel(); 
        }
      });
    
      panel.add(playersJLabel[i], c);
      c.gridy++; 
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
    
    if(selectedPlayer == null) {
      charNames = rmi.getAllCharsFromUser(playerUsernames[0]);
      charactersJLabel = new JLabel[rmi.getNumChars(playerUsernames[0])];
    } else {
      charNames = rmi.getAllCharsFromUser(selectedPlayer);
      charactersJLabel = new JLabel[rmi.getNumChars(selectedPlayer)];
    }
    
    GridBagConstraints c = new GridBagConstraints(); 
    c.gridx = 1;
    c.gridy = 0; 
    c.weightx = 0.95;
    
    c.gridy++; 
    
    if(selectedPlayer == null) {
      panel.add(new JLabel("<html><H1 Style = \"color:#808080; font-size: 30px\">" + playerUsernames[0] + "'s Characters:" + "</H1></html>"));
      System.out.print(playerUsernames[0] + ": ");
    } else {
      panel.add(new JLabel("<html><H1 Style = \"color:#808080; font-size: 30px\">" + selectedPlayer + "'s Characters:" + "</H1></html>"));
      System.out.print(selectedPlayer + ": ");
    }
    
    for(int i = 0; i < charNames.length; i++) {
   // HTML Formatting for JLabel, then add mouse listener.
      System.out.print(charNames[i] + "\t");
      String format = "<html><body style = \"color:white; font-size: 22px\">" + charNames[i] + "</body></html>"; // text with html formatting
      charactersJLabel[i] = new JLabel(format, SwingConstants.CENTER);
      charactersJLabel[i].addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          // Remove background from selected player
          removeSelectedBackgroundChars(); 
          
          // Find what label the user selected
          for(int j = 0; j < charactersJLabel.length; j++) {
            if(e.getSource() == charactersJLabel[j]) {
              /*
               */
              characterSelected = charactersJLabel[j]; 
              selectedCharacter = charNames[j]; // Store the string for later
              charactersJLabel[j].setOpaque(true);
              charactersJLabel[j].setBackground(new Color(234, 201, 55));
              break;
            }
          }
//          refreshStatPanel(); 
        }
      });
    
      panel.add(charactersJLabel[i], c);
      c.gridy++; 
    }
    
    return panel;
  }
  
  private JPanel genCharStatsPanel() {
    JPanel panel = new JPanel(); 
    panel.add(new JLabel("Character Stats Placeholder")); 
    return panel; 
  }
  
  private void refreshCharacterPanel() { 
    // delete curCharPanel, add new genCharPanel()
    remove(curCharactersPanel);
    JPanel p = genCharacterPanel();
    curCharactersPanel = p;
    add(p, charPanel);
    validate(); 
    repaint();
  }
  
  private void refreshStatPanel() {
    remove(curStatsPanel);
    JPanel p = genCharacterPanel();
    curStatsPanel = p;
    add(p, statPanel);
    validate();
    repaint();
  }
  
  private void removeSelectedBackgroundPlayer() {
    if(playerSelected != null)
      playerSelected.setBackground(Color.DARK_GRAY);
  }
  private void removeSelectedBackgroundChars() {
    if(characterSelected != null)
      characterSelected.setBackground(Color.DARK_GRAY);
  }
}
