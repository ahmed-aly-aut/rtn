package commands;

import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import snmp.SnmpManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by AHMED on 13.10.2014.
 */
public class ActionCommand {
    private SnmpManager snmpManager;

    public ActionCommand(SnmpManager snmpManager) {
        this.snmpManager = snmpManager;
    }

    public Vector<Vector<Variable>> getTable(List<String> policys) {
        try {
            Vector<Vector<Variable>> hm = new Vector<Vector<Variable>>();
            snmpManager.start();
            for(String policyEntry:policys) {
                List<VariableBinding> varBindings = snmpManager.walk(new OID(snmpManager.getMapping().readOID(policyEntry)));
                Vector<Variable> v = new Vector<Variable>();
                for (VariableBinding vb : varBindings) {
                    v.add(vb.getVariable());
                }
                hm.add(v);
            }
            snmpManager.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
