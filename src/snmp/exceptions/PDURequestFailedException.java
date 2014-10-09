package snmp.exceptions;

import org.snmp4j.PDU;

/**
 * 
 * 
 * @author Ahmed Aly<ahmed.aly@student.tgm.ac.at>
 * @version 04-10-2014
 * 
 */
public class PDURequestFailedException extends Exception {
	private static final long serialVersionUID = 4L;

	public PDURequestFailedException(String message) {
		super(message);
	}

	public PDURequestFailedException(PDU responsePDU) {
		this("RequestFailed: " + responsePDU.getErrorStatusText()
				+ "\nErrIndex: " + responsePDU.getErrorIndex());
	}

}
