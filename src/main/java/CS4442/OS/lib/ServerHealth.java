package CS4442.OS.lib;

import java.util.HashMap;
import java.util.Map;

public class ServerHealth {
    private Map<String, Integer> logCounts;

    public ServerHealth() {
        logCounts = new HashMap<>();
    }

    public synchronized void incrementLogCount(String category) {
        logCounts.put(category, logCounts.getOrDefault(category, 0) + 1);
    }

    public synchronized int getLogCount(String category) {
        return logCounts.getOrDefault(category, 0);
    }

    public synchronized int getInfoCount() {
        return getLogCount("info");
    }

    public synchronized int getWarningCount() {
        return getLogCount("warning");
    }

    public synchronized int getErrorCount() {
        return getLogCount("error");
    }

    public synchronized Map<String, Integer> getAllLogCounts() {
        return new HashMap<>(logCounts);
    }
}
