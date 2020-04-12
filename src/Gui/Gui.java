package Gui;

import java.awt.*;
import javax.swing.*;

public class Gui {
  
  JFrame jf;
  Container one;
  JLabel items = new JLabel();
  
  public Gui() {
    jf = new JFrame();
    jf.setBackground(Color.BLACK);
    jf.setSize(1000, 750);
    jf.setLayout(new GridBagLayout());
    jf.setResizable(false);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf.setVisible(true);
    drawLayout();
  }
  
  private void drawLayout() {
    
  }
  
}
