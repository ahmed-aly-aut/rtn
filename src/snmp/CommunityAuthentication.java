package snmp;

import org.snmp4j.CommunityTarget;
import org.snmp4j.Target;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import snmp.exceptions.WrongSnmpVersion;

/**
 * Created by aaly on 09.10.14.
 */
public class CommunityAuthentication implements Authentication {
    private Address address;
    private int snmpVersion, retries, timeout;
    private String community;

    private String transportProtocol;

    public CommunityAuthentication(String transportProtocol, String ipAddress, int port, String community, int snmpVersion, int timeout, int retries) throws WrongSnmpVersion {
        this.address = GenericAddress.parse(transportProtocol + ":" + ipAddress
                + "/" + port);
        this.community = community;
        this.transportProtocol = transportProtocol;
        if (snmpVersion == SnmpConstants.version1 || snmpVersion == SnmpConstants.version2c)
            this.snmpVersion = snmpVersion;
        else
            throw new WrongSnmpVersion();
        this.retries = retries;
        this.timeout = timeout;
    }

    public CommunityAuthentication(String transportProtocol, String ipAddress, int port, String community, int snmpVersion, int timeout) throws WrongSnmpVersion {
        this(transportProtocol, ipAddress, port, community, snmpVersion, timeout, 3);
    }

    public CommunityAuthentication(String transportProtocol, String ipAddress, int port, String community, int snmpVersion) throws WrongSnmpVersion {
        this(transportProtocol, ipAddress, port, community, snmpVersion, 6000, 3);
    }

    @Override
    /**
     * This method returns a Target, which contains information about where the
     * data should be fetched and how.
     *
     * @return target
     */
    public Target getTarget() {
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(community));
        target.setAddress(address);
        target.setRetries(retries);
        target.setTimeout(timeout);
        target.setVersion(snmpVersion);
        return target;
    }

    public String getTransportProtocol() {
        return transportProtocol;
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public int getSnmpVersion() {
        return snmpVersion;
    }
}
