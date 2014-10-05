package root.snmp;

import org.snmp4j.mp.SnmpConstants;

import root.exceptions.WrongTransportProtocol;

public class SnmpV2c extends SnmpManager {

	public SnmpV2c(String transportProtocol, String ipAddress, int port,
                   String commmunity, int retries, int timeout) throws WrongTransportProtocol {
		super(transportProtocol, ipAddress, port, commmunity,
				SnmpConstants.version2c, retries, timeout);
	}

}