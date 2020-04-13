package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

public class ItemsLayout extends JPanel{
  

  JScrollPane item = new JScrollPane();
  
  public ItemsLayout() {
    setLayout(new BorderLayout());
    add(Label(),BorderLayout.NORTH);
    item.add(buildItems(10));
    item.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    item.add(item.createVerticalScrollBar());
    add(item, BorderLayout.CENTER);
    revalidate();
    repaint();
    
  }
  
  private JLabel Label(){
    return new JLabel("THIS IS A LABEL");
  }
  
  private JPanel buildItems(int rows) {
    JPanel data = new JPanel();
    data.setLayout(new GridLayout(rows, 1));
    
    JLabel x = new JLabel(buildHtml());
    data.add(x);
    return data;
  }
  
  private String buildHtml() {
    return "<html><p style=\"color:black;\">Hi</p></html>";
  }
  
}
