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

	@Test
	public void testGetHelp() {
		Commands commands = new Commands();
		assertEquals("Valid commands are: /help, /exit, /reverse", commands.getHelp());
	}

	@Test
	public void testReverse() {
		Commands commands = new Commands();
		assertEquals("olleh", commands.reverse("hello"));
		assertEquals("hello world", commands.reverse("dlrow olleh"));
	}

	@Test
	public void testParse() {
		Commands commands = new Commands();
		assertEquals("Valid commands are: /help, /exit, /reverse", commands.parse("/help"));
		assertEquals("exit", commands.parse("/exit"));
		assertEquals("olleh", commands.parse("/reverse hello"));
		assertEquals("Invalid command", commands.parse("/invalid"));
	}


	
	
}
