package snmp;

import org.snmp4j.Target;
import org.snmp4j.UserTarget;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.*;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import snmp.exceptions.WrongSnmpVersionException;

/**
 * Created by aaly on 09.10.14.
 */
public class USMAuthentication implements Authentication {
    private Address address;
    private int securityLevel, snmpVersion, retries, timeout;
    private String securityName;
    private UsmUser user;
    private String transportProtocol;

    public USMAuthentication(String transportProtocol, String ipAddress, int port, String userName, String password, int securityLevel, String securityName, int snmpVersion, int timeout, int retries) throws WrongSnmpVersionException {
        this.address = GenericAddress.parse(transportProtocol + ":" + ipAddress
                + "/" + port);
        this.securityLevel = securityLevel;
        this.securityName = securityName;
        if (snmpVersion == SnmpConstants.version3)
            this.snmpVersion = snmpVersion;
        else
            throw new WrongSnmpVersionException();
        this.retries = retries;
        this.timeout = timeout;
        this.transportProtocol = transportProtocol;

        USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(
                MPv3.createLocalEngineID()), 0);
        SecurityModels.getInstance().addSecurityModel(usm);
        user = new UsmUser(new OctetString(securityName),
                AuthMD5.ID, new OctetString(userName),
                PrivDES.ID, new OctetString(password));
    }

    public USMAuthentication(String transportProtocol, String ipAddress, int port, String userName, String password, int securityLevel, String securityName, int snmpVersion, int timeout) throws WrongSnmpVersionException {
        this(transportProtocol, ipAddress, port, userName, password, securityLevel, securityName, snmpVersion, timeout, 3);
    }

    public USMAuthentication(String transportProtocol, String ipAddress, int port, String userName, String password, int securityLevel, String securityName, int snmpVersion) throws WrongSnmpVersionException {
        this(transportProtocol, ipAddress, port, userName, password, securityLevel, securityName, snmpVersion, 6000, 3);
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

    @Override
    public String getTransportProtocol() {

        return transportProtocol;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    public UsmUser getUsmUser() {
        return user;
    }

    @Override
    public int getSnmpVersion() {
        return snmpVersion;
    }
}
