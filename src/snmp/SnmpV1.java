package root.snmp;

import org.snmp4j.mp.SnmpConstants;

import root.exceptions.WrongTransportProtocol;

public class SnmpV1 extends SnmpManager {

	public SnmpV1(String transportProtocol, String ipAddress, int port,
                  String commmunity, int retries, int timeout) throws WrongTransportProtocol {
		super(transportProtocol, ipAddress, port, commmunity,
				SnmpConstants.version1, retries, timeout);
	}

}