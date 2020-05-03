
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import database.RetrieveManipulateInformation;

/**
 * Display all characters in the database and their
 * stats when clicked on.  Also, allows user to 
 * add, edit or remove characters and modify stats.  
 * @author Isabella Boone
 *
 */
public class CharactersLayout extends JPanel {
  private RetrieveManipulateInformation rmi;
  private JPanel characterPanel, statsPanel;
  private JLabel selectedNameJLabel = null;
  private int selectedIndex; // characternames[selectedIndex] = selectedName
  private String[] characterNames;
  private String selectedName = null;
  

  public CharactersLayout(RetrieveManipulateInformation rmi) {
    this.rmi = rmi;
    
    // Initialize JPanel settings
    setBackground(Color.DARK_GRAY);
    setLayout(new BorderLayout()); 
    
    characterPanel = genCharacterPanel();
    add(characterPanel, BorderLayout.WEST);
    statsPanel = genStatsPanel(); 
    add(statsPanel, BorderLayout.EAST);
    
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

    // Panel of names
    JPanel characters = new JPanel();
    characters.setBackground(Color.LIGHT_GRAY);
    characters.setLayout(new GridLayout(rmi.getNumCharacters(), 1));

    // Initialize character names
    characterNames = rmi.getAllCharacterNames();

    // Add header
    panel.add(new JLabel("<html><H1 Style = \"color:white; font-size: 25px\">" + "Characters:" + "</H1></html>"),
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
          selectedIndex = x; 
          refreshStatsPanel(); 
        }
      });
      characters.add(label); 
    }
    
    names.setViewportView(characters);
    panel.add(names, BorderLayout.CENTER);
    panel.add(addCharacterButtons(), BorderLayout.SOUTH);
    return panel;
  }

  /**
   * Add character, remove character, modify character
   * @return
   */
  private JPanel addCharacterButtons() {
    // JPanel settings
    JPanel panel = new JPanel(); 
    panel.setLayout(new GridLayout(0, 2));
    
    // Dimensions
    Dimension d = new Dimension(450, 75);
    panel.setMinimumSize(d);
    panel.setPreferredSize(d);
    panel.setMaximumSize(d);
    
    // Buttons
    JButton addChar = new JButton("Add Character"), 
        editChar = new JButton("Edit Character"),
        deleteChar = new JButton("Delete Character"),
        addUser = new JButton("Add User"); 
    
    addChar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println("Add character"); 
        new AddCharacter(rmi); 
        refreshCharacterPanel();
      }
    });
    
    editChar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println("Edit Character"); 
        if(selectedName == null) {
          failureToSelect(); 
        } else {
          new EditCharacter(rmi, selectedName); 
          refreshStatsPanel(); 
        }
      }
    });
    
    deleteChar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.out.println("Delete Character");
        if (selectedName == null) {
          failureToSelect(); 
        } else {
          new DeleteCharacter(rmi, selectedName); 
          refreshCharacterPanel(); 
        }
      }
    });

    addUser.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        new AddUser(rmi); 
      }
    });
    
    
    panel.add(addChar);
    panel.add(editChar);
    panel.add(deleteChar);
    panel.add(addUser);
    
    return panel;
  }
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
      charStats = rmi.getCharacterStats(characterNames[0]);
    } else {
      charStats = rmi.getCharacterStats(selectedName);
    }
    

    panel.add(new JLabel("<html><H1 Style = \"color:white; font-size: 25px\">" + "Stats:" + "</H1></html>"),
        BorderLayout.NORTH);
    
    // Format strings
    String HP = charStats[1] + "/" + charStats[0] + "HP";
    String strStam = charStats[2] + " Strength " + charStats[3] + " Stamina"; 
    String loc = rmi.getLocType(charStats[4]) + ": " + charStats[4];
    String user = charStats[5]; 

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
  
  private void refreshCharacterPanel() {
    remove(characterPanel);
    characterPanel = genCharacterPanel(); 
    SwingUtilities.updateComponentTreeUI(characterPanel);
    add(characterPanel, BorderLayout.WEST);
    validate();
    repaint();
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
