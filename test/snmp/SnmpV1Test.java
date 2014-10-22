package snmp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;
import snmp.exceptions.*;

import java.io.IOException;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class SnmpV1Test {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private SnmpManager snmp;

    private Authentication authentication;
    private Mapping mapping;

    @Before
    public void setUp() throws WrongAuthentication, WrongSnmpVersion, WrongTransportProtocol {
        mapping = new Mapping();
        //mapping.load("NETSCREEN-SMI.mib");
        mapping.load("NS-POLICY.mib");
        authentication = new CommunityAuthentication("udp", "10.0.100.10", 161, "5xHIT", SnmpConstants.version1);
        snmp = new SnmpV1(authentication, mapping);

    }

    @Test
    public void testSnmpV1ConstructorWrongSnmpVersionException() throws WrongSnmpVersion, WrongTransportProtocol, WrongAuthentication {
        exception.expect(WrongSnmpVersion.class);
        authentication = new CommunityAuthentication("udp", "10.0.100.10", 161, "5xHIT", 1);
        snmp = new SnmpV1(authentication, mapping);
    }

    @Test
    public void testSnmpV1Constructor2() throws WrongSnmpVersion, WrongTransportProtocol, WrongAuthentication {
        authentication = new CommunityAuthentication("tcp", "10.0.100.10", 161, "5xHIT", SnmpConstants.version1);
        snmp = new SnmpV1(authentication, mapping);
    }

    @Test
    public void testSnmpV1ConstructorWrongTransportProtocolException() throws WrongSnmpVersion, WrongTransportProtocol, WrongAuthentication {
        exception.expect(WrongTransportProtocol.class);
        authentication = new CommunityAuthentication("tls", "10.0.100.10", 161, "5xHIT", SnmpConstants.version1);
        snmp = new SnmpV1(authentication, mapping);
    }

    @Test
    public void testSnmpV1ConstructorWrongAuthenticationException() throws WrongSnmpVersion, WrongTransportProtocol, WrongAuthentication {
        exception.expect(WrongAuthentication.class);
        authentication = new USMAuthentication("udp", "10.0.100.10", 161, "qazxswed", "qazxswed", SecurityLevel.AUTH_PRIV, "authPrivMd5Aes", SnmpConstants.version3);
        snmp = new SnmpV1(authentication, mapping);
    }

    @Test
    public void testGetAsString() throws WrongSnmpVersion, WrongTransportProtocol, WrongAuthentication, SNMPTimeOutException, OIDDoesNotExistsException, PDURequestFailedException, IOException {
        String expected = "NetScreen-5GT version 5.0.0r8.1 (SN: 0064122003000765, Firewall+VPN)";
        authentication = new CommunityAuthentication("udp", "10.0.100.10", 161, "5xHIT", SnmpConstants.version1);
        snmp = new SnmpV1(authentication, mapping);
        snmp.start();
        String result = snmp.getAsString(new OID(".1.3.6.1.2.1.1.1.0"));
        snmp.stop();
        assertEquals(expected, result);
    }

    @Test
    public void testGetAsStringPDURequestFailedException() throws WrongSnmpVersion, WrongTransportProtocol, WrongAuthentication, SNMPTimeOutException, PDURequestFailedException, IOException {
        exception.expect(PDURequestFailedException.class);
        authentication = new CommunityAuthentication("udp", "10.0.100.10", 161, "5xHIT", SnmpConstants.version1);
        snmp = new SnmpV1(authentication, mapping);
        snmp.start();
        String result = snmp.getAsString(new OID(".1.3.6.1.4.1.9.9.661.1.1.1.13"));
        snmp.stop();
    }

    @Test
    public void testGetAsStringSNMPTimeOutException() throws WrongSnmpVersion, WrongTransportProtocol, WrongAuthentication, SNMPTimeOutException, PDURequestFailedException, IOException {
        exception.expect(SNMPTimeOutException.class);
        authentication = new CommunityAuthentication("udp", "10.0.100.10", 161, "5xHIT", SnmpConstants.version1, 1, 1);
        snmp = new SnmpV1(authentication, mapping);
        snmp.start();
        String result = snmp.getAsString(new OID(".1.3.6.1.2.1.1.1.0"));
        snmp.stop();
    }

    @Test
    public void testGet() throws WrongSnmpVersion, WrongTransportProtocol, WrongAuthentication, SNMPTimeOutException, OIDDoesNotExistsException, PDURequestFailedException, IOException {
        String expected = "NetScreen-5GT version 5.0.0r8.1 (SN: 0064122003000765, Firewall+VPN)";
        authentication = new CommunityAuthentication("udp", "10.0.100.10", 161, "5xHIT", SnmpConstants.version1);
        snmp = new SnmpV1(authentication, mapping);
        snmp.start();
        Vector<? extends VariableBinding> result = snmp.get(new OID[]{new OID(".1.3.6.1.2.1.1.1.0")});
        snmp.stop();
        assertEquals(expected, result.get(0).getVariable().toString());
    }

    @Test
    public void testGetPDURequestFailedException() throws WrongSnmpVersion, WrongTransportProtocol, WrongAuthentication, SNMPTimeOutException, PDURequestFailedException, IOException {
        exception.expect(PDURequestFailedException.class);
        authentication = new CommunityAuthentication("udp", "10.0.100.10", 161, "5xHIT", SnmpConstants.version1);
        snmp = new SnmpV1(authentication, mapping);
        snmp.start();
        Vector<? extends VariableBinding> result = snmp.get(new OID[]{new OID(".1.3.6.1.4.1.9.9.661.1.1.1.13")});
        snmp.stop();
    }

    @Test
    public void testGetSNMPTimeOutException() throws WrongSnmpVersion, WrongTransportProtocol, WrongAuthentication, SNMPTimeOutException, PDURequestFailedException, IOException {
        exception.expect(SNMPTimeOutException.class);
        authentication = new CommunityAuthentication("udp", "10.0.100.10", 161, "5xHIT", SnmpConstants.version1, 0, 1);
        snmp = new SnmpV1(authentication, mapping);
        snmp.start();
        Vector<? extends VariableBinding> result = snmp.get(new OID[]{new OID(".1.3.6.1.2.1.1.1.0")});
        snmp.stop();
    }

    @Test
    public void testGetNext() throws WrongSnmpVersion, WrongTransportProtocol, WrongAuthentication, SNMPTimeOutException, OIDDoesNotExistsException, PDURequestFailedException, IOException {
        String expected = "1.3.6.1.4.1.3224.1.14";
        authentication = new CommunityAuthentication("udp", "10.0.100.10", 161, "5xHIT", SnmpConstants.version1);
        snmp = new SnmpV1(authentication, mapping);
        snmp.start();
        Vector<? extends VariableBinding> result = snmp.getNext(new OID[]{new OID(".1.3.6.1.2.1.1.1.0")});
        snmp.stop();
        assertEquals(expected, result.get(0).getVariable().toString());
    }

    @Test
    public void testGetNextPDURequestFailedException() throws WrongSnmpVersion, WrongTransportProtocol, WrongAuthentication, SNMPTimeOutException, PDURequestFailedException, IOException {
        exception.expect(PDURequestFailedException.class);
        authentication = new CommunityAuthentication("udp", "10.0.100.10", 161, "5xHIT", SnmpConstants.version1);
        snmp = new SnmpV1(authentication, mapping);
        snmp.start();
        Vector<? extends VariableBinding> result = snmp.getNext(new OID[]{new OID(".1.3.6.1.6.3.21.0")});
        snmp.stop();
    }

    @Test
    public void testGetNextSNMPTimeOutException() throws WrongSnmpVersion, WrongTransportProtocol, WrongAuthentication, SNMPTimeOutException, PDURequestFailedException, IOException {
        exception.expect(SNMPTimeOutException.class);
        authentication = new CommunityAuthentication("udp", "10.0.100.10", 161, "5xHIT", SnmpConstants.version1, 1, 3);
        snmp = new SnmpV1(authentication, mapping);
        Exception e = null;

        snmp.start();
        Vector<? extends VariableBinding> result = snmp.getNext(new OID[]{new OID(".1.3.6.1.2.1.1.1.0")});
        snmp.stop();
    }
}