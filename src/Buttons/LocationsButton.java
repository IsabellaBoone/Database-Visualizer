package Buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class LocationsButton extends JButton implements ActionListener{
  public LocationsButton(String s) {
    super(s);
    addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("Locations Button");
  }
}
