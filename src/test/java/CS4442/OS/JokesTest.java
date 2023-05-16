package CS4442.OS;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JokesTest {

    @Test
    public void testGetJoke_Success() {
        Jokes jokes = new Jokes();
        String joke = jokes.getJoke();
        assert (joke != null && !joke.isEmpty());
    }

    @Test
    public void testGetJoke_Failure() {
        Jokes jokes = new Jokes();
        jokes.setUrlString(null);
        String joke = jokes.getJoke();
        assertEquals("Failed to get joke", joke);
    }
}
