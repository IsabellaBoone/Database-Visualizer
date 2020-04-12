package Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class PlayersButton extends JButton implements ActionListener{
  public PlayersButton(String s) {
    super(s);
    addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("Players Button");
  }
}