package net.java.sip.communicator.gui;

import java.awt.BorderLayout;
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
import javax.swing.JTextArea;

import net.java.sip.communicator.SipCommunicator;
import net.java.sip.communicator.sip.CommunicationsException;
import net.java.sip.communicator.sip.RequestProcessing;

public class BalanceSplash {
	private static JTextArea textArea = new JTextArea(1, 5);
	private static JFrame frame;
    private static String userBalance;
    static JComboBox<String> cb;
    private static JPanel mainPanel = new JPanel();
   
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
        JButton applyButton = new JButton("Apply");
        JButton cancelButton = new JButton("Cancel");
        applyButton.setEnabled(true);
        cancelButton.setEnabled(true);
        
        JPanel nested1 = new JPanel(); // FlowLayout
        JLabel lbl1 = new JLabel("Would you like to add to your balance?");
        lbl1.setVisible(true);
        nested1.add(lbl1);
        JPanel nested2 = new JPanel(); // 
        JLabel lbl3 = new JLabel("Current Balance");
        nested2.add(lbl3);
        nested2.add(new JLabel(userBalance));
        JPanel nested3 = new JPanel(); // FlowLayout
        nested3.add(new JLabel("Type your number"));
        nested3.add(textArea);  
        JPanel nested4 = new JPanel(); // FlowLayout
        nested4.add(applyButton);
        nested4.add(cancelButton);
        JPanel outer = new JPanel();
        outer.add(nested1);
        outer.add(nested2);
        outer.add(nested3);
        outer.add(nested4);
        mainPanel = outer;
        
        
        JFrame localframe = new JFrame();
        frame = localframe;
        frame.setVisible(true);
        frame.setSize(width, height);
        frame.setLocation(200, 100);
        frame.add(mainPanel);


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
    }
    
    static void applyBalanceButton_actionPerformed(EventObject evt) {
    	String amount = textArea.getText().trim();
        textArea.setText(null);
        System.out.println("I WILL ADD " + amount + " DOLLARS");

        try{
        	double d = Double.parseDouble(amount);
        	
        	if (d < 0)
        		return;
        	
        	RequestProcessing requestProcessing = new RequestProcessing();
       		try {
       			requestProcessing.processInfo("BALANCE", amount);
       		} catch (Exception exc) {
       			return;
       		}
        }
        catch(NumberFormatException nfe){
        	return;
        }finally {
        	SipCommunicator.globalBalance = null;
        	frame.dispose();
        }
    }

    static void cancelBalanceButton_actionPerformed(EventObject evt){
        SipCommunicator.globalBalance = null;
        frame.dispose();
    }	

}

