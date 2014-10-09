package snmp;

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
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;
import snmp.exceptions.OIDDoesntExistsException;
import snmp.exceptions.PDURequestFailedException;
import snmp.exceptions.SNMPTimeOutException;
import snmp.exceptions.WrongTransportProtocol;

/**
 * @author Ahmed ALY<ahmed.aly@student.tgm.ac.at>
 * @version 08-10-2014
 */
public interface SnmpManager {
    public String getAsString(OID oid) throws SNMPTimeOutException,
            OIDDoesntExistsException, PDURequestFailedException;

    public Vector<? extends VariableBinding> get(OID[] oids)
            throws SNMPTimeOutException, PDURequestFailedException;

    public PDU createPDU(int type, OID[] oids);

    public List<? extends VariableBinding> getNext(OID[] oids)
            throws SNMPTimeOutException, PDURequestFailedException;

    public boolean checkResponsePDU(PDU responsePDU)
            throws PDURequestFailedException, SNMPTimeOutException;

    public List<VariableBinding> walk(OID rootID);


    public void start() throws IOException;

    public void stop() throws IOException;
}