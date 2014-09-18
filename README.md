Basic tasks
===========
Implement a simple-to-use application to monitor and configure a hardware firewall appliance “Juniper NetScreen 5GT “. The firewall allows read access over the SNMP-protocol (your app should be able to test if SNMPv3 is available and if not fallback on SNMPv2c) and write access over Telnet.

Your app should accomplish following tasks:

List all configured firewall rules (policies) on the device, add the details of the mentioned services and zones as well.

Allow refreshing of the list by clicking a button and by a configurable time-intervall. Your GUI should remain responsive even with short refresh-intervals!

Visualize the thru-put for a highlighted firewall-rule (nice2have: multiple rows) in a line-chart (configurable refresh-interval, unit bytes/sec)

Encapsulate the data retrieval for further reuse and easy expansion. An UML-model of your design will help you defend it at the review!

Build a visual appealing and easy to use interface (there is more than Swing out there).

Additional information:
=======================
Since there is only one firewall-appliance available, the time each team can test with the hardware will be strictly limited. Therefor it is essentially to use mock-objects to allow testing the app during times where the hardware is not available.

An additional benefit of using mock-objects will be, that a CI-Server can use them for automated building and testing.

You only need to consider firewall-rules for TCP and UDP connections in IPv4.

You can find Information about the SNMP-Mibs special for the manufacturer of the used appliance here (maybe not all of the Mibs work with the used model): 
http://www.oidview.com/mibs/3224/md-3224-1.html

For exploring the SNMP-Data coming from the appliance you can use tools like this:

http://ireasoning.com/mibbrowser.shtml



Advanced tasks (obligatory for grades better than B3)
=====================================================
Additionally to the basic tasks your app should accomplish the following:

Alarm the user visually and per email if the config of the firewall-rules changes. To avoid polling use the SNMP-trap mechanism.

Allow managing of firewall-rules (CRUD). To accomplish this, you will have to send configuration commands via telnet or ssh. An admin-account is available per request.

Use multicast-groups to build a simple transaction system to serialize administrative tasks on the firewall (for example pass an “admin token” to recognize the collaborator who is allowed to write to the firewall). This should also work in a heterogenous environment (different implementations, different OSes), so you have to coordinate with other teams.

Make sure, that your interface to the firewall allows an easy change of the firewall-model (new releases, manufacturer, ...). It is not necessary to make this configurable in the GUI but must (explicitly) be considered in your software-design!

