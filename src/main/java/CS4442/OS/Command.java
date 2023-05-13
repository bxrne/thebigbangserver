package CS4442.OS;

import java.io.PrintWriter;

public class Command {
    private String command;

    private String[] validCommands = { "quit", "help", "list" };

    public enum ServerSignals {
        QUIT, LIST, HELP
    };

    public Command(String command) {
        this.command = command;
        this.validate();
    }

    private boolean validate() throws IllegalArgumentException {
        if (command == null || command.isEmpty() || command.trim().isEmpty()) {
            throw new IllegalArgumentException("Command cannot be null or empty");
        }

        for (String validCommand : validCommands) {
            if (command.equals(validCommand)) {
                return true;
            }
        }

        throw new IllegalArgumentException("Command is not valid");
    }

    public ServerSignals execute(PrintWriter out) {
        switch (command) {
            case "quit":
                return ServerSignals.QUIT;
            case "help":
                String result = "Available commands: ";
                for (String validCommand : validCommands) {
                    result += "/" + validCommand + " ";
                }
                out.println(result);
                return ServerSignals.HELP;
            case "list":
                return ServerSignals.LIST;
            default:
                out.println("Command not found");
                return null;
        }
    }

    public String getCommand() {
        return command;
    }
}
