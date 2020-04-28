package Gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.*;

import database.RetrieveManipulateInformation;

public class Gui extends JFrame{
  RetrieveManipulateInformation rmi;
  JPanel buttons;
  JPanel view = new ItemsLayout(rmi);
  GridBagConstraints gbc = new GridBagConstraints(); 
  JButton itemLayout = new JButton("Items");
  JButton characterLayout = new JButton("Characters");
  JButton playerLayout = new JButton("Players");
  JButton locationLayout = new JButton("Locations");
  
  public Gui(RetrieveManipulateInformation getInfo) {
    rmi = getInfo; 
    initJFrame();
    setButtons();
    setVisible(true);
  }

  private void initJFrame() {
    setLayout(new GridBagLayout());
    setBackground(Color.BLACK);
    setSize(1000, 750); 
    setResizable(true);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent arg0) {
        try {
          RetrieveManipulateInformation.getConncetion().close();
          System.out.println("Connection Closed");
        } catch (SQLException e) {
          
          e.printStackTrace();
        }
        System.exit(0);
      }
    });
    setLocationRelativeTo(null);
  }
  
  //puts buttons on the screen at the top and puts actionlisteners on them.
  
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
  //channges gui to items view
  public void setItemView() {
    remove(view);
    view = new ItemsLayout(rmi);
    System.out.println("Item View");
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weighty = 100000;
    gbc.fill = GridBagConstraints.BOTH;
    
    add(view,gbc);
    SwingUtilities.updateComponentTreeUI(this);
  }
  //changes gui to characters view
  public void setCharacterView() {
    remove(view);
    view = new CharactersLayout(rmi);
    System.out.println("Character View");
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weighty = 100000;
    gbc.fill = GridBagConstraints.BOTH;
    
    add(view,gbc);
    SwingUtilities.updateComponentTreeUI(this);
  }
  //changes gui to players view
  public void setPlayerView() {
    remove(view);
    view = new PlayersLayout();
    System.out.println("Player View");
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weighty = 100000;
    gbc.fill = GridBagConstraints.BOTH;
    
    add(view,gbc);
    SwingUtilities.updateComponentTreeUI(this);
  }
  //changes the gui to location view
  public void setLocationView() {
    remove(view);
    view = new LocationLayout(rmi);
    System.out.println("Location View");
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weighty = 100000;
    gbc.fill = GridBagConstraints.BOTH;
    
    add(view,gbc);
    SwingUtilities.updateComponentTreeUI(this);
  }
  
}
