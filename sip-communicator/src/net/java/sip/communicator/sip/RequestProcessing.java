package net.java.sip.communicator.sip;

import java.text.ParseException;
import java.util.ArrayList;

import javax.sip.ClientTransaction;
import javax.sip.InvalidArgumentException;
import javax.sip.ServerTransaction;
import javax.sip.SipException;
import javax.sip.TransactionUnavailableException;
import javax.sip.address.Address;
import javax.sip.address.SipURI;
import javax.sip.address.URI;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.ContactHeader;
import javax.sip.header.ContentTypeHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.MaxForwardsHeader;
import javax.sip.header.ToHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;

import net.java.sip.communicator.SipCommunicator;
import net.java.sip.communicator.common.Console;
import net.java.sip.communicator.common.Utils;

public class RequestProcessing {
    protected static final Console console = Console.getConsole(RequestProcessing.class);
    private SipManager sipManCallback = null;

    public void processInfo(String action, String choice)throws
        CommunicationsException, ParseException {
            try{
            	if (choice != null && choice.equals("(empty)"))
            		return;
            	
                console.logEntry();
                //Edo leipei to username.
                String username = "fellos";
                String defaultDomainName =
                    Utils.getProperty("net.java.sip.communicator.sip.DEFAULT_DOMAIN_NAME");
                if (defaultDomainName != null){
                    username = username + "@" + defaultDomainName;
                }
                //Let's be uri fault tolerant
                if (username.toLowerCase().indexOf("sip:") == -1 //no sip scheme
                        && username.indexOf('@') != -1 //most probably a sip uri
                   ) {
                    username = "sip:" + username;
                   }

                //Request URI
                URI requestURI;
                System.out.println(defaultDomainName);
                System.out.println(username);
                try {
                    requestURI = SipCommunicator.sipManager.addressFactory.createURI(username);
                }
                catch (ParseException ex) {
                    console.error(username + " is not a legal SIP uri!", ex);
                    throw new CommunicationsException(username+
                            " is not a legal SIP uri!", ex);
                }

                CallIdHeader callIdHeader = SipCommunicator.sipManager.sipProvider.getNewCallId();
                CSeqHeader cSeqHeader;
                try {
                    cSeqHeader = SipCommunicator.sipManager.headerFactory.createCSeqHeader(1,
                            Request.INFO);
                }
                catch (ParseException ex) {
                    //Shouldn't happen
                    console.error(ex, ex);
                    throw new CommunicationsException(
                            "An unexpected erro occurred while"
                            + "constructing the CSeqHeadder", ex);
                }
                catch (InvalidArgumentException ex) {
                    //Shouldn't happen
                    console.error(
                            "An unexpected erro occurred while"
                            + "constructing the CSeqHeadder", ex);
                    throw new CommunicationsException(
                            "An unexpected erro occurred while"
                            + "constructing the CSeqHeadder", ex);
                }

                FromHeader fromHeader = SipCommunicator.sipManager.getFromHeader();
                Address toAddress = SipCommunicator.sipManager.addressFactory.createAddress(
                        requestURI);
                ToHeader toHeader;

                try {
                    toHeader = SipCommunicator.sipManager.headerFactory.createToHeader(
                            toAddress, null);
                }
                catch (ParseException ex) {
                    //Shouldn't happen
                    console.error(
                            "Null is not an allowed tag for the to header!", ex);
                    throw new CommunicationsException(
                            "Null is not an allowed tag for the to header!", ex);
                }

                ArrayList viaHeaders = SipCommunicator.sipManager.getLocalViaHeaders();
                MaxForwardsHeader maxForwards = SipCommunicator.sipManager.getMaxForwardsHeader();
                ContactHeader contactHeader = SipCommunicator.sipManager.getContactHeader();
                Request request = null;
                ContentTypeHeader contentTypeHeader = null;
                String subtype;

                if (choice == null)
                    subtype = "B";
                else
                    subtype = "C";

                if(action.equals("BLOCK")){
                    contentTypeHeader = SipCommunicator.sipManager.headerFactory.createContentTypeHeader("Block", subtype);
                }
                else if(action.equals("UNBLOCK")){
                    contentTypeHeader = SipCommunicator.sipManager.headerFactory.createContentTypeHeader("Unblock", subtype);
                }
                else if(action.equals("FORWARD")){
                    contentTypeHeader = SipCommunicator.sipManager.headerFactory.createContentTypeHeader("Forward", subtype);
                }
                else if(action.equals("RFORWARD")){
                    contentTypeHeader = SipCommunicator.sipManager.headerFactory.createContentTypeHeader("Unforward", subtype);
                }

                try {
                    String empty = "";
                    byte[] someArray;

                    if (choice == null)
                        someArray = empty.getBytes();
                    else
                        someArray = choice.getBytes();

                    request = SipCommunicator.sipManager.messageFactory.createRequest(requestURI,
                            Request.INFO,
                            callIdHeader, cSeqHeader, fromHeader, toHeader, viaHeaders,
                            maxForwards, contentTypeHeader, someArray);
                }
                catch (ParseException ex) {
                    console.error(
                            "Failed to create info Request!", ex);
                    throw new CommunicationsException(
                            "Failed to create info Request!", ex);
                }
                //
                request.addHeader(contactHeader);

                Address fromAddress = fromHeader.getAddress();
                ClientTransaction regTrans = null;
                try {
                    regTrans = SipCommunicator.sipManager.sipProvider.getNewClientTransaction(
                            request);
                }
                catch (TransactionUnavailableException ex) {
                    console.error("Could not create a register transaction!\n"
                            + "Check that the Registrar address is correct!",
                            ex);
                    //throw was missing - reported by Eero Vaarnas
                    throw new CommunicationsException(
                            "Could not create a register transaction!\n"
                            + "Check that the Registrar address is correct!");
                }
                try {
                    regTrans.sendRequest();
                    if( console.isDebugEnabled() )
                        console.debug("sent request= " + request);
                }
                //we sometimes get a null pointer exception here so catch them all POKEMON
                catch (Exception ex) {
                    console.error("Could not send out the register request!", ex);
                    throw new CommunicationsException(
                            "Could not send out the info request!", ex);
                }
            }
            finally{
                console.logExit();
            }
            System.out.println("ProcessINFO");
        }

    void processInfoOK(ClientTransaction clientTransaction, Response response){
        try{
            System.out.println("ProcessINFO OK");
            console.logEntry();
            /*try {
              Request ack = (Request) clientTransaction.getDialog().
              createRequest(Request.ACK);
              clientTransaction.getDialog().sendAck(ack);
            }
            catch (SipException ex) {
            console.error("Failed to acknowledge call!", ex);
            SipCommunicator.sipManager.fireCommunicationsError(new CommunicationsException(
            "Failed to acknowledge call!", ex));
            return;
            }*/

            String fullContentTypeHeader = response.getHeader(ContentTypeHeader.NAME).toString();
            String subtype = fullContentTypeHeader.split("/")[1].trim();

            if (subtype.equals("B")) {
                byte [] res = response.getRawContent();
                String temp = new String(res);
                String [] users = temp.split("/:/");

                SipCommunicator.globalChoices = users;
                System.out.println("DEBUG: Wrote to globalChoices!");
            }
            /*for(int idx = 0; idx < users.length - 1; idx++){
              SipCommunicator.globalChoices[idx] = users[idx];
              }*/
        }
        finally{
            console.logExit();
        }
    }
}
