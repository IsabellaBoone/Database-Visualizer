package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import database.AccessDatabase;

//AUTHOR Joel Gingrich
//TODO add comments
public class LocationLayout extends JPanel {

  private static final long serialVersionUID = 1L;
  private AccessDatabase rmi;
  private JPanel locationPanel, contentPanel;
  private JLabel selectedLocation, selectedContent;
  private JLabel[] locationLabels;
  private ArrayList<JLabel> locationContents = new ArrayList<JLabel>();
  private String[] locationAreaTypes;
  private int selectedLocationIndex, type;
  private int[] locationIDs;

  public LocationLayout(AccessDatabase rmi) {
    this.rmi = rmi;

    initializeJPanel();
  }

  /**
   * Initial construction of all JPanels
   */
  private void initializeJPanel() {
    setBackground(Color.DARK_GRAY);
    setLayout(new GridBagLayout());

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 0.33;
    c.weighty = 0.95;
    locationPanel = buildLocationPanel();
    contentPanel = buildObjListPanel();
    add(locationPanel, c);
    c.gridx++;
    add(contentPanel, c);

  }

  /**
   * builds the panel that shows the list of locations
   * 
   * @return completed panel
   */
  private JPanel buildLocationPanel() {
    JPanel panel = new JPanel();
    panel.setBackground(new Color(132, 132, 132));
    panel.setLayout(new BorderLayout());
    Dimension d = new Dimension(375, 600);
    panel.setMinimumSize(d);
    panel.setPreferredSize(d);
    panel.setMaximumSize(d);

    JScrollPane items = new JScrollPane();
    items.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    items.add(items.createVerticalScrollBar());

    JPanel locations = new JPanel();
    locations.setBackground(new Color(132, 132, 132));
    locations.setLayout(new GridBagLayout());

    locationLabels = new JLabel[rmi.getNumLocations()];
    locationAreaTypes = rmi.getAllAreaTypes();
    locationIDs = rmi.getAllLocationIds();

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 0.95;
    c.ipady = 20;

    panel.add(new JLabel("<html><H1 Style = \"color:white; font-size: 30px\">" + "Locations:" + "</H1></html>"),
        BorderLayout.NORTH);
    // adds creates JLabels with mouse listeners for all locations
    for (int i = 0; i < locationAreaTypes.length; i++) {
      String areaType = "<html><body style = \"color:white; font-size: 22px\">" + locationAreaTypes[i] + ": "
          + locationIDs[i] + "</body></html>";
      locationLabels[i] = new JLabel(areaType, SwingConstants.CENTER);

      d = new Dimension(350, 30);
      locationLabels[i].setMinimumSize(d);
      locationLabels[i].setPreferredSize(d);
      locationLabels[i].setMaximumSize(d);

      locationLabels[i].addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
          if (selectedLocation != null) {
            removeSelectedBackgroundLocation();
          }
          for (int j = 0; j < locationLabels.length; j++) {
            if (e.getSource() == locationLabels[j]) {
              selectedLocation = locationLabels[j];
              selectedLocationIndex = j;

              locationLabels[j].setOpaque(true);
              locationLabels[j].setBackground(new Color(234, 201, 55));
              break;
            }
          }

          refreshContentPanel();
        }
      });
      locations.add(locationLabels[i], c);
      c.gridy++;
    }
    items.setViewportView(locations);
    c.gridy = 1;
    panel.add(items, BorderLayout.CENTER);

    return panel;
  }

  /**
   * builds the panel that shows the list of items, creatures and characters
   * 
   * @return completed panel
   */
  private JPanel buildObjListPanel() {
    JPanel panel = new JPanel();
    Dimension d = new Dimension(415, 480);
    panel.setLayout(new BorderLayout());
    panel.setBackground(new Color(132, 132, 132));
    panel.setMinimumSize(d);
    panel.setPreferredSize(d);
    panel.setMaximumSize(d);

    JScrollPane items = new JScrollPane();
    items.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    items.add(items.createVerticalScrollBar());
    d = new Dimension(415, 400);
    items.setMinimumSize(d);
    items.setPreferredSize(d);
    items.setMaximumSize(d);

    JPanel contents = new JPanel();
    contents.setBackground(new Color(132, 132, 132));
    contents.setLayout(new GridBagLayout());

    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 0.95;
    c.ipady = 20;
    // for all contents apply action listeners
    for (int i = 0; i < locationContents.size(); i++) {

      locationContents.get(i).addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {

          if (selectedContent != null) {
            removeSelectedBackgroundContent();
          }
          for (int j = 0; j < locationContents.size(); j++) {
            if (e.getSource() == locationContents.get(j)) {
              selectedContent = locationContents.get(j);

              locationContents.get(j).setOpaque(true);
              locationContents.get(j).setBackground(new Color(234, 201, 55));
              break;
            }
          }

        }
      });
      contents.add(locationContents.get(i), c);
      c.gridy++;
    }

    items.setViewportView(contents);
    panel.add(items, BorderLayout.NORTH);

    JPanel b = buttons();
    panel.add(b, BorderLayout.CENTER);
    return panel;
  }

  /**
   * constructs the buttons
   * 
   * @return finished buttons
   */
  private JPanel buttons() {
    JPanel subPanel = new JPanel(), subSubPanel = new JPanel(), panel = new JPanel(new BorderLayout());
    JButton add = new JButton("Add Item"), edit = new JButton("Edit Item"), delete = new JButton("Delete Item");
    JButton addC = new JButton("Add Creature"), editC = new JButton("Edit Creature"),
        deleteC = new JButton("Delete Creature");

    subPanel.add(add);
    subPanel.add(edit);
    subPanel.add(delete);

    subSubPanel.add(addC);
    subSubPanel.add(editC);
    subSubPanel.add(deleteC);

    add.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        System.out.println("Add Item");
        new AddItem(rmi, locationIDs[selectedLocationIndex]).addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent arg0) {
            refreshContentPanel();
          }
        });
      }
    });

    delete.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        delete(1);

      }
    });

    edit.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        edit(1);
      }

    });

    addC.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        System.out.println("Add Item");
        new AddCreature(rmi, locationIDs[selectedLocationIndex]).addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosed(WindowEvent arg0) {
            refreshContentPanel();
          }
        });
      }
    });

    deleteC.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        delete(2);

      }
    });

    editC.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        edit(2);
      }

    });
    panel.add(subPanel, BorderLayout.NORTH);
    panel.add(subSubPanel, BorderLayout.CENTER);

    return panel;
  }

  /**
   * rebuilds the panels and updates the information in them
   */
  private void refreshContentPanel() {
    updateContents();
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = 1;
    c.gridy = 0;
    c.weightx = 0.33;
    c.weighty = 0.95;
    remove(contentPanel);
    JPanel temp = buildObjListPanel();
    contentPanel = temp;
    add(contentPanel, c);
    revalidate();
    repaint();
  }

  /*
   * updates the contents of a location
   */
  private void updateContents() {
    locationContents.clear();
    Dimension d = new Dimension(350, 30);
    ResultSet rs = null;
    try {
      // items
      rs = AccessDatabase.getConnection().createStatement()
          .executeQuery("SELECT ItemId FROM ITEM WHERE ITEM.LocationId = " + locationIDs[selectedLocationIndex]);
      while (rs.next()) {
        JLabel item = new JLabel(
            "<html><br style = \"font-size:2px;\"><p style = \"color:white; font-size:15px;\">ItemId = "
                + rs.getInt("ItemId") + "</p><br style = \"font-size:2px;\"></html>");

        item.setMinimumSize(d);
        item.setPreferredSize(d);
        item.setMaximumSize(d);
        locationContents.add(item);
      }

      // creatures
      rs = AccessDatabase.getConnection().createStatement().executeQuery(
          "SELECT IdNumber FROM Creature WHERE Creature.LocationId = " + locationIDs[selectedLocationIndex]);
      while (rs.next()) {
        JLabel item = new JLabel(
            "<html><br style = \"font-size:2px;\"><p style = \"color:white; font-size:15px;\">Creature Id = "
                + rs.getInt("IdNumber") + "</p><br style = \"font-size:2px;\"></html>");

        item.setMinimumSize(d);
        item.setPreferredSize(d);
        item.setMaximumSize(d);
        locationContents.add(item);
      }

      // character
      rs = AccessDatabase.getConnection().createStatement().executeQuery(
          "SELECT Name FROM Characters WHERE Characters.LocationId = " + locationIDs[selectedLocationIndex]);
      while (rs.next()) {
        JLabel item = new JLabel(
            "<html><br style = \"font-size:2px;\"><p style = \"color:white; font-size:15px;\">Character Name = "
                + rs.getString("Name") + "</p><br style = \"font-size:2px;\"></html>");

        item.setMinimumSize(d);
        item.setPreferredSize(d);
        item.setMaximumSize(d);
        locationContents.add(item);
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void removeSelectedBackgroundLocation() {
    if (selectedLocation != null)
      selectedLocation.setBackground(new Color(132, 132, 132));
  }

  private void removeSelectedBackgroundContent() {
    if (selectedContent != null)
      selectedContent.setBackground(new Color(132, 132, 132));
  }

  private void delete(int type) {
    if (selectedContent != null) {
      String content = selectedContent.getText();
      if (content != null) {

        // for item
        if (content.contains("Item") && type == 1) {
          content = content.substring(86);
          content = content.substring(0, content.indexOf("<"));
          int id = Integer.parseInt(content);

          try {
            AccessDatabase.getConnection().createStatement()
                .execute("DELETE FROM ITEM WHERE ItemId = " + id);
            selectedContent = null;
          } catch (SQLException e) {
            e.printStackTrace();
          }
          refreshContentPanel();

          // for creatures
        } else if (content.contains("Creature") && type == 2) {
          content = content.substring(91);
          content = content.substring(0, content.indexOf("<"));
          int id = Integer.parseInt(content);

          try {
            AccessDatabase.getConnection().createStatement()
                .execute("DELETE FROM Creature WHERE IdNumber = " + id);
            selectedContent = null;
          } catch (SQLException e) {
            e.printStackTrace();
          }
          refreshContentPanel();
        }
      }
    }
  }

  private void edit(int type) {
    if (selectedContent != null) {
      ResultSet rs = null;
      String content = selectedContent.getText();
      // if item
      if (content.contains("Item") && type == 1) {
        content = content.substring(86);
        content = content.substring(0, content.indexOf("<"));
        int id = Integer.parseInt(content);

        try {
          rs = AccessDatabase.getConnection().createStatement()
              .executeQuery("SELECT * FROM Item WHERE Item.ItemId = " + id);
          rs.next();
        } catch (SQLException e) {
          e.printStackTrace();
        }

        new EditItem(rs, rmi).addWindowListener(new WindowAdapter() {
          public void windowClosed(WindowEvent arg0) {
            refreshContentPanel();
          }
        });
        // if creature
      } else if (content.contains("Creature") && type == 2) {

        content = content.substring(91);
        content = content.substring(0, content.indexOf("<"));
        int id = Integer.parseInt(content);

        try {
          rs = AccessDatabase.getConnection().createStatement()
              .executeQuery("SELECT * FROM Creature WHERE Creature.IdNumber = " + id);
          rs.next();
        } catch (SQLException e) {
          e.printStackTrace();
        }

        new EditCreature(rs, rmi).addWindowListener(new WindowAdapter() {
          public void windowClosed(WindowEvent arg0) {
            refreshContentPanel();
          }
        });
      }
    }
  }
}
