package CS4442.OS;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;

import org.junit.Test;

public class TestCommand {
    private PrintWriter out = new PrintWriter(System.out);

    @Test
    public void helpCommandSignal() {
        Command command = new Command("help");
        assertEquals(command.execute(out), Command.ServerSignals.HELP);
    }

    @Test
    public void listCommandSignal() {
        Command command = new Command("list");
        assertEquals(command.execute(out), Command.ServerSignals.LIST);
    }

    @Test
    public void quitCommandSignal() {
        Command command = new Command("quit");
        assertEquals(command.execute(out), Command.ServerSignals.QUIT);

    }

    @Test
    public void clearCommandSignal() {
        Command command = new Command("clear");
        assertEquals(command.execute(out), Command.ServerSignals.CLEAR);
    }

    @Test
    public void invalidCommand() {
        try {
            new Command("");
            assert (false);
        } catch (IllegalArgumentException e) {
            assert (true);
        }
    }

    @Test
    public void nullCommand() {
        try {
            new Command(null);
            assert (false);
        } catch (IllegalArgumentException e) {
            assert (true);
        }
    }

}
