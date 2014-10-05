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
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.Null;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import root.exceptions.OIDDoesntExistsException;
import root.exceptions.PDURequestFailedException;
import root.exceptions.SNMPTimeOutException;
import root.exceptions.WrongTransportProtocol;

/**
 *
 * @author aaly
 */
public abstract class SnmpManager {
	private String commmunity;
	private int snmpVersion, retries, timeout;
	private Address address;
	private Snmp snmp;
	private TransportMapping<? extends Address> transport;

	public SnmpManager(String transportProtocol, String ipAddress, int port,
			String commmunity, int snmpVersion, int retries, int timeout)
			throws WrongTransportProtocol {
		this.commmunity = commmunity;
		this.snmpVersion = snmpVersion;
		this.retries = retries;
		this.timeout = timeout;
		address = GenericAddress.parse(transportProtocol + ":" + ipAddress
				+ "/" + port);

		try {
			if (transportProtocol.equalsIgnoreCase("UDP")) {
				transport = new DefaultUdpTransportMapping();
			} else if (transportProtocol.equalsIgnoreCase("TCP")) {
				transport = new DefaultTcpTransportMapping();
			} else {
				throw new WrongTransportProtocol();
			}
		} catch (IOException e) {
			System.err.println("ERROR: Socket binding failed!");
			e.printStackTrace();
		}
		snmp = new Snmp(transport);
	}

	public String getAsString(OID oid) throws SNMPTimeOutException,
			OIDDoesntExistsException, PDURequestFailedException {
		// extract the response PDU (could be null if timed out)
		VariableBinding ret = (VariableBinding) get(new OID[] { oid }).get(0);
		String response = ret.getVariable().toString();
		if (response.equals("noSuchObject"))
			throw new OIDDoesntExistsException();
		return response;
	}

	public Vector<? extends VariableBinding> get(OID[] oids)
			throws SNMPTimeOutException, PDURequestFailedException {
		ResponseEvent responseEvent = null;
		Vector<? extends VariableBinding> vbs = null;
		try {
			// create the PDU
			PDU requestPDU = new PDU();
			requestPDU.setType(PDU.GET);
			// put the oid you want to get
			for (OID oid : oids) {
				requestPDU.add(new VariableBinding(oid));
			}
			// send the PDU
			responseEvent = snmp.send(requestPDU, getTarget());
			Logger.getLogger(SnmpManager.class.getName()).log(Level.INFO,
					responseEvent.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// extract the response PDU (could be null if timed out)
		if (responseEvent != null) {
			PDU responsePDU = responseEvent.getResponse();
			if (checkResponsePDU(responsePDU))
				vbs = responsePDU.getVariableBindings();
		} else {
			throw new SNMPTimeOutException();
		}

		return vbs;
	}

	public Vector<? extends VariableBinding> getNext(OID[] oids)
			throws SNMPTimeOutException, PDURequestFailedException {
		ResponseEvent responseEvent = null;
		Vector<? extends VariableBinding> vbs = null;
		try {
			// create the PDU
			PDU requestPDU = new PDU();
			requestPDU.setType(PDU.GETNEXT);
			// put the oid you want to get
			for (OID oid : oids) {
				requestPDU.add(new VariableBinding(oid));
			}
			requestPDU.setNonRepeaters(0);

			// send the PDU
			responseEvent = snmp.send(requestPDU, getTarget());
			Logger.getLogger(SnmpManager.class.getName()).log(Level.INFO,
					responseEvent.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// extract the response PDU (could be null if timed out)
		if (responseEvent != null) {
			PDU responsePDU = responseEvent.getResponse();
			if (checkResponsePDU(responsePDU))
				vbs = responsePDU.getVariableBindings();
		} else {
			throw new SNMPTimeOutException();
		}
		return vbs;
	}

	public List<VariableBinding> walk(OID rootOID) {
		List<VariableBinding> ret = new ArrayList<VariableBinding>();

		PDU requestPDU = new PDU();
		requestPDU.add(new VariableBinding(rootOID));
		requestPDU.setType(PDU.GETBULK);
		// maximum oid per pdu request
		requestPDU.setMaxRepetitions(5);

		try {

			boolean finished = false;
			int iter = 0;

			while (!finished) {
				VariableBinding vb = null;

				ResponseEvent respEvt = snmp.send(requestPDU, getTarget());
				Logger.getLogger(SnmpV2c.class.getName()).log(Level.INFO,
						"GETBULK iteration number " + iter++);
				PDU responsePDU = respEvt.getResponse();

				if (responsePDU != null) {
					Vector vbs = responsePDU.getVariableBindings();
					if (vbs != null && vbs.size() > 0) {
						for (int i = 0; i < vbs.size(); i++) {
							// vb sanity check
							vb = (VariableBinding) vbs.get(i);
							if (vb.getOid() == null) {
								Logger.getLogger(SnmpV2c.class.getName()).log(
										Level.INFO, "vb.getOid() == null");
								finished = true;
								break;
							} else if (vb.getOid().size() < rootOID.size()) {
								Logger.getLogger(SnmpV2c.class.getName())
										.log(Level.INFO,
												"vb.getOid().size() < targetOID.size()");
								finished = true;
								break;
							} else if (rootOID.leftMostCompare(rootOID.size(),
									vb.getOid()) != 0) {
								Logger.getLogger(SnmpV2c.class.getName()).log(
										Level.INFO,
										"targetOID.leftMostCompare() != 0)");
								finished = true;
								break;
							} else if (Null.isExceptionSyntax(vb.getVariable()
									.getSyntax())) {
								Logger.getLogger(SnmpV2c.class.getName())
										.log(Level.INFO,
												"Null.isExceptionSyntax(vb.getVariable().getSyntax())");
								finished = true;
								break;
							} else if (vb.getOid().compareTo(rootOID) <= 0) {
								Logger.getLogger(SnmpV2c.class.getName())
										.log(Level.INFO,
												"Variable received is not "
														+ "lexicographic successor of requested "
														+ "one:");
								Logger.getLogger(SnmpV2c.class.getName()).log(
										Level.INFO,
										vb.toString() + " <= " + rootOID);
								finished = true;
								break;
							}
							ret.add(vb);
						}
					}
				}

				if (!finished) {
					if (responsePDU == null) {
						Logger.getLogger(SnmpV2c.class.getName()).log(
								Level.INFO, "responsePDU == null");
						finished = true;
					} else if (responsePDU.getErrorStatus() != 0) {
						Logger.getLogger(SnmpV2c.class.getName())
								.log(Level.INFO,
										"responsePDU.getErrorStatus() != 0");
						Logger.getLogger(SnmpV2c.class.getName()).log(
								Level.INFO, responsePDU.getErrorStatusText());
						finished = true;
					} else {
						// Set up the variable binding for the next entry.
						requestPDU.setRequestID(new Integer32(0));
						requestPDU.set(0, vb);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * This method returns a Target, which contains information about where the
	 * data should be fetched and how.
	 * 
	 * @return target
	 */
	public Target getTarget() {
		CommunityTarget target = new CommunityTarget();
		target.setCommunity(new OctetString(commmunity));
		target.setAddress(address);
		target.setRetries(retries);
		target.setTimeout(timeout);
		target.setVersion(snmpVersion);
		return target;
	}
	
	/**
	 * This method returns true or false, which depend on the response PDU.
	 * If the response PDU is not null and don't have an error, true will be returned.
	 * 
	 * @param responsePDU
	 * @return
	 * @throws PDURequestFailedException
	 * @throws SNMPTimeOutException
	 */
	private boolean checkResponsePDU(PDU responsePDU)
			throws PDURequestFailedException, SNMPTimeOutException {
		if (responsePDU != null)
			if (responsePDU.getErrorStatus() == PDU.noError)
				return true;
			else
				throw new PDURequestFailedException(responsePDU);
		else
			throw new SNMPTimeOutException("Timeout: No Response from "
					+ address);
	}

	public void start() throws IOException {
		transport.listen();
	}

	public void stop() throws IOException {
		transport.close();
	}

	public String getCommmunity() {
		return commmunity;
	}

	public void setCommmunity(String commmunity) {
		this.commmunity = commmunity;
	}

	public int getSnmpVersion() {
		return snmpVersion;
	}

	public void setSnmpVersion(int snmpVersion) {
		this.snmpVersion = snmpVersion;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}