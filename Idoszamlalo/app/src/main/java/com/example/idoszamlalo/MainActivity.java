package com.example.idoszamlalo;

import static java.util.Calendar.getInstance;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView vizsgaSzoveg;
    private Timer timer;
    private Date vizsgaIdopont;
    private long masodpercMilli, percMilli, oraMilli, napMilli;
    private MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        player.start();
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Date most = getInstance().getTime();
                long hatralevoIdo = vizsgaIdopont.getTime() - most.getTime();
                long nap = hatralevoIdo / napMilli;
                hatralevoIdo %= napMilli;
                long ora = hatralevoIdo / oraMilli;
                hatralevoIdo %= oraMilli;
                long perc = hatralevoIdo / percMilli;
                hatralevoIdo %= percMilli;
                long masodperc = hatralevoIdo / masodpercMilli;
                String hatralevoSzoveg = String.format("%d nap \n és \n%02d:%02d:%02d", nap, ora, perc, masodperc);
                runOnUiThread(() -> {
                    vizsgaSzoveg.setText(hatralevoSzoveg);
                });
            }
        };
        timer.schedule(task, 0, 500);
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.stop();
        timer.cancel();
    }

    private void init() {
        vizsgaSzoveg = (TextView) findViewById(R.id.vizsgaSzoveg);
        //-- Komplex vizsga időpontja: 2023.05.31 9:00 -----
        Calendar vizsga = getInstance();
        vizsga.set(2023, 4, 31, 9, 0, 0);
        this.vizsgaIdopont = vizsga.getTime();
        masodpercMilli = 1000;
        percMilli = masodpercMilli * 60;
        oraMilli = percMilli * 60;
        napMilli = oraMilli * 24;
        player = MediaPlayer.create(this,R.raw.zene);
        player.setLooping(true);
    }

}