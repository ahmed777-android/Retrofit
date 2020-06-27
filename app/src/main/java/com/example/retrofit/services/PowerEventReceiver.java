package com.example.retrofit.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class PowerEventReceiver extends BroadcastReceiver {
    // implementation of  BroadcastReceiver to receiving the event of device_plugged_in or not
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        boolean setting = false;
        SharedPreferences preferences = context.getSharedPreferences
                ("default", Context.MODE_PRIVATE);
        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            setting = true;
            Toast.makeText(context, "device_plugged_in", Toast.LENGTH_LONG).show();
        } else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
            Toast.makeText(context, "Unplugged", Toast.LENGTH_LONG).show();
            setting = false;
        }
        preferences.edit().putBoolean("device_plugged_in", setting).apply();
    }
}
