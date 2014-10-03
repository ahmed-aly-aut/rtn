package root.snmp;

import org.snmp4j.smi.OID;

public interface OIDDecoder extends NetscreenOID {

	public static final OID sysDescr = new OID(".1.3.6.1.2.1.1.1.0");
	
	public static final OID sysObjectID = new OID(".1.3.6.1.2.1.1.2.0");
	
	public static final OID sysUpTime = new OID(".1.3.6.1.2.1.1.3.0");
	
	public static final OID sysContact = new OID(".1.3.6.1.2.1.1.4.0");
	
	public static final OID sysName = new OID(".1.3.6.1.2.1.1.5.0");
	
	public static final OID sysLocation = new OID(".1.3.6.1.2.1.1.6.0");
	
	public static final OID sysService = new OID(".1.3.6.1.2.1.1.7.0");

}
