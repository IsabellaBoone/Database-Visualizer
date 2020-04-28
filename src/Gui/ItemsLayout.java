package Gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

import database.RetrieveManipulateInformation;

// TODO: Add buttons for add item, edit item, and delete item.
public class ItemsLayout extends JPanel{
  
  RetrieveManipulateInformation rmi;
  JScrollPane item = new JScrollPane();
  JLabel selected = null;
  int selectedId;
  RetrieveManipulateInformation ri = new RetrieveManipulateInformation(RetrieveManipulateInformation.getConncetion());
  int[] itemsList;
  
  public ItemsLayout(RetrieveManipulateInformation rmi){
    this.rmi = rmi;
    
    setLayout(new BorderLayout());
    setBackground(Color.GRAY);
    add(Label(),BorderLayout.NORTH);

    item.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    item.add(item.createVerticalScrollBar());
    add(item, BorderLayout.CENTER);
    
    add(buttons(), BorderLayout.SOUTH);
    
    
    item.setViewportView(buildItems(ri.getNumItems()));
    revalidate();
    repaint();
    
  }
  
  private JPanel buttons() {
    JPanel subPanel = new JPanel(); 
    JButton add = new JButton("Add Item"), edit = new JButton("Edit Item"), delete = new JButton("Delete Item");
    subPanel.add(add); 
    subPanel.add(edit); 
    subPanel.add(delete); 
    add.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        System.out.println("Add Item");
        new AddItem(rmi); 
      }
    });
    delete.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        deleteItem();
      }
    });
    edit.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent arg0) {
        editItem();
      }
      
    });
    return subPanel; 
  }
  
  private JLabel Label(){
    JLabel label = new JLabel("<html><p style = \"color:black; font-size:15px\">Items</p></html>", SwingConstants.CENTER);
    label.setBackground(Color.WHITE);
    label.setOpaque(true);
    label.setPreferredSize(new Dimension(100,20));
    return label;
  }
  
  private JPanel buildItems(int rows){
    itemsList = ri.getAllItems();
    JPanel data = new JPanel();
    data.setLayout(new GridLayout(rows, 1));
    for(int i = 0; i < rows; i++) {
      final int x = i;
      JLabel label = new JLabel(buildHtml(itemsList[i]));
      label.setOpaque(true);
      label.setBackground(new Color(30, 30, 30));
      label.addMouseListener( new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
              removeSelectedBackground();
              label.setBackground(new Color(234, 201, 55));
              selected = label;
              selectedId = itemsList[x];
          }
      });
      data.add(label);
    }
    return data;
  }

  private String buildHtml(int primaryKey){
    String labelText = "";
    ResultSet rs = null;
    try {
      rs = ri.getConncetion().createStatement().executeQuery("SELECT * FROM ITEM WHERE ITEM.ITEMID = " + primaryKey);
      rs.next();
      labelText = "<html><br style = \"font-size:2px;\"><p style = \"color:white; font-size:20px;\">ItemId = " + rs.getInt("ItemId") + "   Weight = " + rs.getInt("Weight") + "   Volume = " + rs.getInt("Volume") +
          "   LocationId = " + (rs.getInt("LocationId") == 0 ? "null" : "" + rs.getInt("LOCATIONID")) + 
          "   CharacterName = " + (rs.getString("cName") == null ? "null" : rs.getString("cName")) + "</p><br style = \"font-size:2px;\"></html>";
      rs.close();
    } catch(SQLException e){
      e.printStackTrace();
    }
    
    
    return labelText;
  }
  
  private void removeSelectedBackground() {
    if(selected != null)
      selected.setBackground(new Color(30, 30, 30));
  }
  
  private void deleteItem() {
    if(selected != null) {
      try {
        ri.getConncetion().createStatement().execute("DELETE FROM ITEM WHERE ItemId = " + selectedId);
        selected = null;
      }catch(SQLException e) {
        e.printStackTrace();
      }
      item.setViewportView(buildItems(ri.getNumItems()));
    }
  }
  
  private void editItem() {
    new EditItem();
  }
  
}
