package com.example.greyvibrant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    Button playBtnClick;
    SeekBar positionBar;
    SeekBar volumeBar;
    TextView remainingTimeLabel, elapsedTimeLabel, userLoggedIn;
    MediaPlayer mp;
    int totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        playBtnClick = findViewById(R.id.playBtnClick);
        userLoggedIn = findViewById(R.id.userLoggedIn);
        userLoggedIn.setText(SharedPrefManager.getInstance(this).getUsername());
        elapsedTimeLabel = findViewById(R.id.elapsed);
        remainingTimeLabel = findViewById(R.id.remainingTime);
        playBtnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Play(view);
            }
        });
        // Media player
        mp = MediaPlayer.create(this, R.raw.song1);
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.5f, 0.5f);
        totalTime = mp.getDuration();

        // position bar
        positionBar = findViewById(R.id.positionBar);
        positionBar.setMax(totalTime);
        positionBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mp.seekTo(progress);
                    positionBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // volume bar
        volumeBar = findViewById(R.id.volumeBar);
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volumeNum = progress / 100f;
                mp.setVolume(volumeNum, volumeNum);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // thread (update position bar and time label)
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null) {
                    Message msg = new Message();
                    msg.what = mp.getCurrentPosition();
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            int currentPosition = msg.what;
            // update position bar
            positionBar.setProgress(currentPosition);

            // update labels
            String elapsedTime = createTimeLabel(currentPosition);
            elapsedTimeLabel.setText(elapsedTime);
            String remainingTime = createTimeLabel(totalTime - currentPosition);
            remainingTimeLabel.setText("- " + remainingTime);
        }
    };

    public String createTimeLabel(int time) {
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;
        timeLabel = min + ":";
        if (sec < 10) {
            timeLabel += "0";
        }
        timeLabel += sec;
        return timeLabel;
    }

    public void Play(View view) {
        if (!mp.isPlaying()) {
            // stopping
            mp.start();
            playBtnClick.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_pause_black_24dp));
        } else {
            // playing
            mp.pause();
            playBtnClick.setBackgroundDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp));
        }
    }
}
