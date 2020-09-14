package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    Button button;
    Boolean isRunning = false;
    int ticks;
    SeekBar timerSeekbar;
    TextView timerText;
    BigDecimal var60 = new BigDecimal(0);
    BigDecimal secondsTemp = new BigDecimal(0);
    BigDecimal minutes = new BigDecimal(0);
    BigDecimal seconds = new BigDecimal(0);
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.horn);
        timerSeekbar = findViewById(R.id.timerSeekBar);
        timerText = findViewById(R.id.textView);
        timerSeekbar.setMax(360);
        timerSeekbar.setProgress(30);
        timerText.setText("0:30");
        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ticks = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                ticks = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ticks = seekBar.getProgress();
            }
        });

        button = findViewById(R.id.goButton);
        button.setText("go");

    }

    public void go(View view) {

        if (isRunning == false) {
            button.setText("stop");
            isRunning = true;
            timerSeekbar.setEnabled(false);

            new CountDownTimer(ticks * 1000, 1000) {

                @Override
                public void onTick(long l) {
                    if (isRunning == false) {
                        this.cancel();
                    }
                    else{
                        var60 = new BigDecimal(60);
                        secondsTemp = new BigDecimal(l / 1000);
                        minutes = secondsTemp.divide(var60, BigDecimal.ROUND_FLOOR);
                        seconds = secondsTemp.remainder(var60);
                        timerText.setText(minutes + ":" + String.format("%02d", seconds.intValue()));
                    }
                }

                @Override
                public void onFinish() {
                    mediaPlayer.start();
                    button.setText("go");
                    isRunning = false;
                    timerSeekbar.setEnabled(true);
                    timerSeekbar.setProgress(30);
                    timerText.setText("0:30");
                }
            }.start();
        } else {
            button.setText("go");
            isRunning = false;
            timerSeekbar.setEnabled(true);
            timerText.setText("0:30");
            timerSeekbar.setProgress(30);
        }
    }
}