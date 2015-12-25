package com.hinohunomi.todaysonething;

import android.content.Context;

public class TodayMessage implements ITakeMessage {
    private ITakeMessage takeMassage;

    public TodayMessage(ITakeMessage tm) {
        takeMassage = tm;
    }

    public String GetMessage(Context context) {
        AppConfig conf = new AppConfig(context);
        String result = conf.getTodayMessage();
        if (result == null) {
            result = takeMassage.GetMessage(context);
            conf.setTodayMessage(result);
        }
        return result;
    }
}
