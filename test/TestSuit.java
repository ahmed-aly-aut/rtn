/**
 * Created by aaly on 27.10.14.
 */

import content.TestBase;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import snmp.SnmpV1Test;
import snmp.SnmpV2cTest;
import snmp.SnmpV3Test;
import snmp.TestMapping;
import ssh.SSHConnectorTest;
import ssh.SSHManagerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SnmpV1Test.class,
        SnmpV2cTest.class,
        SnmpV3Test.class,
        TestBase.class,
        SSHConnectorTest.class,
        SSHManagerTest.class,
        TestMapping.class
})
public class TestSuit {
}
