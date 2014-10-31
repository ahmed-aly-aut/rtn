package ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SSHConnector {
    private static final Logger LOGGER = Logger.getLogger(SSHConnector.class
            .getName());
    private JSch jschSSHChannel;
    private String userName;
    private String connectionIP;
    private int connectionPort;
    private String password;
    private Session sessionConnection;
    private int timeOut;

    public SSHConnector(String userName, String password, String connectionIP,
                        String knownHostsFileName, int connectionPort,
                        int timeOut) {
        this.userName = userName;
        this.password = password;
        this.connectionIP = connectionIP;
        this.connectionPort = connectionPort;
        this.timeOut = timeOut;

        jschSSHChannel = new JSch();
        try {
            if (knownHostsFileName != null) {
                jschSSHChannel.setKnownHosts(knownHostsFileName);

                sessionConnection = jschSSHChannel.getSession(userName,
                        connectionIP, connectionPort);
                sessionConnection.setPassword(password);
            } else {
                sessionConnection = jschSSHChannel.getSession(userName,
                        connectionIP, connectionPort);
                sessionConnection.setPassword(password);
                sessionConnection.setConfig("StrictHostKeyChecking", "no");
            }
        } catch (JSchException e) {
            logError(e.getMessage());
        }
    }

    public SSHConnector(String userName, String password, String connectionIP,
                      String knownHostsFileName) {
        this(userName, password, connectionIP, knownHostsFileName, 22, 1000);
    }

    public SSHConnector(String userName, String password, String connectionIP,
                      String knownHostsFileName, int connectionPort) {
        this(userName, password, connectionIP, knownHostsFileName, connectionPort, 1000);
    }

    private String logError(String errorMessage) {
        if (errorMessage != null) {
            LOGGER.log(Level.SEVERE, "{0}:{1} - {2}", new Object[]{
                    connectionIP, connectionPort, errorMessage});
        }

        return errorMessage;
    }

    public void connect() {
        try {
            sessionConnection.connect(timeOut);
        } catch (JSchException e) {
            logError(e.getMessage());
        }
    }

    public void close() {
        sessionConnection.disconnect();
    }

    public String getConnectionIP() {
        return connectionIP;
    }

    public int getConnectionPort() {
        return connectionPort;
    }

    public Session getSessionConnection() {
        return sessionConnection;
    }

}