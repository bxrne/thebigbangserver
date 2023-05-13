package CS4442.OS;

public class Message {
    private String from;
    private String body;

    public Message(String from, String body) {

        this.from = from;
        this.body = body;

    }

    public String getFrom() {
        return from;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "[" + from + "]: " + body;
    }

    public boolean validate() {
        // if null
        if (from == null || body == null) {
            return false;
        }

        // if empty
        if (from.isEmpty() || body.isEmpty()) {
            return false;
        }

        // if whitespace
        if (from.trim().isEmpty() || body.trim().isEmpty()) {
            return false;
        }

        return true;
    }
}
