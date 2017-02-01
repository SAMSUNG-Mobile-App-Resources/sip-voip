package gov.nist.sip.proxy;

import java.util.Vector;
import javax.sip.address.URI;

public class UserInfo implements java.io.Serializable {
    private String username;
    private String pass;
    private String name;
    private String lastName;
    private String mail;
    private String forwardTarget;
    private Vector<String> blockedUsers;
    private Policy billingPolicy;
    private double balance;
    transient private String uri;
    transient private boolean isConnected;

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

    public String GetUserURI(){
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

    public void SetUserURI(String uri){
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

    public double CalculateCharge(long time) {
        long timeSecs = time / 1000; //time arg is in msecs
        double res = 0;

        switch (billingPolicy) {
            case POLICY_A:
                if (timeSecs > 120)
                    res = 120 * 0.01 + (timeSecs - 120) * 0.1;
                else
                    res = timeSecs * 0.01;
                break;
            case POLICY_B:
                if (timeSecs > 480)
                    res = 480 * 0.01 + (timeSecs - 480) * 0.2;
                else
                    res = timeSecs * 0.01;
                break;
            case POLICY_C:
                if (timeSecs > 1200)
                    res = 1200 * 0.01 + (timeSecs - 1200) * 0.3;
                else
                    res = timeSecs * 0.01;
                break;
        }

        return res;
    }
}
