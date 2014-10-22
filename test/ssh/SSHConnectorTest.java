package ssh;

import com.jcraft.jsch.JSchException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class SSHConnectorTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private String command, userName, password, connectionIP,
            knownHostsFileName;
    private int connectionPort, timeOutMilliseconds;
    private SSHConnector instance, instance2, instance3, instance4;

    @Before
    public void setUp() {
        command = "cat test";
        userName = "aaly";
        password = "Aly1234";
        connectionIP = "10.0.104.98";
        connectionPort = 22;
        timeOutMilliseconds = 2000;
        knownHostsFileName = "/home/aaly/.ssh/known_hosts";

        instance = new SSHConnector(userName, password, connectionIP,
                knownHostsFileName);

        instance4 = new SSHConnector(userName, password, connectionIP,
                null);

        instance2 = new SSHConnector(userName, password, connectionIP,
                knownHostsFileName, connectionPort, timeOutMilliseconds);

        instance3 = new SSHConnector(userName, password, connectionIP,
                knownHostsFileName, connectionPort);
    }

    @Test
    public void testSSHConstruktor() throws JSchException {
        exception.expect(JSchException.class);
        instance2 = new SSHConnector(userName, password, connectionIP,
                null, connectionPort);
    }

    /**
     * Test of connect, of class SSHManager.
     */
    @Test
    public void testConnect() {
        instance.connect();
        instance.close();
    }

    /**
     * Test of connect, of class SSHManager. {@link ExpectedException}
     * JSchException
     */
    @Test
    public void testConnectException() throws JSchException {
        exception.expect(JSchException.class);
        instance2 = new SSHConnector(userName, password, "1.1.1.1",
                knownHostsFileName, connectionPort, timeOutMilliseconds);

        instance2.connect();
    }

    @Test
    public void testGetConnectionIP() {
        String expResult = connectionIP;
        // call sendCommand for each command and the output
        // (without prompts) is returned
        String result = instance.getConnectionIP();

        assertEquals(expResult, result);

    }

    @Test
    public void testGetConnectionPort() {
        int expResult = connectionPort;
        // call sendCommand for each command and the output
        // (without prompts) is returned
        int result = instance.getConnectionPort();

        assertEquals(expResult, result);
    }

    @Test
    public void testGetSessionConnection() {
        String expResult = connectionIP;
        // call sendCommand for each command and the output
        // (without prompts) is returned
        String result = instance.getSessionConnection().getHost();
        System.out.println(result);

        assertEquals(expResult, result);
    }
}
