package com.example.myapplicationplayer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    String url;
    EditText et;
    int time;
    NumberPicker np;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = getSharedPreferences("myprefs", MODE_PRIVATE);
        url = sharedPref.getString("url", "");
        time = sharedPref.getInt("time", 10);
        et = findViewById(R.id.edittext);
        et.setText(url);

//        MediaController.

//        new MediaSession(MainActivity.this, "blblblb ").getController().dispatchMediaButtonEvent(keyEvent);

        np = findViewById(R.id.numberPicker);
        np.setMaxValue(23);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
//        np.setOnValueChangedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                String txt = et.getText().toString();
                time = np.getValue();
                startAlert(txt, time);
            }
        });
    }

    public void startAlert(String txt, int time) {
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        intent.putExtra("url", txt);

        int finalTime = 10;

        if (time > 1) finalTime = time;

        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(new Date());

        calendar.set(Calendar.HOUR_OF_DAY, finalTime);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 5000, pendingIntent);
        sharedPref.edit().putString("url", txt).commit();
        sharedPref.edit().putInt("time", finalTime).commit();
        Toast.makeText(this, "Alarm set in " + finalTime + " hours",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
