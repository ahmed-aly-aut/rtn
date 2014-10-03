package root.snmp;

import java.util.List;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

/**
 *
 * @author masjoko
 */
public class SnmpUtils {
	public static String get(int version, Address targetaddress, String comm,
			OID oid) {
		if (version == SnmpConstants.version1) {
			return SnmpV1Utils.get(targetaddress, comm, oid);
		} else if (version == SnmpConstants.version2c) {
			return SnmpV2cUtils.get(targetaddress, comm, oid);
		} else {
			return null;
		}
	}

	public static VariableBinding getNext(int version, Address targetaddress,
			String comm, OID oid) {
		if (version == SnmpConstants.version1) {
			return SnmpV1Utils.getNext(targetaddress, comm, oid);
		} else if (version == SnmpConstants.version2c) {
			return SnmpV2cUtils.getNext(targetaddress, comm, oid);
		} else {
			return null;
		}

	}

	public static List<VariableBinding> walk(int version, Address address,
			String comm, OID rootOID) {
		if (version == SnmpConstants.version1) {
			return SnmpV1Utils.walk(address, comm, rootOID);
		} else if (version == SnmpConstants.version2c) {
			return SnmpV2cUtils.walk(address, comm, rootOID);
		} else {
			return null;
		}
	}
}