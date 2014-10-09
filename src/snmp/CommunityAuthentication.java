package snmp;

import org.snmp4j.CommunityTarget;
import org.snmp4j.Target;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OctetString;

/**
 * Created by aaly on 09.10.14.
 */
public class CommunityAuthentication implements Authentication {
    private Address address;
    private int snmpVersion, retries, timeout;
    private String community;

    public CommunityAuthentication(Address address, String community, int snmpVersion, int retries, int timeout) {
        this.address = address;
        this.community = community;
        this.snmpVersion = snmpVersion;
        this.retries = retries;
        this.timeout = timeout;
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
}
