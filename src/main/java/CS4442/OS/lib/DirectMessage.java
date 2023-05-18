package CS4442.OS.lib;

public class DirectMessage {
    private String rawMessage;
    private String toNickname;
    private String fromNickname;

    public DirectMessage(String rawMessage, String toNickname, String fromNickname) {
        this.rawMessage = rawMessage;
        this.toNickname = toNickname;
        this.fromNickname = fromNickname;

        this.validate();
    }

    public void validate() throws IllegalArgumentException {
        if (rawMessage == null || rawMessage.isEmpty() || rawMessage.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        if (toNickname == null || toNickname.isEmpty() || toNickname.trim().isEmpty()) {
            throw new IllegalArgumentException("To nickname cannot be null or empty");
        }
        if (fromNickname == null || fromNickname.isEmpty() || fromNickname.trim().isEmpty()) {
            throw new IllegalArgumentException("From nickname cannot be null or empty");
        }
    }

}
