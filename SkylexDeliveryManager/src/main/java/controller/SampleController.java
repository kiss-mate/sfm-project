package controller;

import com.google.inject.Inject;
import logic.ILogic;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles actions related to Sample objects coming from the GUI and forwards actions to Logic
 */
public class SampleController {
    private final Logger _log;
    private final ILogic _logic;
    private final Scanner _scanner;

    /**
     * Creates new SampleController object
     * @param log Logger log, cannot be null
     * @param logic ILogic logic, cannot be null
     * @param scanner Scanner scanner, cannot be null
     */
    @Inject
    public SampleController(Logger log, ILogic logic, Scanner scanner) {
        if ((_log = log) == null)  throw new NullPointerException("log");
        if ((_logic = logic) == null)  throw new NullPointerException("logic");
        if ((_scanner = scanner) == null)  throw new NullPointerException("scanner");
    }

    public void handleFakeAction() {
        _log.log(Level.INFO, "Started event handling");
        _log.log(Level.INFO,"Input value1 [string]: ");
        var input1 = _scanner.nextLine();
        _log.log(Level.INFO,"Input value2 [int]: ");
        var input2 = _scanner.nextInt();
        _scanner.nextLine();//paranoid scanning
        _logic.addOneSample(input1, input2);
        _log.log(Level.INFO, "Action handled");
    }
}
