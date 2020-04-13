package Gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Buttons.*;

public class Gui extends JFrame{

  JPanel buttons;
  GridBagConstraints gbc = new GridBagConstraints(); 
  JButton itemLayout = new JButton("Items");
  JButton charactersLayout = new CharactersButton("Characters");
  JButton playersLayout = new PlayersButton("Players");
  JButton locationLayout = new LocationsButton("Locations");
  
  public Gui(){
    initJFrame();
    setButtons();
    setVisible(true);
  }
  
  private void initJFrame() {
    
    setLayout(new BorderLayout());
    setBackground(Color.BLACK);
    setSize(1000, 750); 
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  private void setButtons() {
    itemLayout.addActionListener( new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
          System.out.println("Items Button");
        }
        });
    charactersLayout.addActionListener( new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        System.out.println("Characters Button");
      }
      });
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
    
    add(buttons, BorderLayout.NORTH);
    
  }

  public void setItemsView() {
    JPanel items = new ItemsLayout();
    System.out.println("Items View");
    add(items, BorderLayout.SOUTH);
    update(getGraphics());
  }
  
}
