package commands;

import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import snmp.SnmpManager;

import java.io.IOException;
import java.util.ArrayList;
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

    public HashMap<Integer, Vector<Variable>> getTable() {
        try {
            HashMap<Integer, Vector<Variable>> hm = new HashMap<Integer, Vector<Variable>>();
            snmpManager.start();
            List<VariableBinding> varBindings = snmpManager.walk(new OID(".1.3.6.1.4.1.3224.10.1.1"));
            if (varBindings.size() == 0)
                System.err.println("No result returned.");
            List<VariableBinding> policyId = new ArrayList<VariableBinding>();
            List<VariableBinding> policyName = new ArrayList<VariableBinding>();
            for (VariableBinding varBinding : varBindings) {
                if (varBinding.getOid().toString().startsWith("1.3.6.1.4.1.3224.10.1.1.1."))
                    policyId.add(varBinding);
                if (varBinding.getOid().toString().startsWith("1.3.6.1.4.1.3224.10.1.1.24."))
                    policyName.add(varBinding);
            }
            for (int i = 0; i < policyId.size(); i++) {
                Vector<Variable> v = new Vector<Variable>();
                v.add(policyName.get(i).getVariable());
                hm.put(policyId.get(i).getVariable().toInt(), v);
            }
            for (VariableBinding varBinding : policyId) {
                System.out.println(
                        varBinding.getOid() +
                                " : " +
                                varBinding.getVariable().getSyntaxString() +
                                " : " +
                                varBinding.getVariable());

            }
            for (VariableBinding varBinding : policyName) {
                System.out.println(
                        varBinding.getOid() +
                                " : " +
                                varBinding.getVariable().getSyntaxString() +
                                " : " +
                                varBinding.getVariable());

            }
            hm.toString();
            snmpManager.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
