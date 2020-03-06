package com.weihuan.buzzbuzz.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.weihuan.buzzbuzz.db.Recipe;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private Recipe recipe;
    @Override
    public void onReceive(Context context, Intent intent) {
//        recipe = intent.getParcelableExtra("MyRecipe");

    }
}
