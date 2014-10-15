package snmp;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;
import snmp.exceptions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class SnmpV2c implements SnmpManager {
    private Snmp snmp;
    private TransportMapping<? extends Address> transport;
    private Authentication authentication;
    private Mapping mapping;

    public SnmpV2c(Authentication authentication, Mapping mapping) throws WrongTransportProtocol, WrongAuthentication, WrongSnmpVersion {
        if (authentication instanceof CommunityAuthentication) {
            if (authentication.getSnmpVersion() != SnmpConstants.version2c)
                throw new WrongSnmpVersion("Should be version 2c");
            this.authentication = authentication;
            this.mapping = mapping;
            try {
                if (authentication.getTransportProtocol().equalsIgnoreCase("UDP")) {
                    transport = new DefaultUdpTransportMapping();
                } else if (authentication.getTransportProtocol().equalsIgnoreCase("TCP")) {
                    transport = new DefaultTcpTransportMapping();
                } else {
                    throw new WrongTransportProtocol();
                }
            } catch (IOException e) {
                System.err.println("ERROR: Socket binding failed!");
                e.printStackTrace();
            }
            snmp = new Snmp(transport);
        } else
            throw new WrongAuthentication("CommunityAuthentication has to be used!");
    }

    /**
     * The method getAsString(Oid oid) is using the @see SnmpManager#get to get a String value of the specified OID.
     *
     * @param oid the requested OID
     * @return a String with the result from the specified OID
     * @throws SNMPTimeOutException                      will be thrown if a timeout with request happens
     * @throws snmp.exceptions.OIDDoesNotExistsException will be thrown if the specified OID does not exist
     * @throws PDURequestFailedException                 will be thrown if an error occurs within the request
     */
    public String getAsString(OID oid) throws SNMPTimeOutException,
            OIDDoesNotExistsException, PDURequestFailedException {
        // extract the response PDU (could be null if timed out)
        VariableBinding ret = get(new OID[]{oid}).get(0);
        String response = ret.getVariable().toString();
        if (response.equals("noSuchObject"))
            throw new OIDDoesNotExistsException();
        return response;
    }

    /**
     * The method get can be specified with an Array of requested OIDs. A Vector with elements of the subclass VariableBinding will be returned.
     * OID requested from the method GET can only return a value. Therefore the OIDd must be a scalar and not a branch.
     *
     * @param oids the requested OIDs
     * @return A Vector with VariableBindings
     * @throws SNMPTimeOutException      will be thrown if a timeout with request happens
     * @throws PDURequestFailedException will be thrown if an error occurs within the request
     * @see org.snmp4j.smi.VariableBinding
     */
    public Vector<? extends VariableBinding> get(OID[] oids)
            throws SNMPTimeOutException, PDURequestFailedException {
        ResponseEvent responseEvent = null;
        Vector<? extends VariableBinding> vbs = null;
        try {
            // send the PDU
            responseEvent = snmp.send(createPDU(PDU.GET, oids), authentication.getTarget());
            Logger.getLogger(SnmpManager.class.getName()).log(Level.INFO,
                    responseEvent.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // extract the response PDU (could be null if timed out)
        if (responseEvent != null) {
            PDU responsePDU = responseEvent.getResponse();
            if (checkResponsePDU(responsePDU))
                vbs = responsePDU.getVariableBindings();
        } else {
            throw new SNMPTimeOutException();
        }

        return vbs;
    }

    /**
     * This method creates the requst and puts it in an PDU object. This object will be returnd and used from Methods such as get,walk and getnext.
     *
     * @param type the type of the response, possible are PDU.GET,PDU.GETNEXT, PDU.GETBULK
     * @param oids the requested OIDs
     * @return the request PDU
     * @see org.snmp4j.PDU
     */
    public PDU createPDU(int type, OID[] oids) {
        // create the PDU
        PDU requestPDU = new PDU();
        requestPDU.setType(type);
        // put the oid you want to get
        for (OID oid : oids) {
            requestPDU.add(new VariableBinding(oid));
        }
        return requestPDU;
    }

    public List<? extends VariableBinding> getNext(OID[] oids)
            throws SNMPTimeOutException, PDURequestFailedException {
        ResponseEvent responseEvent = null;
        Vector<? extends VariableBinding> vbs = null;
        try {


            // send the PDU
            responseEvent = snmp.send(createPDU(PDU.GETNEXT, oids), authentication.getTarget());
            Logger.getLogger(SnmpManager.class.getName()).log(Level.INFO,
                    responseEvent.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // extract the response PDU (could be null if timed out)
        if (responseEvent != null) {
            PDU responsePDU = responseEvent.getResponse();
            if (checkResponsePDU(responsePDU))
                vbs = responsePDU.getVariableBindings();
        } else {
            throw new SNMPTimeOutException();
        }
        return vbs;
    }

    /**
     * This method returns true or false, which depend on the response PDU.
     * If the response PDU is not null and don't have an error, true will be returned.
     *
     * @param responsePDU
     * @return
     * @throws PDURequestFailedException
     * @throws SNMPTimeOutException
     */
    public boolean checkResponsePDU(PDU responsePDU)
            throws PDURequestFailedException, SNMPTimeOutException {
        if (responsePDU != null)
            if (responsePDU.getErrorStatus() == PDU.noError)
                return true;
            else
                throw new PDURequestFailedException(responsePDU);
        else
            throw new SNMPTimeOutException("Timeout: No Response from "
                    + authentication.getAddress());
    }

    /**
     * This method needs a valid
     *
     * @param rootID
     * @return
     */
    public List<VariableBinding> walk(OID rootID) {
        TreeUtils treeUtils = new TreeUtils(snmp, new DefaultPDUFactory());
        treeUtils.setMaxRepetitions(Integer.MAX_VALUE);
        List<TreeEvent> events = treeUtils.getSubtree(authentication.getTarget(), rootID);

        // Get snmpwalk result.
        List<VariableBinding> varBindings = new ArrayList<VariableBinding>();
        for (int i = 0; i < events.size(); i++) {
            TreeEvent event = events.get(i);

            if (event != null) {
                if (event.isError()) {
                    System.err.println("oid [" + rootID + "] " + event.getErrorMessage());
                }
                Collections.addAll(varBindings, event.getVariableBindings());
            }
        }
        return varBindings;
    }

    public Mapping getMapping() {
        return mapping;
    }

    @Override
    public void start() throws IOException {
        transport.listen();
    }

    @Override
    public void stop() throws IOException {
        transport.close();
    }


}