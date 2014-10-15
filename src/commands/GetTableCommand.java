package commands;

import org.snmp4j.smi.Variable;

import java.util.HashMap;
import java.util.Vector;

/**
 * This class is responsible for the get table command and based on the command
 * pattern. This class also implements the interface Command, which contain the
 * execute function. If the get table command had been called this class will refer
 * with the function execute to the ActionCommand Class, which will call the actual
 * function to run the end command.
 *
 * @author AHMED ALY
 *
 */
public class GetTableCommand implements Command<HashMap<Integer,Vector<Variable>>> {
    private ActionCommand actionCommand;

    /**
     * Constructor for the get table command. A parameter have to bee given over. This
     * parameter have to be a ActionCommand object.
     *
     * @param actionCommand
     *            object which contain the actual get table command function.
     */
    public GetTableCommand(ActionCommand actionCommand) {
        this.actionCommand = actionCommand;
    }

    /**
     * This function call the get table command function from the object ActionCommand,
     * which was given over the Constructor.
     */
    public HashMap<Integer,Vector<Variable>> execute() {
        return actionCommand.getTable();
    }
}