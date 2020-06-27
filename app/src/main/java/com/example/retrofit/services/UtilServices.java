package com.example.retrofit.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class UtilServices {
    public static void registerPowerEventsReceiver(Context mContext, BroadcastReceiver receiver) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        mContext.getApplicationContext().registerReceiver(receiver, filter);
    }

    public static void unregisterPowerEventsReceiver(Context mContext, BroadcastReceiver receiver) {
        mContext.getApplicationContext().unregisterReceiver(receiver);
    }

}
