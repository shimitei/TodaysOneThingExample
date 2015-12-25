package com.hinohunomi.todaysonething;

import android.app.Application;

public class Globals extends Application {
    public static ITakeMessage thingManager = new TodayMessage(new RandomMessage());
}
