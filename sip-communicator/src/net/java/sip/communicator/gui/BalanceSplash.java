package net.java.sip.communicator.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.EventObject;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.java.sip.communicator.SipCommunicator;
import net.java.sip.communicator.sip.CommunicationsException;
import net.java.sip.communicator.sip.RequestProcessing;

public class BalanceSplash {
	
	private static JFrame frame;
    private static String userBalance;
    static JComboBox<String> cb;
    
    public static void ShowBalance(int width, int height){
        RequestProcessing requestProcessing = new RequestProcessing();

        try {
        	requestProcessing.processInfo("BALANCE", null);
        } catch (Exception exc) {
        	System.out.println("Looks like we'll never get another call...");
        	return;
        }

        while(SipCommunicator.globalBalance == null){
            System.out.println("DEBUG: in the loop!");
            try {
                Thread.sleep(500);
            } catch (Exception exc) {
                System.out.println("DEBUG: exception!");
            }
        }

        System.out.println("DEBUG: out of the loop!");

        userBalance = SipCommunicator.globalBalance;
        JPanel panel = new JPanel();
        JButton user = new JButton(userBalance);
        JButton applyButton = new JButton("Apply");
        JButton cancelButton = new JButton("Cancel");

        JFrame localframe = new JFrame();
        frame = localframe;
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setLocation(200, 100);
        frame.add(panel);

        JLabel lbl = new JLabel("Would you like to cancel the forwarding to this user ?");
        lbl.setVisible(true);

        panel.add(lbl);
        applyButton.setEnabled(true);
        cancelButton.setEnabled(true);

        ActionListener applyListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                applyBalanceButton_actionPerformed(evt);
            }
        };
        applyButton.addActionListener(applyListener);

        ActionListener cancelListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                cancelBalanceButton_actionPerformed(evt);
            }
        };
        cancelButton.addActionListener(cancelListener);
        panel.add(user);
        panel.add(applyButton);
        panel.add(cancelButton);

    }
    static void applyBalanceButton_actionPerformed(EventObject evt) {
        String amount = (String) cb.getSelectedItem();
        System.out.println("I WILL ADD " + amount + " DOLLARS");

        RequestProcessing requestProcessing = new RequestProcessing();

        try {
            requestProcessing.processInfo("BALANCE", amount);
        } catch (Exception exc) {
            return;
        }

        SipCommunicator.globalBalance = null;
        frame.dispose();
    }

    static void cancelBalanceButton_actionPerformed(EventObject evt){
        SipCommunicator.globalBalance = null;
        frame.dispose();
    }	

}
