package Gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import database.RetrieveManipulateInformation;
import javax.swing.JPanel;

//AUTHOR Joel Gingrich
//TODO add edit and add options.
public class LocationLayout extends JPanel {

  private RetrieveManipulateInformation rmi;
  private JPanel locationPanel, contentPanel;
  private JLabel selectedLocation, selectedContent;
  private JLabel[] locationLabels;
  private ArrayList<JLabel> locationContents = new ArrayList<JLabel>();
  private String[] locationAreaTypes;
  private int selectedLocationIndex, selectedContentIndex;
  private int[] locationIDs;

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
    locationPanel = buildLocationPanel();
    contentPanel = buildObjListPanel();
    add(locationPanel, c);
    c.gridx++;
    add(contentPanel, c);

  }

  private JPanel buildLocationPanel() {
    JPanel panel = new JPanel();
    panel.setBackground(new Color(132, 132, 132));
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

    for (int i = 0; i < locationAreaTypes.length; i++) {
      String areaType = "<html><body style = \"color:white; font-size: 22px\">" + locationAreaTypes[i]
          + "</body></html>";
      locationLabels[i] = new JLabel(areaType, SwingConstants.CENTER);

      d = new Dimension(200, 30);
      locationLabels[i].setMinimumSize(d);
      locationLabels[i].setPreferredSize(d);
      locationLabels[i].setMaximumSize(d);

      // locationLabels[i].setMinimumSize(new Dimension(400,100));

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

  private JPanel buildObjListPanel() {
    JPanel panel = new JPanel();
    Dimension d = new Dimension(400, 300);
    panel.setLayout(new BorderLayout());
    panel.setBackground(new Color(132, 132, 132));
    panel.setMinimumSize(d);
    panel.setPreferredSize(d);
    panel.setMaximumSize(d);

    JScrollPane items = new JScrollPane();
    items.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    items.add(items.createVerticalScrollBar());
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
    
    
    for (int i = 0; i < locationContents.size(); i++) {

      // locationLabels[i].setMinimumSize(new Dimension(400,100));

      locationContents.get(i).addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
          
          if (selectedContent != null) {
            removeSelectedBackgroundContent();
          }
          for (int j = 0; j < locationContents.size(); j++) {
            if (e.getSource() == locationContents.get(j)) {
              selectedContent = locationContents.get(j);
              selectedContentIndex = j;

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
    return panel;
  }
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
    add(contentPanel,c);
    revalidate();
    repaint();
  }
  private void updateContents() {
    locationContents.clear();
    ResultSet rs = null;
    try {
      rs = RetrieveManipulateInformation.getConncetion().createStatement()
          .executeQuery("SELECT ItemId FROM ITEM WHERE ITEM.LocationId = " + locationIDs[selectedLocationIndex]);
      while (rs.next()) {
        JLabel item = new JLabel(
            "<html><br style = \"font-size:2px;\"><p style = \"color:white; font-size:15px;\">ItemId = "
                + rs.getInt("ItemId") + "</p><br style = \"font-size:2px;\"></html>");
        Dimension d = new Dimension(200, 30);
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
}
