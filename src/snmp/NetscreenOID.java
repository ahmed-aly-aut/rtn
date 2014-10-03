package root.snmp;

import org.snmp4j.smi.OID;

public interface NetscreenOID {

	public static final OID resCpuLast5Min = new OID(
			".1.3.6.1.4.1.3224.16.1.3.0");
	/**
	 * Leaf: Each policy is identified by a unique policy ID.
	 */
	public static final OID policyId = new OID(".1.3.6.1.4.1.3224.10.1.1.1");
	/**
	 * Leaf: Each policy has a name.
	 */
	public static final OID policyName = new OID(".1.3.6.1.4.1.3224.10.1.1.24");
	/**
	 * Leaf: Bytes go through this policy per second
	 */
	public static final OID policyBps = new OID(".1.3.6.1.4.1.3224.10.2.1.6");
	/**
	 * Leaf: Show the status of one policy entry. inactive(0), inuse(1),
	 * hidden(2)
	 */
	public static final OID policyStatus = new OID(
			".1.3.6.1.4.1.3224.10.1.1.23");

	/**
	 * Leaf: Accepted TCP and UDP services like Telnet, FTP, SMTP and HTTP.
	 * Indicates the traffic service type ,which the policy allows. 'Any' means
	 * the policy allows all service to go through.
	 */
	public static final OID policyServiceName = new OID(
			".1.3.6.1.4.1.3224.10.1.1.25");

	/**
	 * Leaf: Describes the source zone name traffic flow passes.
	 */
	public static final OID policySrcZone = new OID(
			".1.3.6.1.4.1.3224.10.1.1.3");

	/**
	 * Leaf: Describes the destination zone name traffic flow passes.
	 */
	public static final OID policyDestZone = new OID(
			".1.3.6.1.4.1.3224.10.1.1.4");

	/**
	 * Leaf:
	 */
	public static final OID policySrcAddr = new OID(
			".1.3.6.1.4.1.3224.10.1.1.5");

	/**
	 * Leaf:
	 */
	public static final OID policyDestAddr = new OID(
			".1.3.6.1.4.1.3224.10.1.1.6");

	/**
	 * Leaf:
	 */
	public static final OID policyService = new OID(
			".1.3.6.1.4.1.3224.10.1.1.7");

	/**
	 * Leaf: Describes what the firewall does to the traffic it receives.
	 */
	public static final OID policyAction = new OID("1.3.6.1.4.1.3224.10.1.1.8");

}
