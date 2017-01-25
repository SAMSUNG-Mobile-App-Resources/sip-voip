package net.java.sip.communicator.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class BlockSplash {
	private static JFrame frame;
	static JComboBox<String> cb;
   
	public static void Block(int width, int height){
		   
		   String [] choices = new String[]{"marialena", "giorgos", "axilleas"};
		   JComboBox<String> localcb = new JComboBox<String>(choices);
		   JFrame localframe = new JFrame();
		   cb = localcb;
		   frame = localframe;  
		
		   JPanel panel = new JPanel();
		   JButton applyButton = new JButton("Apply");
		   JButton cancelButton = new JButton("Cancel");
		   
		   frame.setVisible(true);
	       frame.setSize(width, height);
	       frame.setLocation(200, 100);
	       frame.add(panel);
	       
	       JLabel lbl = new JLabel("Select one of the possible users and click Apply");
	       lbl.setVisible(true);

	       panel.add(lbl);
	       applyButton.setEnabled(true);
	       cancelButton.setEnabled(true);

	       ActionListener applyListener = new ActionListener()
	       {
	           public void actionPerformed(ActionEvent evt)
	           {
	               applyBlockButton_actionPerformed(evt);
	           }
	       };
	       applyButton.addActionListener(applyListener);
	       
	       ActionListener cancelListener = new ActionListener()
	       {
	           public void actionPerformed(ActionEvent evt)
	           {
	        	   cancelBlockButton_actionPerformed(evt);
	           }
	       };
	       cancelButton.addActionListener(cancelListener);
	       cb.setVisible(true);
	       panel.add(cb);
	       panel.add(applyButton);
	       panel.add(cancelButton);
	}
	public static void UnBlock(int width, int height){
		   
		   String [] choices = new String[]{"marialena", "giorgos", "axilleas"};
		   JComboBox<String> localcb = new JComboBox<String>(choices);
		   JFrame localframe = new JFrame();
		   cb = localcb;
		   frame = localframe;  
		
		   JPanel panel = new JPanel();
		   JButton applyButton = new JButton("Apply");
		   JButton cancelButton = new JButton("Cancel");
		   
		   frame.setVisible(true);
	       frame.setSize(width, height);
	       frame.setLocation(200, 100);
	       frame.add(panel);
	       
	       JLabel lbl = new JLabel("Select one of the possible users and click Apply");
	       lbl.setVisible(true);

	       panel.add(lbl);
	       applyButton.setEnabled(true);
	       cancelButton.setEnabled(true);

	       ActionListener applyListener = new ActionListener()
	       {
	           public void actionPerformed(ActionEvent evt)
	           {
	               applyunBlockButton_actionPerformed(evt);
	           }
	       };
	       applyButton.addActionListener(applyListener);
	       
	       ActionListener cancelListener = new ActionListener()
	       {
	           public void actionPerformed(ActionEvent evt)
	           {
	        	   cancelunBlockButton_actionPerformed(evt);
	           }
	       };
	       cancelButton.addActionListener(cancelListener);
	       cb.setVisible(true);
	       panel.add(cb);
	       panel.add(applyButton);
	       panel.add(cancelButton);
	}
	
	static void cancelunBlockButton_actionPerformed(EventObject evt){
		frame.dispose();
	}
	
	static void applyunBlockButton_actionPerformed(EventObject evt){
		   //TODO: AXILLEA FTIAKSE TIN GAMOVASI.
		   String user = (String) cb.getSelectedItem();
		   System.out.println("I CHOSE " +user);
	       frame.dispose();
	}
	
	static void applyBlockButton_actionPerformed(EventObject evt){
		   //TODO: AXILLEA FTIAKSE TIN GAMOVASI.
		   String user = (String) cb.getSelectedItem();
		   System.out.println("I CHOSE " +user);
	       frame.dispose();
	}
	static void cancelBlockButton_actionPerformed(EventObject evt){
		frame.dispose();
	}
}
