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

import net.java.sip.communicator.SipCommunicator;
import net.java.sip.communicator.sip.CommunicationsException;
import net.java.sip.communicator.sip.RequestProcessing;

public class BlockSplash {
    private static JFrame frame;
    static JComboBox<String> cb;

    public static void Block(int width, int height){
        RequestProcessing requestProcessing = new RequestProcessing();

        try {
        	requestProcessing.processInfo("BLOCK", null);
        } catch (Exception exc) {
        	System.out.println("ERROR: a tragedy has befallen our request!");
        	return;
        }

        while(SipCommunicator.globalChoices == null){
            System.out.println("DEBUG: in the loop!");
            try {
                Thread.sleep(500);
            } catch (Exception exc) {
                System.out.println("DEBUG: exception!");
            }
        }

        System.out.println("DEBUG: out of the loop!");

        JComboBox<String> localcb = new JComboBox<String>(SipCommunicator.globalChoices);
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
        RequestProcessing requestProcessing = new RequestProcessing();

        try {
        	requestProcessing.processInfo("UNBLOCK", null);
        } catch (Exception exc) {
        	System.out.println("Well, looks like they're gonna stay blocked forever...");
        	return;
        }

        while(SipCommunicator.globalChoices == null){
            System.out.println("DEBUG: in the loop!");
            try {
                Thread.sleep(500);
            } catch (Exception exc) {
                System.out.println("DEBUG: exception!");
            }
        }

        System.out.println("DEBUG: out of the loop!");

        JComboBox<String> localcb = new JComboBox<String>(SipCommunicator.globalChoices);
 
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
        SipCommunicator.globalChoices = null;
        frame.dispose();
    }

    static void applyunBlockButton_actionPerformed(EventObject evt){
        String user = (String) cb.getSelectedItem();
        System.out.println("I CHOSE " +user);

        RequestProcessing requestProcessing = new RequestProcessing();

        try {
            requestProcessing.processInfo("UNBLOCK", user);
        } catch (Exception exc) {
            return;
        }

        SipCommunicator.globalChoices = null;
        frame.dispose();
    }

    static void applyBlockButton_actionPerformed(EventObject evt){
        String user = (String) cb.getSelectedItem();
        System.out.println("I CHOSE " +user);
        RequestProcessing requestProcessing = new RequestProcessing();

        try {
            requestProcessing.processInfo("BLOCK", user);
        } catch (Exception exc) {
            return;
        }

        SipCommunicator.globalChoices = null;
        frame.dispose();
    }

    static void cancelBlockButton_actionPerformed(EventObject evt){
        SipCommunicator.globalChoices = null;
        frame.dispose();
    }
}
