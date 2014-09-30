package test.ssh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ssh.SSHManager;

import com.jcraft.jsch.JSchException;

@RunWith(JUnit4.class)
public class SSHManagerTest {
	private String command, userName, password, connectionIP,
			knownHostsFileName;

	private int connectionPort, timeOutMilliseconds;

	private SSHManager instance, instance2, instance3;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() {
		command = "cat test";
		userName = "aaly";
		password = "Aly1234";
		connectionIP = "10.0.105.229";
		connectionPort = 22;
		timeOutMilliseconds = 60000;
		knownHostsFileName = "/home/aaly/.ssh/known_hosts";

		instance2 = new SSHManager(userName, password, connectionIP,
				knownHostsFileName, connectionPort, timeOutMilliseconds);

		instance3 = new SSHManager(userName, password, connectionIP,
				knownHostsFileName, connectionPort);

	}

	/**
	 * Test of connect, of class SSHManager.
	 */
	@Test
	public void testConnect() {
		instance = new SSHManager(userName, password, connectionIP,
				knownHostsFileName);
		System.out.println("Connect");
		instance.connect();
		instance.close();
	}

	/**
	 * Test of sendCommand method, of class SSHManager.
	 */
	@Test
	public void testSendCommand() {
		String errorMessage = instance2.connect();

		if (errorMessage != null) {
			System.out.println(errorMessage);
			fail();
		}

		String expResult = "TestCat\n";
		// call sendCommand for each command and the output
		// (without prompts) is returned
		String result = instance2.sendCommand(command);
		// close only after all commands are sent
		instance2.close();
		assertEquals(expResult, result);
	}

	/**
	 * Test of sendCommand method, of class SSHManager.
	 */
	@Test
	public void testSendCommandException() {
		exception.expect(JSchException.class);
		String errorMessage = instance3.connect();

		// call sendCommand for each command and the output
		// (without prompts) is returned
		String result = instance3.sendCommand("bla");
		// close only after all commands are sent
		instance3.close();
	}

	/**
	 * Test of connect, of class SSHManager. {@link ExpectedException}
	 * JSchException
	 */
	@Test
	public void doCommonConstructorActionsException() {
		exception.expect(JSchException.class);
		instance = new SSHManager("fail", password, connectionIP,
				knownHostsFileName);

		System.out.println("failed to connect");
		instance.connect();
	}

	/**
	 * Test of connect, of class SSHManager. {@link ExpectedException}
	 * JSchException
	 */
	@Test
	public void testConnectException() {
		exception.expect(JSchException.class);
		instance = new SSHManager(userName, password, connectionIP, "");

		System.out.println("failed to connect");
		instance.connect();
	}
}
