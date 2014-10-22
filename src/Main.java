import net.percederberg.mibble.MibLoaderException;
import org.snmp4j.mp.SnmpConstants;

import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import snmp.*;
import commands.ActionCommand;
import commands.GetTableCommand;
import snmp.exceptions.*;
import ssh.SSHConnector;
import ssh.SSHManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.chrono.MinguoChronology;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        new Main().snmp();
    }


    public void snmp() {
        SnmpManager snmp;
        try {
            Authentication authentication = new CommunityAuthentication("udp", "10.0.100.10", 161, "5xHIT", SnmpConstants.version2c, 6000, 3);
            Mapping mapping = new Mapping();
            //mapping.load("NETSCREEN-SMI.mib");
            mapping.load("NS-POLICY.mib");
            snmp = new SnmpV2c(authentication, mapping);
            ActionCommand actionCommand = new ActionCommand(snmp);
            List<String> l = new ArrayList<String>();
            l.add("nsPlyId");
            l.add("nsPlyServiceName");
            l.add("nsPlySrcZone");
            GetTableCommand getTableCommand = new GetTableCommand(actionCommand,l);
            System.out.println(getTableCommand.execute());
            for(Vector<Variable> v:getTableCommand.execute())
                for(Variable v2:v)
                    System.out.println(v2);
            System.out.println("Test"+snmp.get(new OID[]{new OID(".1.3.6.1.2.1.1.1.0")}));
        } catch (WrongTransportProtocol e1) {
            System.err.println(e1.getMessage());
            e1.printStackTrace();
        } catch (WrongAuthentication wrongAuthentication) {
            wrongAuthentication.printStackTrace();
        } catch (WrongSnmpVersion wrongSnmpVersion) {
            wrongSnmpVersion.printStackTrace();
        } catch (SNMPTimeOutException e) {
            e.printStackTrace();
        } catch (PDURequestFailedException e) {
            e.printStackTrace();
        }
    }

    public void ssh() {
        SSHConnector connector = new SSHConnector("aaly", "Aly1234", "10.0.2.15",
                "/home/aaly/.ssh/known_hosts");
        SSHManager ssh = new SSHManager(connector);
        System.out.println("connected");
        System.out.println(ssh.sendCommand("cat test"));
        System.out.println("Connection closed");
    }

    public void mibble(){
        Mapping mapping = new Mapping();
        //mapping.load("NETSCREEN-SMI.mib");
        mapping.load("NS-POLICY.mib");
        System.out.println(mapping.readOID("nsPlyId"));
    }
}
