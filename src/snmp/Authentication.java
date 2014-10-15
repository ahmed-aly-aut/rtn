package snmp;

import org.snmp4j.Target;
import org.snmp4j.smi.Address;


/**
 * Created by aaly on 09.10.14.
 */
public interface Authentication {

    /**
     * This method returns a Target, which contains information about where the
     * data should be fetched and how.
     *
     * @return target
     */
    public Target getTarget();

    //TODO comments
    public String getTransportProtocol();

    //TODO comments
    public Address getAddress();

    //TODO comments
    public int getSnmpVersion();

}
