package snmp;

import org.snmp4j.PDU;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;
import snmp.exceptions.OIDDoesNotExistsException;
import snmp.exceptions.PDURequestFailedException;
import snmp.exceptions.SNMPTimeOutException;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

/**
 * @author Ahmed ALY<ahmed.aly@student.tgm.ac.at>
 * @version 08-10-2014
 */
public interface SnmpManager {
    public String getAsString(OID oid) throws SNMPTimeOutException,
            OIDDoesNotExistsException, PDURequestFailedException, OIDDoesNotExistsException;

    public Vector<? extends VariableBinding> get(OID[] oids)
            throws SNMPTimeOutException, PDURequestFailedException;

    public PDU createPDU(int type, OID[] oids);

    public List<? extends VariableBinding> getNext(OID[] oids)
            throws SNMPTimeOutException, PDURequestFailedException;

    public boolean checkResponsePDU(PDU responsePDU)
            throws PDURequestFailedException, SNMPTimeOutException;

    public List<VariableBinding> walk(OID rootID);

    public Mapping getMapping();

    public void start() throws IOException;

    public void stop() throws IOException;
}