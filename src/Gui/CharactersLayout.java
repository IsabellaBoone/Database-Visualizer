package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import database.AccessDatabase;

/**
 * Display all characters in the database and their
 * stats when clicked on.  Also, allows user to 
 * add, edit or remove characters and modify stats.  
 * @author Isabella Boone
 *
 */
@SuppressWarnings("serial")
public class CharactersLayout extends JPanel {
  private AccessDatabase access; // our database access
  private JPanel characterPanel, // Panel that displays our character names
    statsPanel; // Panel that displays selected characters stats
  private String[] characterNames;          // all character names in the database. 
  private JLabel selectedNameJLabel = null; // currently selected character
  private String selectedName = null;       // currently selected character name
  
  /**
   * Initialize our JPanel, set background, layout, and
   * add our character panel and stats panel.
   * @param access
   */
  public CharactersLayout(AccessDatabase access) {
    this.access = access;
    
    // Initialize JPanel settings
    setBackground(Color.DARK_GRAY);
    setLayout(new GridLayout(1, 2)); 
    
    characterPanel = genCharacterPanel();
    add(characterPanel);
    statsPanel = genStatsPanel(); 
    add(statsPanel);
    
  }

  private JPanel genCharacterPanel() {
    // Entire panel, that will have header and panel of names
    JPanel panel = new JPanel();
    panel.setBackground(Color.DARK_GRAY);
    panel.setLayout(new BorderLayout());
    
    // Size for panel
    Dimension d = new Dimension(450, 650);
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
    characters.setLayout(new GridLayout(access.getNumCharacters(), 1));

    // Initialize character names
    characterNames = access.getAllCharacterNames();

    // Add header
    panel.add(new JLabel("<html><H1 Style = \"color:white; font-size: 25px\">" 
        + "Characters:" + "</H1></html>", SwingUtilities.CENTER), BorderLayout.NORTH);

    // Add all labels of character names
    for (int i = 0; i < characterNames.length; i++) {
      final int x = i;
      String format = "<html><body style = \"color:black; font-size: 20px\">" 
          + characterNames[i] + "</body></html>";
      JLabel label = new JLabel(format, SwingConstants.CENTER);
      label.setOpaque(true); 
      label.setBackground(Color.LIGHT_GRAY);
      label.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
          // Mouseclick = selecting new character
          removeSelectedBackgroundChars(); // remove old selected background
          label.setBackground(new Color(234,201,55)); // set to yellow to indicate selection
          selectedNameJLabel = label; // Store JLabel for changing background later
          selectedName = characterNames[x]; // Store new selectedName
          refreshStatsPanel(); // Because we have a new selectedName
        }
      });
      characters.add(label); 
    }
    
    // Set scrollbar onto names JPanel, and add names to enitre panel
    names.setViewportView(characters);
    panel.add(names, BorderLayout.CENTER);
    panel.add(addCharacterButtons(), BorderLayout.SOUTH);
    
    // Return entire panel
    return panel;
  }

  /**
   * Add character, remove character, modify character
   * @return
   */
  private JPanel addCharacterButtons() {
    // JPanel settings
    JPanel panel = new JPanel(); 
    panel.setLayout(new GridLayout(0, 3));
    
    // Dimensions
    Dimension d = new Dimension(450, 75);
    panel.setMinimumSize(d);
    panel.setPreferredSize(d);
    panel.setMaximumSize(d);
    
    // Buttons
    JButton addChar = new JButton("Add Character"), 
        editChar = new JButton("Edit Character"),
        deleteChar = new JButton("Delete Character"),
        addUser = new JButton("Add User"),
        refresh = new JButton("Refresh"); 
    
    // Add character button
    addChar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println("Add character"); 
        new AddCharacter(access).addWindowListener(new WindowAdapter() {
          public void windowClosed(WindowEvent arg0) {
            refresh();
          }
        });; 
      }
    });
    
    // Edit character button
    editChar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(selectedName == null) {
          // Cannot edit a character that we aren't selecting
          failureToSelect(); 
        } else {
          new EditCharacter(access, selectedName).addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent arg0) {
              selectedName = null; 
              // Otherwise, try to generate a stat panel for a character that doesn't exist
            }
          });
        }
      }
    });
    
    // Delete character button 
    deleteChar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println("Delete Character");
        if (selectedName == null) {
          failureToSelect(); 
        } else {
          new DeleteCharacter(access, selectedName);
          selectedName = null; // name is no longer in database
        }
      }
    });

    // Add user button
    addUser.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        new AddUser(access);
      }
    });
    
    // Manual refresh button
    refresh.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        refresh();
      }
    });
    
    // Add all buttons to panel
    panel.add(addChar);
    panel.add(editChar);
    panel.add(deleteChar);
    panel.add(addUser);
    panel.add(refresh);
    
    // Return entire panel
    return panel;
  }
  
  /**
   * Generate stats panel.  Works by grabbing the stats on the
   * currently selectedName, filtering them and adding to a panel.
   * @return JPanel of stats for selectedName
   */
  private JPanel genStatsPanel() {
    // Entire panel, that will have header and panel of names
    JPanel panel = new JPanel();
    panel.setBackground(Color.DARK_GRAY);
    panel.setLayout(new BorderLayout());
    
    // Size for panel
    Dimension d = new Dimension(450, 700);
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
      charStats = access.getCharacterStats(characterNames[0]);
      panel.add(new JLabel("<html><H1 Style = \"color:white; font-size: 25px\">" + characterNames[0] + "'s Stats:" + "</H1></html>", SwingUtilities.CENTER),
          BorderLayout.NORTH);
    } else {
      System.out.println(selectedName); 
      charStats = access.getCharacterStats(selectedName);
      panel.add(new JLabel("<html><H1 Style = \"color:white; font-size: 25px\">" + selectedName + "'s Stats:" + "</H1></html>", SwingUtilities.CENTER),
          BorderLayout.NORTH);
    }
    
    // Format strings
    String HP = charStats[1] + "/" + charStats[0] + "HP";
    String strStam = charStats[2] + " Strength " + charStats[3] + " Stamina"; 
    String loc = "Located in: " + access.getLocType(charStats[4]) + ": " + charStats[4];
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
    
    // Add to the center
    panel.add(stats, BorderLayout.CENTER);
    
    // Return entire panel
    return panel;
  }
  
  /**
   * Refresh all panels by removing them, generating new ones,
   * adding them, validate and repainting.  
   */
  private void refresh() {
    remove(characterPanel);
    characterPanel = genCharacterPanel(); 
    SwingUtilities.updateComponentTreeUI(characterPanel);
    add(characterPanel);
    
    remove(statsPanel);
    statsPanel = genStatsPanel();
    add(statsPanel);
    SwingUtilities.updateComponentTreeUI(statsPanel);

    validate();
    repaint();
  }
  
  /**
   * Refresh the stats panel by removing it, generating a new one,
   * adding it, updating, validating and repainting.
   */
  private void refreshStatsPanel() {
    remove(statsPanel);
    statsPanel = genStatsPanel();
    add(statsPanel, BorderLayout.EAST);
    SwingUtilities.updateComponentTreeUI(statsPanel);

    validate();
    repaint();
  }
  
  /**
   * Remove the background on the currently selected JLabel.
   */
  private void removeSelectedBackgroundChars() {
    if (selectedNameJLabel != null)
      selectedNameJLabel.setBackground(Color.LIGHT_GRAY);
  } 

  /**
   * Failure to select
   */
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
