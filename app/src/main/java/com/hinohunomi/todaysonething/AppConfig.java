package com.hinohunomi.todaysonething;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppConfig {
    private SharedPreferences pref;

    public AppConfig(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setTodayMessage(String message) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString("MESSAGE_DATE", MyDateUtil.getTodayString("yyyy-MM-dd"));
        editor.putString("MESSAGE_TEXT", message);
        editor.commit();
    }

    public String getTodayMessage() {
        String result = null;
        if (hasTodayMessage()) {
            String message = pref.getString("MESSAGE_TEXT", null);
            if (message != null) {
                result = message;
            }
        }
        return result;
    }

    private boolean hasTodayMessage() {
        boolean result = false;
        String messageDate = pref.getString("MESSAGE_DATE", null);
        if (messageDate != null && MyDateUtil.getTodayString("yyyy-MM-dd").compareTo(messageDate) == 0) {
            result = true;
        }
        return result;
    }

    private SharedPreferences.Editor getEditor() {
        return pref.edit();
    }
}
