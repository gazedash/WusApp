package com.example.myapplicationplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //start activity
        String url = intent.getExtras().getString("url");
//        Intent i = new Intent();
//        i.setClassName("com.example.myapplicationplayer", "com.example.myapplicationplayer.MainActivity");
        //"https://open.spotify.com/track/6oBjnOBqngXWsRuYY5eTnz?si=BaICUmnzTj2Yarx8NNvpEw"));
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);
    }
}

