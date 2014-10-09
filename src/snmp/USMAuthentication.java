package snmp;

import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.UserTarget;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.*;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OctetString;

/**
 * Created by aaly on 09.10.14.
 */
public class USMAuthentication implements Authentication {
    private Address address;
    private int securityLevel, snmpVersion, retries, timeout;
    private String securityName;

    public USMAuthentication(Address address, String userName, String password, Snmp snmp, int securityLevel, String securityName, int snmpVersion, int retries, int timeout) {
        this.address = address;
        this.securityLevel = securityLevel;
        this.securityName = securityName;
        this.snmpVersion = snmpVersion;
        this.retries = retries;
        this.timeout = timeout;

        USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(
                MPv3.createLocalEngineID()), 0);
        SecurityModels.getInstance().addSecurityModel(usm);
        UsmUser user = new UsmUser(new OctetString(securityName),
                AuthMD5.ID, new OctetString(userName),
                PrivDES.ID, new OctetString(password));

        snmp.getUSM().addUser(new OctetString(securityName),
                user);
    }

    @Override
    public Target getTarget() {
        UserTarget target = new UserTarget();
        target.setAddress(address);
        target.setRetries(retries);
        target.setTimeout(timeout);
        target.setVersion(snmpVersion);
        target.setSecurityLevel(securityLevel);
        target.setSecurityName(new OctetString(securityName));
        return target;
    }
}
