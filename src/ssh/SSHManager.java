package ssh;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by aaly on 09.10.14.
 */
public class SSHManager {
    private static final Logger LOGGER = Logger.getLogger(SSHConnector.class
            .getName());

    private SSHConnector sshConnector;

    public SSHManager(SSHConnector sshConnector) {
       this. sshConnector = sshConnector;
    }

    private String logWarning(String warnMessage) {
        if (warnMessage != null) {
            LOGGER.log(Level.WARNING, "{0}:{1} - {2}", new Object[]{
                    sshConnector.getConnectionIP(), sshConnector.getConnectionPort(), warnMessage});
        }

        return warnMessage;
    }

    public String sendCommand(String command) {
        sshConnector.connect();
        StringBuilder outputBuffer = new StringBuilder();

        try {
            Channel channel = sshConnector.getSessionConnection().openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.connect();
            InputStream commandOutput = channel.getInputStream();
            int readByte = commandOutput.read();

            while (readByte != 0xffffffff) {
                outputBuffer.append((char) readByte);
                readByte = commandOutput.read();
            }
            channel.disconnect();
        } catch (IOException ioX) {
            logWarning(ioX.getMessage());
            sshConnector.close();
            return null;
        } catch (JSchException jschX) {
            logWarning(jschX.getMessage());
            sshConnector.close();
            return null;
        }
        sshConnector.close();
        return outputBuffer.toString();
    }

    public SSHConnector getSshConnector(){
        return sshConnector;
    }
}
