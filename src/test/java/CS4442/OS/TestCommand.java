package CS4442.OS;

import java.io.PrintWriter;

import org.junit.Test;

public class TestCommand {
    private PrintWriter out = new PrintWriter(System.out);

    @Test
    public void helpCommandSignal() {
        Command command = new Command("help");
        assert (command.execute(out) == Command.ServerSignals.HELP);
    }

    @Test
    public void listCommandSignal() {
        Command command = new Command("list");
        assert (command.execute(out) == Command.ServerSignals.LIST);
    }

    @Test
    public void quitCommandSignal() {
        Command command = new Command("quit");
        assert (command.execute(out) == Command.ServerSignals.QUIT);
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
