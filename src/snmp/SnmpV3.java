package root.snmp;

import org.snmp4j.PDU;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Target;
import org.snmp4j.UserTarget;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.*;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;

import org.snmp4j.smi.VariableBinding;
import root.exceptions.WrongTransportProtocol;

public class SnmpV3 extends SnmpManager {

    public SnmpV3(String transportProtocol, String username, String password, String ipAddress, int port,
                  String commmunity, int retries, int timeout) throws WrongTransportProtocol {
        super(transportProtocol, ipAddress, port, commmunity,
                SnmpConstants.version3, retries, timeout);
        USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(
                MPv3.createLocalEngineID()), 0);
        SecurityModels.getInstance().addSecurityModel(usm);
        UsmUser user = new UsmUser(new OctetString("MD5DES"),
                AuthMD5.ID, new OctetString(username),
                PrivDES.ID, new OctetString(password));

        getSnmp().getUSM().addUser(new OctetString("MD5DES"),
                user);
    }

    public PDU createPDU(int type, OID[] oids){
        // create the PDU
        PDU requestPDU = new ScopedPDU();
        requestPDU.setType(type);
        // put the oid you want to get
        for (OID oid : oids) {
            requestPDU.add(new VariableBinding(oid));
        }
        return requestPDU;
    }

    @Override
    public Target getTarget() {
        UserTarget target = new UserTarget();
        target.setAddress(getAddress());
        target.setRetries(getRetries());
        target.setTimeout(getTimeout());
        target.setVersion(SnmpConstants.version3);
        target.setSecurityLevel(SecurityLevel.AUTH_PRIV);
        target.setSecurityName(new OctetString("MD5DES"));
        return target;
    }

}
