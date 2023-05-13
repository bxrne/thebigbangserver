package CS4442.OS;

import org.junit.Test;

public class TestMessage {
    @Test
    public void testMessage() {
        Message message = new Message("test", "test");
        assert message.validate();
    }

    @Test
    public void testMessageNullFrom() {
        Message message = new Message(null, "test");
        assert !message.validate();
    }

    @Test
    public void testMessageNullBody() {
        Message message = new Message("test", null);
        assert !message.validate();
    }

    @Test
    public void testMessageNullFromAndBody() {
        Message message = new Message(null, null);
        assert !message.validate();
    }

    @Test
    public void testMessageEmptyFrom() {
        Message message = new Message("", "test");
        assert !message.validate();
    }

    @Test
    public void testMessageEmptyBody() {
        Message message = new Message("test", "");
        assert !message.validate();
    }

    @Test
    public void testMessageEmptyFromAndBody() {
        Message message = new Message("", "");
        assert !message.validate();
    }

    @Test
    public void testMessageWhitespaceFrom() {
        Message message = new Message(" ", "test");
        assert !message.validate();
    }

    @Test
    public void testMessageWhitespaceBody() {
        Message message = new Message("test", " ");
        assert !message.validate();
    }

    @Test
    public void testMessageWhitespaceFromAndBody() {
        Message message = new Message(" ", " ");
        assert !message.validate();
    }

}
