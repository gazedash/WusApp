package com.example.myapplicationplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.WindowManager;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //start activity
        String url = intent.getExtras().getString("url", "");

        AudioManager am =
                (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//
        am.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0);

//        Intent i = new Intent();
//        i.setClassName("com.example.myapplicationplayer", "com.example.myapplicationplayer.MainActivity");
        //"https://open.spotify.com/track/6oBjnOBqngXWsRuYY5eTnz?si=BaICUmnzTj2Yarx8NNvpEw"));
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        browserIntent.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED +
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD +
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON +
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        context.startActivity(browserIntent);


        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        long eventtime = SystemClock.uptimeMillis();
        Intent keyIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent keyEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY, 0);
        keyIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
        mAudioManager.dispatchMediaKeyEvent(keyEvent);

        keyEvent = KeyEvent.changeAction(keyEvent, KeyEvent.ACTION_UP);
        keyIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);

        mAudioManager.dispatchMediaKeyEvent(keyEvent);
    }
}

