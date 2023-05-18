package CS4442.OS.lib;

import java.util.HashMap;
import java.util.Map;

public class ServerHealth {
    private Map<String, Integer> logCounts;

    public ServerHealth() {
        // Initialize logCounts to an empty HashMap
        logCounts = new HashMap<>();
    }
    public synchronized void incrementLogCount(String category) {
        // If the category is not in the map, add it with a value of 1
        logCounts.put(category, logCounts.getOrDefault(category, 0) + 1);
    }

    public synchronized int getLogCount(String category) {
        // If the category is not in the map, return 0
        return logCounts.getOrDefault(category, 0);
    }

    public synchronized int getInfoCount() {
        // Call getLogCount with the category "info"
        return getLogCount("info");
    }

    public synchronized int getWarningCount() {
        //  Call getLogCount with the category "warning"
        return getLogCount("warning");
    }

    public synchronized int getErrorCount() {
        // Call getLogCount with the category "error"
        return getLogCount("error");
    }

    public synchronized Map<String, Integer> getAllLogCounts() {
        // Return a copy of the logCounts map
        return new HashMap<>(logCounts);
    }
}
