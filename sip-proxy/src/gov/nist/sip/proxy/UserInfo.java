package gov.nist.sip.proxy;

import java.util.Vector;
import javax.sip.address.URI;
import java.util.HashMap;

public class UserInfo {
    private URI uri;
    private String username;
    private String pass;
    private String name;
    private String lastName;
    private String mail;
    private String forwardTarget;
    private Vector<String> blockedUsers;
    private Policy billingPolicy;
    private double balance;
    private boolean isConnected;

    public enum Policy {
        POLICY_A, POLICY_B, POLICY_C
    }

    
    public UserInfo(String username, String pass, String name, String lastName, String mail, Policy billingPolicy) {
        this.username = username;
        this.pass = pass;
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.billingPolicy = billingPolicy;
        forwardTarget = null;
        uri = null;
        blockedUsers = new Vector<String>();
        balance = 50.0;
        isConnected = false;
    }

    public URI GetUserURI(){
        return uri;
    }

    public String GetUserName() {
        return username;
    }
    
    public String GetName() {
    	return name;
    }
    
    public String GetLastName() {
    	return lastName;
    }
    
    public String GetMail() {
    	return mail;
    }

    public Policy GetPolicy() {
        return billingPolicy;
    }

    public String GetPassword() {
        return pass;
    }

    public Vector<String> GetBlockedUsers() {
        return blockedUsers;
    }

    public String GetForwardTarget() {
        return forwardTarget;
    }

    public double GetBalance() {
        return balance;
    }

    public void SetUserURI(URI uri){
        this.uri = uri;
    }

    public void AddToBlockedUsers(String userToBlock) {
        if (!this.blockedUsers.contains(userToBlock))
            this.blockedUsers.add(userToBlock);
    }

    public void RemoveFromBlockedUsers(String blockedUser) {
        if (this.blockedUsers.contains(blockedUser))
            this.blockedUsers.remove(blockedUser);
    }

    public void ForwardTo(String fwdTarget) {
        this.forwardTarget = fwdTarget;
    }

    public void ClearFwd() {
        this.forwardTarget = null;
    }
    
    public void UserCameOnline() {
    	this.isConnected = true;
    }
    
    public void UserWentOffline() {
    	this.isConnected = false;
    }

    public void UpdateBalance(double amount, Action action) {
        switch (action) {
            case ACTION_BALCHARGE:
                this.balance -= amount;
                break;
            case ACTION_BALINCR:
                this.balance += amount;
                break;
            default:
            	System.out.println("ERROR: In UserInfo.UpdateBalance with an invalid action\n");
            	break;
        }
    }
}
