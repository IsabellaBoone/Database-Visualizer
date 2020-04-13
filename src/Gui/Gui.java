package Gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Gui extends JFrame{

  JPanel buttons;
  JPanel view = new ItemsLayout();
  GridBagConstraints gbc = new GridBagConstraints(); 
  JButton itemLayout = new JButton("Items");
  JButton characterLayout = new JButton("Characters");
  JButton playerLayout = new JButton("Players");
  JButton locationLayout = new JButton("Locations");
  
  public Gui(){
    initJFrame();
    setButtons();
    setVisible(true);
  }
  
  private void initJFrame() {
    
    setLayout(new GridBagLayout());
    setBackground(Color.BLACK);
    setSize(1000, 750); 
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  private void setButtons() {
    itemLayout.addActionListener( new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        setItemView();
      }
    });
    characterLayout.addActionListener( new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        setCharacterView();
      }
    });
    playerLayout.addActionListener( new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        setPlayerView();
      }
    });
    locationLayout.addActionListener( new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        setLocationView();
      }
    });
    buttons = new JPanel(new GridBagLayout());
    gbc.insets = new Insets(0,0,0,0);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1;
    gbc.gridx = 0;
    gbc.gridy = 0;
    buttons.add(itemLayout, gbc);
    
    gbc.gridx = 1;
    gbc.gridy = 0;
    buttons.add(characterLayout, gbc);
    
    gbc.gridx = 2;
    gbc.gridy = 0;
    buttons.add(playerLayout, gbc);
    
    gbc.gridx = 3;
    gbc.gridy = 0;
    buttons.add(locationLayout, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.NORTHWEST;
    gbc.weighty = 1;
    buttons.setBackground(Color.GRAY);
    add(buttons, gbc);
    
  }

  public void setItemView() {
    remove(view);
    view = new ItemsLayout();
    System.out.println("Item View");
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    
    add(view,gbc);
    SwingUtilities.updateComponentTreeUI(this);
  }
  
  public void setCharacterView() {
    remove(view);
    view = new CharactersLayout();
    System.out.println("Character View");
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    
    add(view,gbc);
    SwingUtilities.updateComponentTreeUI(this);
  }
  
  public void setPlayerView() {
    remove(view);
    view = new PlayersLayout();
    System.out.println("Player View");
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    
    add(view,gbc);
    SwingUtilities.updateComponentTreeUI(this);
  }
  
  public void setLocationView() {
    remove(view);
    view = new LocationLayout();
    System.out.println("Location View");
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.BOTH;
    
    add(view,gbc);
    SwingUtilities.updateComponentTreeUI(this);
  }
  
}
