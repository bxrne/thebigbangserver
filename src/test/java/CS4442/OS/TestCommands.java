package CS4442.OS;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestCommands {

	@Test
	public void testIsValidCommand() {
		Commands commands = new Commands();
		assertTrue(commands.isValidCommand("/help"));
		assertTrue(commands.isValidCommand("/exit"));
		assertTrue(commands.isValidCommand("/reverse"));
		assertFalse(commands.isValidCommand("/invalid"));
	}
	
}
