package Gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ItemsLayout extends JPanel{
  public ItemsLayout() {
    setLayout(new BorderLayout());
    JLabel s = new JLabel("Hi");
    add(s);
  }
}
