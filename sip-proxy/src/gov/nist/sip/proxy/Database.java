package gov.nist.sip.proxy;

import java.util.Vector;
import java.util.HashMap;

enum Policy {
    POLICY_A, POLICY_B, POLICY_C
}

enum Action {
    ACTION_BLOCK, ACTION_UNBLOCK, ACTION_FORWARD, ACTION_FRESET, ACTION_BALCHARGE, ACTION_BALINCR
}

class UserInfo {
    String username;
    String pass;
    String forwardTarget;
    String address;
    Vector<String> blockedUsers;
    Policy billingPolicy;
    double balance;

    UserInfo(String username, String pass, Policy billingPolicy, String address) {
        this.username = username;
        this.pass = pass;
        this.billingPolicy = billingPolicy;
        this.address = address;
        blockedUsers = new Vector<String>();
        balance = 50.0;
    }

    String GetUserName() {
        return username;
    }

    Policy GetPolicy() {
        return billingPolicy;
    }

    String GetPassword() {
        return pass;
    }

    Vector<String> GetBlockedUsers() {
        return blockedUsers;
    }

    String GetForwardTarget() {
        return forwardTarget;
    }

    double GetBalance() {
        return balance;
    }

    void AddToBlockedUsers(String userToBlock) {
        if (!this.blockedUsers.contains(userToBlock))
            this.blockedUsers.add(userToBlock);
    }

    void RemoveFromBlockedUsers(String blockedUser) {
        if (this.blockedUsers.contains(blockedUser)) {
            this.blockedUsers.remove(blockedUser);
        }
    }

    void ForwardTo(String fwdTarget) {
        this.forwardTarget = fwdTarget;
    }

    void ClearFwd() {
        this.forwardTarget = null;
    }

    void UpdateBalance(double amount, Action action) {
        switch (action) {
            case ACTION_BALCHARGE:
                this.balance += amount;
                break;
            case ACTION_BALINCR:
                this.balance -= amount;
                break;
        }
    }
}

public class Database {
    HashMap<String, UserInfo> activeDatabase;

    boolean InsertUser(UserInfo user) {
        String key = user.GetUserName();

        if (activeDatabase.containsKey(key))
            return false;
        else {
            activeDatabase.put(key, user);
        }

        return true;
    }

    boolean Delete(String username) {
        String key = username;

        if (!activeDatabase.containsKey(key)) {
            return false;
        }else {
            activeDatabase.remove(key);
        }

        return true;
    }

    boolean Update(Action action, String user, String optUser, double balance) {
        UserInfo v;

        if (!activeDatabase.containsKey(user)) {
            return false;
        }

        v = activeDatabase.get(user);

        switch (action){
            case ACTION_BLOCK:
                //NOTE: THA SOu GAMHSW AN DE DOULEUEI
                if (optUser != null) {
                    if (activeDatabase.containsKey(optUser)) {
                        v.AddToBlockedUsers(optUser);
                    }else {
                        return false;
                    }
                }else {
                    return false;
                }
                break;
            case ACTION_UNBLOCK:
                if (optUser != null) {
                    if (activeDatabase.containsKey(optUser)) {
                        v.RemoveFromBlockedUsers(optUser);
                    }else {
                        return false;
                    }
                }else {
                    return false;
                }
                break;
            case ACTION_FORWARD:
                if (optUser != null) {
                    if (activeDatabase.containsKey(optUser)) {
                        v.ForwardTo(optUser);
                    }else {
                        return false;
                    }
                }else {
                    return false;
                }
                break;
            case ACTION_FRESET:
                v.ClearFwd();
                break;
            case ACTION_BALINCR:
            case ACTION_BALCHARGE:
                v.UpdateBalance(balance, action);
                break;
        }

        return true;
    }
}
