package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class ItemsLayout extends JPanel{
  

  JScrollPane item = new JScrollPane();
  JLabel selected = null;
  
  public ItemsLayout() {
    setLayout(new BorderLayout());
    setBackground(Color.GRAY);
    add(Label(),BorderLayout.NORTH);
    //item.add(buildItems(10));
    item.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    item.add(item.createVerticalScrollBar());
    add(item, BorderLayout.CENTER);
    
    item.setViewportView(buildItems(50));
    revalidate();
    repaint();
    
  }
  
  private JLabel Label(){
    JLabel x = new JLabel("<html><p style = \"color:white; font-size:15px\">Items</p></html>", SwingConstants.CENTER);
    x.setBackground(Color.WHITE);
    x.setPreferredSize(new Dimension(100,20));
    return x;
  }
  
  private JPanel buildItems(int rows) {
    JPanel data = new JPanel();
    data.setLayout(new GridLayout(rows, 1));
    for(int i = 0; i < rows; i++) {
      JLabel x = new JLabel(buildHtml());
      x.addMouseListener( new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
              removeSelectedBackground();
              x.setText("<html><p style=\"color:black; background-color: yellow;\">Hi</p></html>");
              selected = x;
          }
      });
      x.setPreferredSize(new Dimension(20,50));
      data.add(x);
    }
    return data;
  }

  private String buildHtml() {
    return "<html><p style=\"color:black;\">Hi</p></html>";
  }
  
  private void removeSelectedBackground() {
    if(selected != null)
      selected.setText("<html><p style=\"color:black;\">Hi</p></html>");
  }
  
}
