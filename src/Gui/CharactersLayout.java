package Gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CharactersLayout extends JPanel{
  public CharactersLayout() {
    setLayout(new BorderLayout());
    setBackground(Color.GRAY);
    JLabel s = new JLabel("Bye");
    add(s);
  }
}
