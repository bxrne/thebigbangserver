package CS4442.OS;

import java.io.PrintWriter;

import org.junit.Test;

public class TestCommand {
    private PrintWriter out = new PrintWriter(System.out);

    @Test
    public void helpCommand() {
        Command command = new Command("help");
        assert (command.execute(out) == Command.ServerSignals.HELP);
    }

    @Test
    public void listCommand() {
        Command command = new Command("list");
        assert (command.execute(out) == Command.ServerSignals.LIST);
    }

    @Test
    public void quitCommand() {
        Command command = new Command("quit");
        assert (command.execute(out) == Command.ServerSignals.QUIT);
    }
}
