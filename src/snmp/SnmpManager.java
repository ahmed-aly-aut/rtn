package root.snmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.Null;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;
import root.exceptions.OIDDoesntExistsException;
import root.exceptions.PDURequestFailedException;
import root.exceptions.SNMPTimeOutException;
import root.exceptions.WrongTransportProtocol;

/**
 * @author aaly
 */
public abstract class SnmpManager {
    private String commmunity;
    private int snmpVersion, retries, timeout;
    private Address address;
    private Snmp snmp;
    private TransportMapping<? extends Address> transport;

    public SnmpManager(String transportProtocol, String ipAddress, int port,
                       String commmunity, int snmpVersion, int retries, int timeout)
            throws WrongTransportProtocol {
        this.commmunity = commmunity;
        this.snmpVersion = snmpVersion;
        this.retries = retries;
        this.timeout = timeout;
        address = GenericAddress.parse(transportProtocol + ":" + ipAddress
                + "/" + port);

        try {
            if (transportProtocol.equalsIgnoreCase("UDP")) {
                transport = new DefaultUdpTransportMapping();
            } else if (transportProtocol.equalsIgnoreCase("TCP")) {
                transport = new DefaultTcpTransportMapping();
            } else {
                throw new WrongTransportProtocol();
            }
        } catch (IOException e) {
            System.err.println("ERROR: Socket binding failed!");
            e.printStackTrace();
        }
        snmp = new Snmp(transport);
    }

    public String getAsString(OID oid) throws SNMPTimeOutException,
            OIDDoesntExistsException, PDURequestFailedException {
        // extract the response PDU (could be null if timed out)
        VariableBinding ret = (VariableBinding) get(new OID[]{oid}).get(0);
        String response = ret.getVariable().toString();
        if (response.equals("noSuchObject"))
            throw new OIDDoesntExistsException();
        return response;
    }

    public Vector<? extends VariableBinding> get(OID[] oids)
            throws SNMPTimeOutException, PDURequestFailedException {
        ResponseEvent responseEvent = null;
        Vector<? extends VariableBinding> vbs = null;
        try {
            // send the PDU
            responseEvent = snmp.send(createPDU(PDU.GET, oids), getTarget());
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

    public Vector<? extends VariableBinding> getNext(OID[] oids)
            throws SNMPTimeOutException, PDURequestFailedException {
        ResponseEvent responseEvent = null;
        Vector<? extends VariableBinding> vbs = null;
        try {


            // send the PDU
            responseEvent = snmp.send(createPDU(PDU.GETNEXT, oids), getTarget());
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
     * This method returns a Target, which contains information about where the
     * data should be fetched and how.
     *
     * @return target
     */
    public Target getTarget() {
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(commmunity));
        target.setAddress(address);
        target.setRetries(retries);
        target.setTimeout(timeout);
        target.setVersion(snmpVersion);
        return target;
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
    private boolean checkResponsePDU(PDU responsePDU)
            throws PDURequestFailedException, SNMPTimeOutException {
        if (responsePDU != null)
            if (responsePDU.getErrorStatus() == PDU.noError)
                return true;
            else
                throw new PDURequestFailedException(responsePDU);
        else
            throw new SNMPTimeOutException("Timeout: No Response from "
                    + address);
    }

    public List<VariableBinding> walk(OID rootID) {
        TreeUtils treeUtils = new TreeUtils(snmp, new DefaultPDUFactory());
        treeUtils.setMaxRepetitions(Integer.MAX_VALUE);
        List<TreeEvent> events = treeUtils.getSubtree(getTarget(), rootID);

        // Get snmpwalk result.
        TreeEvent event = events.get(0);
        List<VariableBinding> varBindings = new ArrayList<VariableBinding>();
        if (event != null) {
            if (event.isError()) {
                System.err.println("oid [" + rootID + "] " + event.getErrorMessage());
            }
            for(VariableBinding  vb : event.getVariableBindings())
            varBindings.add(vb);
            if (varBindings == null || varBindings.size() == 0) {
                System.out.println("No result returned.");
            }
            System.out.println();
            for (VariableBinding varBinding : varBindings) {
                System.out.println(
                        varBinding.getOid() +
                                " : " +
                                varBinding.getVariable().getSyntaxString() +
                                " : " +
                                varBinding.getVariable());
            }
        }

        return varBindings;
    }

    public void start() throws IOException {
        transport.listen();
    }

    public void stop() throws IOException {
        transport.close();
    }

    public String getCommmunity() {
        return commmunity;
    }

    public void setCommmunity(String commmunity) {
        this.commmunity = commmunity;
    }

    public int getSnmpVersion() {
        return snmpVersion;
    }

    public void setSnmpVersion(int snmpVersion) {
        this.snmpVersion = snmpVersion;
    }

    public Snmp getSnmp() {
        return snmp;
    }

    public void setSnmp(Snmp snmp) {
        this.snmp = snmp;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


}