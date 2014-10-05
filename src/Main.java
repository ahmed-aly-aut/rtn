package root;

import java.io.IOException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

import root.exceptions.OIDDoesntExistsException;
import root.exceptions.PDURequestFailedException;
import root.exceptions.SNMPTimeOutException;
import root.exceptions.WrongTransportProtocol;
import root.snmp.OIDDecoder;
import root.snmp.SnmpManager;
import root.snmp.SnmpV2c;
import root.ssh.SSHManager;

public class Main {
	public static void main(String[] args) {
		new Main().snmpwalk();
	}

	public void snmpwalk() {
		SnmpManager snmp = null;
		try {
			snmp = new SnmpV2c("udp", "10.0.100.10", 161, "5xHIT", 3, 2000);
		} catch (WrongTransportProtocol e1) {
			System.err.println(e1.getMessage());
			e1.printStackTrace();
		}
		ConcurrentHashMap<Integer, Vector<Variable>> table = new ConcurrentHashMap<Integer, Vector<Variable>>();
		try {
			snmp.start();
			List<VariableBinding> policyIDs = snmp.walk(OIDDecoder.policyId);
			List<VariableBinding> policyServiceNAMEs = snmp
					.walk(OIDDecoder.policyServiceName);
			List<VariableBinding> policySrcAddrs = snmp
					.walk(OIDDecoder.policySrcAddr);
			List<VariableBinding> policyDestAddrs = snmp
					.walk(OIDDecoder.policyDestAddr);
			List<VariableBinding> policySrcZones = snmp
					.walk(OIDDecoder.policySrcZone);
			List<VariableBinding> policyDestZones = snmp
					.walk(OIDDecoder.policyDestZone);
			List<VariableBinding> policyActions = snmp
					.walk(OIDDecoder.policyAction);

			for (int i = 0; i < policyIDs.size(); i++) {
				Vector<Variable> v = new Vector<Variable>();
				v.add(policyServiceNAMEs.get(i).getVariable());
				v.add(policySrcAddrs.get(i).getVariable());
				v.add(policyDestAddrs.get(i).getVariable());
				v.add(policySrcZones.get(i).getVariable());
				v.add(policyDestZones.get(i).getVariable());
				v.add(policyActions.get(i).getVariable());
				table.put(policyIDs.get(i).getVariable().toInt(), v);

				System.out.print(i + 1);
				for (Variable var : table.get(i + 1))
					System.out.print("\t" + var);
				System.out.print("\n");
			}

			snmp.stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void snmpget() {
		SnmpManager snmp = null;
		try {
			snmp = new SnmpV2c("udp", "10.0.100.10", 161, "5xHIT", 3, 2000);
		} catch (WrongTransportProtocol e1) {
			System.err.println(e1.getMessage());
			e1.printStackTrace();
		}
		try {
			snmp.start();
			System.out.println(snmp.getAsString(OIDDecoder.sysDescr));
			System.out.println(snmp.getAsString(OIDDecoder.sysName));
			snmp.stop();
		} catch (IOException | SNMPTimeOutException | OIDDoesntExistsException
				| PDURequestFailedException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void ssh() {
		SSHManager ssh = new SSHManager("aaly", "Aly1234", "10.0.105.229",
				"/home/aaly/.root.ssh/known_hosts");
		ssh.connect();
		System.out.println("connected");
		System.out.println(ssh.sendCommand("cat test"));
		ssh.close();
		System.out.println("Connection closed");
	}

}
