package Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Gui.Gui;

public class ItemsButton extends JButton implements ActionListener{
  public ItemsButton(String s) {
    super(s);
    addActionListener(this);
  }
  
  public void actionPerformed(ActionEvent ae) {
    System.out.println("Items Button");
  }
}
