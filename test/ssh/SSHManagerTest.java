package ssh;

import com.jcraft.jsch.JSchException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class SSHManagerTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();
    private String command, userName, password, connectionIP,
            knownHostsFileName;
    private int connectionPort, timeOutMilliseconds;
    private SSHConnector instance;
    private SSHManager sshManager;

    @Before
    public void setUp() {
        command = "get policy";
        userName = "5ahit";
        password = "Waeng7ohch8o";
        connectionIP = "10.0.100.10";
        connectionPort = 22;
        timeOutMilliseconds = 60000;
        knownHostsFileName = "/home/aaly/.ssh/known_hosts";

        instance = new SSHConnector(userName, password, connectionIP,
                null);
        sshManager = new SSHManager(instance);
    }

    /**
     * Test of sendCommand method, of class SSHManager.
     */
    @Test
    public void testSendCommand() {
        String expResult = "TestCat\n";
        // call sendCommand for each command and the output
        // (without prompts) is returned
        String result = sshManager.sendCommand("get policy");
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of sendCommand method, of class SSHManager.
     */
    @Test
    public void testSendCommandException() throws IOException {
        exception.expect(IOException.class);
        instance = new SSHConnector("as", password, connectionIP,
                null);
        sshManager = new SSHManager(instance);
        // call sendCommand for each command and the output
        // (without prompts) is returned
        String result = sshManager.sendCommand("bla");
    }

    /**
     * Test of sendCommand method, of class SSHManager.
     */
    @Test
    public void testSendCommandException2() throws JSchException {
        exception.expect(JSchException.class);
        instance = new SSHConnector("as", password, connectionIP,
                null);
        sshManager = new SSHManager(instance);
        // call sendCommand for each command and the output
        // (without prompts) is returned
        String result = sshManager.sendCommand("bla");
    }

    @Test
    public void testGetSshConector() {
        SSHConnector expResult = instance;

        SSHConnector result = sshManager.getSshConnector();

        assertEquals(expResult, result);
    }
}
