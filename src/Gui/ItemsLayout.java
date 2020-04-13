package Gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ItemsLayout extends JPanel{
  
  public ItemsLayout() {
    setLayout(new BorderLayout());
    setBackground(Color.GRAY);
    JLabel s = new JLabel(buildHtml());
    s.setBackground(Color.WHITE);
    add(s, BorderLayout.CENTER);
  }
  
  private String buildHtml() {
    return "<html><p style=\"color:white;\">Hi</p></html>";
  }
  
}
