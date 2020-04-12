package Gui;

import java.awt.*;
import javax.swing.*;

public class Gui {
  
  JFrame jf;
  JPanel buttons;
  GridBagConstraints gbc = new GridBagConstraints(); 
  Container one;
  JLabel items = new JLabel();
  JButton itemLayout = new JButton("Items");
  JButton charactersLayout = new JButton("Characters");
  JButton playersLayout = new JButton("Players");
  JButton locationLayout = new JButton("Locations");
  
  public Gui() {
    
    initJFrame();
    setButtons();
    //drawLayout();
    
    jf.setVisible(true);
  }
  
  private void initJFrame() {
    jf = new JFrame();
    jf.setLayout(new BorderLayout());
    jf.setBackground(Color.BLACK);
    jf.setSize(1000, 750); 
    jf.setResizable(false);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  private void setButtons() {
    
    
    buttons = new JPanel(new GridBagLayout());
    gbc.fill = GridBagConstraints.HORIZONTAL;
    
    gbc.weightx = 0.5;
    gbc.gridx = 0;
    gbc.gridy = 0;
    buttons.add(itemLayout, gbc);
    
    gbc.gridx = 1;
    gbc.gridy = 0;
    buttons.add(charactersLayout, gbc);
    
    gbc.gridx = 2;
    gbc.gridy = 0;
    buttons.add(playersLayout, gbc);
    
    gbc.gridx = 3;
    gbc.gridy = 0;
    buttons.add(locationLayout, gbc);
    
    jf.add(buttons, BorderLayout.NORTH);
    
  }
  
  
  private void drawLayout() {
    
  }
  
}
