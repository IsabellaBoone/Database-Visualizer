package Gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import database.RetrieveManipulateInformation;

import database.RetrieveManipulateInformation;

//Author: Joshua J
public class PlayersLayout extends JPanel{
	 private RetrieveManipulateInformation rmi;// used for replacing manipulating information ya know
	  private JLabel playerJLabel[], charactersJLabel[], selectedPlayerJLabel, selectedCharacterJLabel; // Needed for highlighting selected character
	  private JPanel curCharactersPanel = null, curStatsPanel = null; // used for replacing panels, kinda lowkey important 
	  private GridBagConstraints charPanel, statPanel; // used for replacing panels, kinda lowkey important 
	  private String selectedPlayerUsername = null, selectedCharacterName = null; // maybe not needed
	  private String[] playerUsernames, charNames;
	  
	  public PlayersLayout(RetrieveManipulateInformation rmi) {
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
	    
//	    curCharactersPanel = genCharacterPanel(); 
//	    add(curCharactersPanel, c);
	    charPanel = c; 
	    c.gridx++;
	    
//	    curStatsPanel = genCharStatsPanel(); 
//	    add(curStatsPanel, c);
	    statPanel = c; 
	    c.gridx++;
	    setVisible(true);
	  }
	  
	  private JPanel genUsernamePanel() {
	    JPanel panel = new JPanel();
	    panel.setBackground(new Color(80, 80, 80));
	    panel.setLayout(new BorderLayout());
	    Dimension d = new Dimension(300, 600);
	    panel.setMinimumSize(d);
	    panel.setPreferredSize(d);
	    panel.setMaximumSize(d);
	    
	    JScrollPane items = new JScrollPane();
	    items.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    items.add(items.createVerticalScrollBar());

	    JPanel locations = new JPanel();
	    locations.setBackground(new Color(132, 132, 132));
	    locations.setLayout(new GridBagLayout());

	    playerJLabel = new JLabel[rmi.getNumLocations()];
	    
	    
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
	      panel.add(new JLabel("<html><H1 Style = \"color:#808080; font-size: 30px\">" + playerUsernames[0] + "'s Characters:" + "</H1></html>"));
	      System.out.print(playerUsernames[0] + ": ");
	    } else {
	      panel.add(new JLabel("<html><H1 Style = \"color:#808080; font-size: 30px\">" + selectedPlayerUsername + "'s Characters:" + "</H1></html>"));
	      System.out.print(selectedPlayerUsername + ": ");
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
	              selectedCharacterJLabel = charactersJLabel[j]; 
	              selectedCharacterName = charNames[j]; // Store the string for later
	              charactersJLabel[j].setOpaque(true);
	              charactersJLabel[j].setBackground(new Color(234, 201, 55));
	              break;
	            }
	          }
//	          refreshStatPanel(); 
	        }
	      });
	    
	      panel.add(charactersJLabel[i], c);
	      c.gridy++; 
	    }
	    
	    return panel;
	  }
	  
	  private JPanel genCharStatsPanel() {
	    JPanel panel = new JPanel(); 
	    String[] charStats = rmi.getCharacterStats(selectedCharacterName);
	    
	    
	    
	    
	    
//	    panel.add(new JLabel("Character Stats Placeholder")); 
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
	    if(selectedPlayerJLabel != null)
	      selectedPlayerJLabel.setBackground(Color.DARK_GRAY);
	  }
	  private void removeSelectedBackgroundChars() {
	    if(selectedCharacterJLabel != null)
	      selectedCharacterJLabel.setBackground(Color.DARK_GRAY);
	  }
}

