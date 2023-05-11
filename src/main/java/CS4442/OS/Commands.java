package CS4442.OS;

public class Commands {

	private String[] validCommands = { "/help", "/exit", "/reverse" };
	
	public boolean isValidCommand(String command) {
		for (String validCommand : validCommands) {
			if (validCommand.equals(command)) {
				return true;
			}
		}
		return false;
	}

	public String getHelp() {
		return "Valid commands are: " + String.join(", ", validCommands);
	}
	
	public String reverse(String message) {
		return new StringBuilder(message).reverse().toString();
	}

	public String parse(String command) {
		String commandInput = command.split(" ")[0].trim();
		String commandPayload = command.substring(command.indexOf(" ") + 1).trim();

		if (isValidCommand(commandInput)) {
			return execute(commandInput, commandPayload);
		} else {
			return "Invalid command";
		}
	}

	private String execute(String command, String message) {
		switch (command) {
			case "/help":
				return getHelp();
			case "/reverse":
				return reverse(message);
			case "/exit":
				return "exit";
			default:
				return "Invalid command";
		}
	}
}
