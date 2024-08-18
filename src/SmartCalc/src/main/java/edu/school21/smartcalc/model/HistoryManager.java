package edu.school21.smartcalc.model;

import java.util.prefs.Preferences;

public class HistoryManager {
    private static final String HISTORY_KEY = "history";
    private Preferences preferences;

    public HistoryManager() {
        preferences = Preferences.userNodeForPackage(HistoryManager.class);
    }

    public void saveHistory(String history) {
        preferences.put(HISTORY_KEY, history);
    }

    public String loadHistory() {
        return preferences.get(HISTORY_KEY, "");
    }
}
