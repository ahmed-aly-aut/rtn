package root.snmp;

import org.snmp4j.Target;
import org.snmp4j.UserTarget;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.OctetString;

import root.exceptions.WrongTransportProtocol;

public class SnmpV3 extends SnmpManager {

	public SnmpV3(String transportProtocol, String ipAddress, int port,
                  String commmunity, int retries, int timeout) throws WrongTransportProtocol {
		super(transportProtocol, ipAddress, port, commmunity,
				SnmpConstants.version3, retries, timeout);
		USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(
				MPv3.createLocalEngineID()), 0);
		SecurityModels.getInstance().addSecurityModel(usm);
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
