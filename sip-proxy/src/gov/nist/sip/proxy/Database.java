package gov.nist.sip.proxy;

import java.util.Vector;
import javax.sip.address.URI;
import java.util.HashMap;

enum ForwardingStatus {
    FWDSTATUS_OK, FWDSTATUS_BLOCKED, FWDSTATUS_LOOP
}

class FwdRes {
    private ForwardingStatus status;
    private URI finalURI;

    FwdRes(ForwardingStatus status, URI finalURI) {
        this.status = status;
        this.finalURI = finalURI;
    }

    ForwardingStatus GetStatus() {
        return status;
    }

    URI GetURI() {
        return finalURI;
    }
}

enum Action {
    ACTION_BLOCK, ACTION_UNBLOCK, ACTION_FORWARD, ACTION_FRESET, ACTION_BALCHARGE, ACTION_BALINCR
}

public class Database {
    private HashMap<String, UserInfo> activeDatabase;

    public Database(){
        activeDatabase = new HashMap<String, UserInfo>();

        //NOTE: For debugging only, remove later
        
        UserInfo user1 = new UserInfo("fellos", "8cb2237d0679ca88db6464eac60da96345513964", UserInfo.Policy.POLICY_A, "192.168.1.1");
        UserInfo user2 = new UserInfo("piofellos", "8cb2237d0679ca88db6464eac60da96345513964", UserInfo.Policy.POLICY_A, "192.168.1.2");
        UserInfo user3 = new UserInfo("ipiosfellos", "8cb2237d0679ca88db6464eac60da96345513964", UserInfo.Policy.POLICY_B, "192.168.1.3");

        activeDatabase.put(user1.GetUserName(), user1); 
        activeDatabase.put(user2.GetUserName(), user2); 
        activeDatabase.put(user2.GetUserName(), user3); 
    }

    public boolean InsertUser(UserInfo user) {
        String key = user.GetUserName();

        if (activeDatabase.containsKey(key))
            return false;
        else {
            activeDatabase.put(key, user);
        }

        return true;
    }
    
    public UserInfo GetUser(String username) {
    	if (activeDatabase.containsKey(username))
    		return activeDatabase.get(username);
    	else
    		return null;
    }
    
    public Vector<String> GetAllUsers() {
    	Vector<String> res = new Vector<String>();
    	for (HashMap.Entry<String, UserInfo> entry : activeDatabase.entrySet()) {
    		res.add(entry.getKey());
    	}
    	
    	return res;
    }

    public boolean Delete(String username) {
        String key = username;

        if (!activeDatabase.containsKey(key)) {
            return false;
        }else {
            activeDatabase.remove(key);
        }

        return true;
    }

    public boolean Update(Action action, String user, String optUser, double balance) {
        UserInfo v;

        if (!activeDatabase.containsKey(user)) {
            return false;
        }

        v = activeDatabase.get(user);

        switch (action){
            case ACTION_BLOCK:
                //@NOTE: THA SOu GAMHSW AN DE DOULEUEI
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

    public FwdRes resolveForward(String source, String target){
        Vector<String> intermediateTargets = new Vector<String>();
        UserInfo v;

        v = activeDatabase.get(target);
        Vector<String> blocked = v.GetBlockedUsers();

        if (v.GetForwardTarget() == null){
            if(blocked.contains(source)) {
                return new FwdRes(ForwardingStatus.FWDSTATUS_BLOCKED, null);
                //return null;
            }else {
                return new FwdRes(ForwardingStatus.FWDSTATUS_OK, v.GetUserURI());
                //return v.GetUserURI();
            }
        }else {
            intermediateTargets.add(v.GetUserName());
            v = activeDatabase.get(v.GetForwardTarget());

            while (v.GetForwardTarget() != null) {
                intermediateTargets.add(v.GetUserName());
                v = activeDatabase.get(v.GetForwardTarget());

                if (intermediateTargets.contains(v.GetUserName())) //cycle detected
                    return new FwdRes(ForwardingStatus.FWDSTATUS_LOOP, null);
                    //return null;
            }

            blocked = v.GetBlockedUsers();

            if (!blocked.contains(source)) {
                return new FwdRes(ForwardingStatus.FWDSTATUS_OK, v.GetUserURI());
                //return v.GetUserURI();
            }else
                return new FwdRes(ForwardingStatus.FWDSTATUS_BLOCKED, null);
        }

        //return null;
    }
}
