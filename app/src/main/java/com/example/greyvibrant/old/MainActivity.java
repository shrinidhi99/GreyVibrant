package com.example.greyvibrant.old;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.greyvibrant.R;

public class MainActivity extends AppCompatActivity {

    Button play, stop, pause;
    MediaPlayer player;
    TextView songName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        stop = findViewById(R.id.stop);
        songName = findViewById(R.id.songName);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Play(view);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pause(view);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stop(view);
            }
        });
    }
    public void Play(View v)
    {
        if(player == null)
        {
            player = MediaPlayer.create(MainActivity.this,R.raw.song1);
            songName.setText("song1 is playing...");
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                }
            });
        }
        player.start();
    }
    public void Pause(View v)
    {
        if(player != null)
        {
            player.pause();
            songName.setText("song1 is paused...");
        }
    }
    public void Stop(View v)
    {
        songName.setText("No songs being played :(");
        stopPlayer();
    }
    private void stopPlayer()
    {
        if(player != null)
        {
            player.release();
            player = null;
            Toast.makeText(MainActivity.this,"MediaPlayer released",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}
