package com.example.stopwatchjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timer ;
    Button start, pause, reset;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);

        if (savedInstanceState != null) {
            MillisecondTime =  savedInstanceState.getLong("MillisecondTime", 0L);
            StartTime =  savedInstanceState.getLong("StartTime", 0L);
            TimeBuff =  savedInstanceState.getLong("TimeBuff", 0L);
            UpdateTime =  savedInstanceState.getLong("UpdateTime", 0L);
            Seconds =  savedInstanceState.getInt("Seconds", 0);
            Minutes = savedInstanceState.getInt("Minutes", 0);
            MilliSeconds =  savedInstanceState.getInt("MilliSeconds", 0);
        }

        timer = (TextView)findViewById(R.id.time_view);
        start = (Button)findViewById(R.id.start_button);
//        pause = (Button)findViewById(R.id.stop_button);
        reset = (Button)findViewById(R.id.reset_button);

        handler = new Handler() ;

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reset.isEnabled())
                {
                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);

                    reset.setEnabled(false);
                    start.setText(R.string.stop);
                }
                else
                {
                    TimeBuff += MillisecondTime;
                    handler.removeCallbacks(runnable);

                    reset.setEnabled(true);
                    start.setText(R.string.start);
                }

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;

                timer.setText("00:00.00");

            }
        });

    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("MillisecondTime", MillisecondTime);
        outState.putLong("StartTime", StartTime);
        outState.putLong("TimeBuff", TimeBuff);
        outState.putLong("UpdateTime", UpdateTime);
        outState.putInt("Seconds", Seconds);
        outState.putInt("Minutes", Minutes);
        outState.putInt("MilliSeconds", MilliSeconds);
        outState.putString("time", "" + String.format("%02d", Minutes) + ":"
                + String.format("%02d", Seconds) + "."
                + String.format("%02d", MilliSeconds));
        outState.putBoolean("reset", reset.isEnabled());
        outState.putString("start/stop", String.valueOf(start.getText()));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        MillisecondTime =  savedInstanceState.getLong("MillisecondTime", 0L);
        StartTime =  savedInstanceState.getLong("StartTime", 0L);
        TimeBuff =  savedInstanceState.getLong("TimeBuff", 0L);
        UpdateTime =  savedInstanceState.getLong("UpdateTime", 0L);
        Seconds =  savedInstanceState.getInt("Seconds", 0);
        Minutes = savedInstanceState.getInt("Minutes", 0);
        MilliSeconds =  savedInstanceState.getInt("MilliSeconds", 0);
        timer.setText(savedInstanceState.getString("time"));
        reset.setEnabled(savedInstanceState.getBoolean("reset"));
        start.setText(savedInstanceState.getString("start/stop"));
        if (!reset.isEnabled())
        {
            handler.postDelayed(runnable, 0);
        }

    }

    public Runnable runnable = new Runnable() {

        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) ((UpdateTime / 10)%100);

            timer.setText("" + String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds) + "."
                    + String.format("%02d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };
}