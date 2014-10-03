package root.snmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.Null;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpV1Utils {
	public static String get(Address targetaddress, String comm, OID oid) {
		String ret = null;
		TransportMapping transport;
		try {
			// create transport
			transport = new DefaultUdpTransportMapping();
			CommunityTarget target = new CommunityTarget();
			target.setCommunity(new OctetString("public"));
			target.setAddress(targetaddress);
			target.setRetries(3);
			target.setTimeout(2000);
			target.setVersion(SnmpConstants.version2c);

			// create the PDU
			PDU pdu = new PDU();
			pdu.setType(PDU.GET);
			// put the oid you want to get
			pdu.add(new VariableBinding(oid));
			pdu.setNonRepeaters(0);

			// pdu string
			System.out.println("pdu " + pdu.toString());
			Snmp snmp = new Snmp(transport);
			snmp.listen();

			// send the PDU
			ResponseEvent responseEvent = snmp.send(pdu, target);
			Logger.getLogger(SnmpV1Utils.class.getName()).log(Level.INFO,
					responseEvent.toString());
			// extract the response PDU (could be null if timed out)
			PDU responsePDU = responseEvent.getResponse();
			Logger.getLogger(SnmpV1Utils.class.getName()).log(Level.INFO,
					responsePDU.toString());
			Vector vbs = responsePDU.getVariableBindings();
			if (vbs.size() > 0) {
				VariableBinding vb = (VariableBinding) vbs.get(0);
				ret = vb.getVariable().toString();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public static VariableBinding getNext(Address targetaddress, String comm,
			OID oid) {
		VariableBinding ret = null;
		TransportMapping transport;
		try {
			// create transport
			transport = new DefaultUdpTransportMapping();
			CommunityTarget target = new CommunityTarget();
			target.setCommunity(new OctetString("public"));
			target.setAddress(targetaddress);
			target.setRetries(3);
			target.setTimeout(2000);
			target.setVersion(SnmpConstants.version2c);

			// create the PDU
			PDU pdu = new PDU();
			pdu.setType(PDU.GETNEXT);
			// put the oid you want to get
			pdu.add(new VariableBinding(oid));
			pdu.setNonRepeaters(0);

			// pdu string
			System.out.println("pdu " + pdu.toString());
			Snmp snmp = new Snmp(transport);
			snmp.listen();

			// send the PDU
			ResponseEvent responseEvent = snmp.send(pdu, target);
			Logger.getLogger(SnmpV1Utils.class.getName()).log(Level.INFO,
					responseEvent.toString());
			// extract the response PDU (could be null if timed out)
			PDU responsePDU = responseEvent.getResponse();
			Logger.getLogger(SnmpV1Utils.class.getName()).log(Level.INFO,
					responsePDU.toString());
			Vector vbs = responsePDU.getVariableBindings();
			if (vbs.size() > 0) {
				ret = (VariableBinding) vbs.get(0);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public static List<VariableBinding> walk(Address address, String comm,
			OID rootOID) {
		List<VariableBinding> ret = new ArrayList<VariableBinding>();

		PDU requestPDU = new PDU();
		requestPDU.add(new VariableBinding(rootOID));
		requestPDU.setType(PDU.GETBULK);
		// maximum oid per pdu request
		requestPDU.setMaxRepetitions(5);

		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(comm));
		target.setAddress(address);
		target.setVersion(SnmpConstants.version2c);

		try {
			TransportMapping transport = new DefaultUdpTransportMapping();
			Snmp snmp = new Snmp(transport);
			transport.listen();

			boolean finished = false;
			int iter = 0;

			while (!finished) {
				VariableBinding vb = null;

				ResponseEvent respEvt = snmp.send(requestPDU, target);
				Logger.getLogger(SnmpV1Utils.class.getName()).log(Level.INFO,
						"GETBULK iteration number " + iter++);
				PDU responsePDU = respEvt.getResponse();

				if (responsePDU != null) {
					Vector vbs = responsePDU.getVariableBindings();
					if (vbs != null && vbs.size() > 0) {
						for (int i = 0; i < vbs.size(); i++) {
							// vb sanity check
							vb = (VariableBinding) vbs.get(i);
							if (vb.getOid() == null) {
								Logger.getLogger(SnmpV1Utils.class.getName())
										.log(Level.INFO, "vb.getOid() == null");
								finished = true;
								break;
							} else if (vb.getOid().size() < rootOID.size()) {
								Logger.getLogger(SnmpV1Utils.class.getName())
										.log(Level.INFO,
												"vb.getOid().size() < targetOID.size()");
								finished = true;
								break;
							} else if (rootOID.leftMostCompare(rootOID.size(),
									vb.getOid()) != 0) {
								Logger.getLogger(SnmpV1Utils.class.getName())
										.log(Level.INFO,
												"targetOID.leftMostCompare() != 0)");
								finished = true;
								break;
							} else if (Null.isExceptionSyntax(vb.getVariable()
									.getSyntax())) {
								Logger.getLogger(SnmpV1Utils.class.getName())
										.log(Level.INFO,
												"Null.isExceptionSyntax(vb.getVariable().getSyntax())");
								finished = true;
								break;
							} else if (vb.getOid().compareTo(rootOID) <= 0) {
								Logger.getLogger(SnmpV1Utils.class.getName())
										.log(Level.INFO,
												"Variable received is not "
														+ "lexicographic successor of requested "
														+ "one:");
								Logger.getLogger(SnmpV1Utils.class.getName())
										.log(Level.INFO,
												vb.toString() + " <= "
														+ rootOID);
								finished = true;
								break;
							}
							ret.add(vb);
						}
					}
				}

				if (!finished) {
					if (responsePDU == null) {
						Logger.getLogger(SnmpV1Utils.class.getName()).log(
								Level.INFO, "responsePDU == null");
						finished = true;
					} else if (responsePDU.getErrorStatus() != 0) {
						Logger.getLogger(SnmpV1Utils.class.getName())
								.log(Level.INFO,
										"responsePDU.getErrorStatus() != 0");
						Logger.getLogger(SnmpV1Utils.class.getName()).log(
								Level.INFO, responsePDU.getErrorStatusText());
						finished = true;
					} else {
						// Set up the variable binding for the next entry.
						requestPDU.setRequestID(new Integer32(0));
						requestPDU.set(0, vb);
					}
				}
			}
			snmp.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
}