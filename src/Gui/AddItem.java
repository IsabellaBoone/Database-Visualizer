package Gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class AddItem extends JPanel {
  // 1st panel: item number and radio button for what item it is
  // 2nd panel: 
  
  public AddItem() {
    setLayout(new BorderLayout());
    setBackground(Color.GRAY);
    JLabel s = new JLabel("Bye");
    add(s);
  }
}
