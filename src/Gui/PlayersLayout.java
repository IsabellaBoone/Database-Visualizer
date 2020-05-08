package Gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import database.RetrieveManipulateInformation;

//Author: Joshua J
public class PlayersLayout extends JPanel{
	  private RetrieveManipulateInformation rmi;
	  private JPanel characterPanel, statsPanel, itemsPanel;
	  private JLabel selectedNameJLabel = null;
	  private String[] characterNames, itemsWithChar;
	  private int[] itemIDs;
	  private String selectedName = null;
	  
	  
	  public PlayersLayout(RetrieveManipulateInformation rmi) {
	    this.rmi = rmi;
	    
	    // Initialize character names in character panel
	    characterNames = rmi.getAllCharacterNames();
	    if(selectedName == null) {
	    	rmi.getAllCharItems(characterNames[0]);
	    } else {
	    	rmi.getAllCharItems(selectedName);
	    }
	        
	        // Initialize JPanel settings
	    setBackground(Color.DARK_GRAY);
	    setLayout(new BorderLayout());
	    itemsPanel = genItemsPanel();

	    //char -> border layout west
        characterPanel = genCharacterPanel();
	    add(characterPanel, BorderLayout.WEST);
	    //items -> border layout center
	    itemsPanel = genItemsPanel();
	    add(itemsPanel, BorderLayout.CENTER);
	    //stats -> east border
	    statsPanel = genStatsPanel(); 
	    add(statsPanel, BorderLayout.EAST);
	  }
	  
	  /**
	   * Generates a character panel
	   * @return
	   */
	  private JPanel genCharacterPanel() {
		    // Entire panel, that will have header and panel of names
		    JPanel panel = new JPanel();
		    panel.setBackground(Color.DARK_GRAY);
		    panel.setLayout(new BorderLayout());
		    
		    // Size for panel
		    Dimension d = new Dimension(350, 650);
		    panel.setMinimumSize(d);
		    panel.setPreferredSize(d);
		    panel.setMaximumSize(d);

		    // Scrollbar 
		    JScrollPane names = new JScrollPane();
		    names.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		    names.add(names.createVerticalScrollBar());
		    names.getVerticalScrollBar().setUnitIncrement(20);
		    
		    // Panel of names
		    JPanel characters = new JPanel();
		    characters.setBackground(Color.LIGHT_GRAY);
		    characters.setLayout(new GridLayout(rmi.getNumCharacters(), 1));


		    // Add header
		    panel.add(new JLabel("<html><H1 Style = \"color:white; font-size: 20px\">" + "Characters:" + "</H1></html>", SwingUtilities.CENTER),
		        BorderLayout.NORTH);

		    // Add all labels of character names
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
		          refreshItemsPanel();
		          refreshStatsPanel(); 
		        }

		      });
		      characters.add(label); 
		    }
		    
		    names.setViewportView(characters);
		    panel.add(names, BorderLayout.CENTER);
		    return panel;
		  }

	  
	  /** because there is one already made
	   * Generates a Items panel
	   * @return
	   */
	  private JPanel genItemsPanel() {
		    // Entire panel, that will have header and panel of names
		    JPanel panel = new JPanel();
		    panel.setBackground(Color.DARK_GRAY);
		    panel.setLayout(new BorderLayout());
		    
		    // Size for panel
		    Dimension d = new Dimension(300, 650);
		    panel.setMinimumSize(d);
		    panel.setPreferredSize(d);
		    panel.setMaximumSize(d);

		    // Scrollbar 
		    JScrollPane names = new JScrollPane();
		    names.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		    names.add(names.createVerticalScrollBar());
		    names.getVerticalScrollBar().setUnitIncrement(20);
		    
		    // Panel of items
		    JPanel items = new JPanel(); 
		    items.setBackground(Color.LIGHT_GRAY);
		    items.setLayout(new GridLayout(10, 1));
		    int numItems; 
		    
		    panel.add(new JLabel("<html><H1 Style = \"color:white; font-size: 20px\">" + "Items:" + "</H1></html>", SwingUtilities.CENTER),
	                BorderLayout.NORTH);
		    
		    // check if selectedName == null, if so, check if characterNames[0] has items or not
		    if(selectedName == null) {
		    	numItems = rmi.getNumItemsFromChar(characterNames[0]);
		    	if(numItems == 0) {
		        // If selectedName is null and characterNames[0] 
		        items.add(new JLabel("No items found", SwingUtilities.CENTER), BorderLayout.CENTER);
		        panel.add(items, BorderLayout.CENTER);
		        return panel; 
		        }
		      
		    } else {
		      // check if selectedName has item or not
		    	numItems = rmi.getNumItemsFromChar(selectedName);
		    	if(numItems == 0) {		    	
				    items.add(new JLabel("No items found", SwingUtilities.CENTER), BorderLayout.CENTER);
				    panel.add(items, BorderLayout.CENTER);
				    return panel; 
		    	}
		    }
		    
		    // Initialize item names in item panel
		    itemsWithChar = rmi.getAllCharItems(selectedName);

		    // Add all labels of items held by character
		    for (int i = 0; i < itemsWithChar.length; i++) {
		      final int x = i;
		      String format = "<html><body style = \"color:black; font-size: 20px\">" + itemsWithChar[i] + "</body></html>";
		      JLabel label = new JLabel(format, SwingConstants.CENTER);
		      label.setOpaque(true);
		      label.setBackground(Color.LIGHT_GRAY);
		      label.addMouseListener(new MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		          removeSelectedBackgroundChars(); 
		          label.setBackground(new Color(234,201,55));
		          selectedNameJLabel = label; 
		          selectedName = characterNames[x];
		          refresh();
		        }
		      });
		      items.add(label); 
		    } 
		    
		    names.setViewportView(items);
		    panel.add(names, BorderLayout.CENTER);
		    return panel;
		  }	  
	  
	  private void noItems() {
	    
	  }
	  
	  /**
	   * Generage a stat panel
	   * @return
	   */
	  private JPanel genStatsPanel() {
	    // Entire panel, that will have header and panel of names
	    JPanel panel = new JPanel();
	    panel.setBackground(Color.DARK_GRAY);
	    panel.setLayout(new BorderLayout());
	    
	    // Size for panel
	    Dimension d = new Dimension(350, 500);
	    panel.setMinimumSize(d);
	    panel.setPreferredSize(d);
	    panel.setMaximumSize(d);

	    // Panel of stats
	    JPanel stats = new JPanel();
	    stats.setBackground(Color.LIGHT_GRAY);
	    stats.setLayout(new GridLayout(5, 1));

	    // Fetch stats
	    String[] charStats;  
	    if(selectedName == null) {
	      charStats = rmi.getCharacterStats(characterNames[0]);
	      panel.add(new JLabel("<html><H1 Style = \"color:white; font-size: 20px\">" + characterNames[0] + "'s Stats:" + "</H1></html>", SwingUtilities.CENTER),
	        BorderLayout.NORTH);
	    } else {
	      charStats = rmi.getCharacterStats(selectedName);
	      panel.add(new JLabel("<html><H1 Style = \"color:white; font-size: 20px\">" + selectedName + "'s Stats:" + "</H1></html>", SwingUtilities.CENTER),
	          BorderLayout.NORTH);
	    }
	    
	    // Format strings
	    String HP = charStats[1] + "/" + charStats[0] + "HP";
	    String strStam = charStats[2] + " Strength " + charStats[3] + " Stamina"; 
	    String loc = "Located in: " + rmi.getLocType(charStats[4]) + ": " + charStats[4];
	    String user = "Belongs to: " + charStats[5]; 

	    // Add JLabel
	    JLabel hpLabel = new JLabel(HP, SwingConstants.CENTER);
	    JLabel strStamLabel = new JLabel(strStam, SwingConstants.CENTER);
	    JLabel locLabel = new JLabel(loc, SwingConstants.CENTER);
	    JLabel userLabel = new JLabel(user, SwingConstants.CENTER);
	    
	    // Add all stats to panel
	    stats.add(hpLabel);
	    stats.add(strStamLabel);
	    stats.add(locLabel);
	    stats.add(userLabel);
	    
	    panel.add(stats, BorderLayout.CENTER);
	    
	    return panel;
	  }

	  
//	  private void updateContents() {
//
//		    Dimension d = new Dimension(350, 30);
//		    ResultSet rs = null;
//		    try {
//		      // items
//		      rs = RetrieveManipulateInformation.getConnection().createStatement()
//		          .executeQuery("SELECT ItemId FROM ITEM WHERE ITEM.LocationId = " + locationIDs[selectedLocationIndex]);
//		      while (rs.next()) {
//		        JLabel item = new JLabel(
//		            "<html><br style = \"font-size:2px;\"><p style = \"color:white; font-size:15px;\">ItemId = "
//		                + rs.getInt("ItemId") + "</p><br style = \"font-size:2px;\"></html>");
//		        
//		        item.setMinimumSize(d);
//		        item.setPreferredSize(d);
//		        item.setMaximumSize(d);
//		      }
//		    }
//	  }
	  
	  private void refresh() {
	    remove(characterPanel);
	    characterPanel = genCharacterPanel(); 
	    SwingUtilities.updateComponentTreeUI(characterPanel);
	    add(characterPanel, BorderLayout.WEST);
	    
	    remove(itemsPanel);
	    itemsPanel = genItemsPanel(); 
	    SwingUtilities.updateComponentTreeUI(itemsPanel);
	    add(itemsPanel, BorderLayout.CENTER);
	    
	    remove(statsPanel);
	    statsPanel = genStatsPanel();
	    add(statsPanel, BorderLayout.EAST);
	    SwingUtilities.updateComponentTreeUI(statsPanel);

	    validate();
	    repaint();
	  }
	  
	  private void refreshItemsPanel() {
		  remove(itemsPanel);
		  itemsPanel = genItemsPanel(); 
		  SwingUtilities.updateComponentTreeUI(itemsPanel);
		  add(itemsPanel, BorderLayout.CENTER);
	  }
	  
	  private void refreshStatsPanel() {
	    remove(statsPanel);
	    statsPanel = genStatsPanel();
	    add(statsPanel, BorderLayout.EAST);
	    SwingUtilities.updateComponentTreeUI(statsPanel);

	    validate();
	    repaint();
	  }
	  
	  private void removeSelectedBackgroundChars() {
	    if (selectedNameJLabel != null)
	      selectedNameJLabel.setBackground(Color.LIGHT_GRAY);
	  } 
	  
	  // Buttons
	  
	  private void failureToSelect() {
	    JFrame frame = new JFrame("Error");
	    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    frame.setSize(200,200);
	    frame.setLocationRelativeTo(null);
	    JLabel bad = new JLabel("You need to select a character to edit first.");
	    JButton cont = new JButton("Ok");
	    cont.addActionListener(e -> frame.dispose());
	    
	    frame.setLayout(new FlowLayout());
	    frame.add(bad);
	    frame.add(cont);
	    frame.pack();
	    frame.setVisible(true);
	  }
}	  
