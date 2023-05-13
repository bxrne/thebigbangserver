package CS4442.OS;

import java.io.Serializable;

public class Message implements Serializable {
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

        return from != null && body != null && !from.isEmpty() && !body.isEmpty();
    }
}
