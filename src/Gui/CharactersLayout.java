package Gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CharactersLayout extends JPanel{
  public CharactersLayout() {
    setLayout(new BorderLayout());
    JLabel s = new JLabel("Bye");
    add(s);
  }
}
