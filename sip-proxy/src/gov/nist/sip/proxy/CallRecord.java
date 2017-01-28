package gov.nist.sip.proxy;

import java.util.Date;
import java.util.HashMap;

public class CallRecord {
    public class CallInfo {
        private String callerUsername;
        private long time;

        public void SetCaller(String username) {
            callerUsername = username;
        }

        public String GetCaller() {
            return callerUsername;
        }

        public void SetTime(long time) {
            this.time = time;
        }

        public long GetTime() {
            return time;
        }
    }

    HashMap<String, CallInfo> callRecord;
   
    
    CallRecord() {
        callRecord = new HashMap<String, CallInfo>();
    }


    public void RegisterNewCall(String callId, String caller) {
        CallInfo call = new CallInfo();
        call.SetCaller(caller);
        
        Date callTimer = new Date();
        call.SetTime(callTimer.getTime());

        callRecord.put(callId, call);
    }

    public CallInfo GetCallForCharging(String callId) {
        if (callRecord.containsKey(callId)) {
            CallInfo res = callRecord.remove(callId);
            long callStartTime = res.GetTime();
            
            Date callTimer = new Date();
            res.SetTime(callTimer.getTime() - callStartTime); //set time to be the call's duration
            
            return res;
        }else
            return null;
    }

}
