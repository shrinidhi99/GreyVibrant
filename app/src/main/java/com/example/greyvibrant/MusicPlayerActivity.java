package com.example.greyvibrant;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

public class MusicPlayerActivity extends AppCompatActivity {
    private   MediaPlayer mediaPlayer;
    SeekBar scrubSeekBar;
    AudioManager audioManager;
    int currentSongPos;
    String url;
    ImageView playb,pauseb,playnextb,playprevb;
    ListView list;
    boolean isplaying=true;
    ArrayList<String> songs=new ArrayList<String>();
    ArrayAdapter adapter;
    public void playpause(View view) {

        if(!isplaying)
        {
            mediaPlayer.start();
            isplaying=true;
            playb.setEnabled(false);
            playb.setVisibility(View.INVISIBLE);
            pauseb.setEnabled(true);
            pauseb.setVisibility(View.VISIBLE);

        }
        else {

            mediaPlayer.pause();
            isplaying=false;
            playb.setEnabled(true);
            playb.setVisibility(View.VISIBLE);
            pauseb.setEnabled(false);
            pauseb.setVisibility(View.INVISIBLE);

        }

    }



//    public void stop(View view) throws IOException {
//        if(mediaPlayer.getCurrentPosition()<mediaPlayer.getDuration())
//        {
//           scrubSeekBar.setEnabled(false);
//            mediaPlayer.stop();
//
//           mediaPlayer.prepare();
//
//        }
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playb=findViewById(R.id.playbutton);
        pauseb=findViewById(R.id.pausebutton);
        playnextb=findViewById(R.id.playnext);
        playprevb=findViewById(R.id.playprev);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        songs.add("https://files1.mp3slash.xyz/stream/9acbc20ccd8344768a4b95521606e063");
        songs.add("https://files1.mp3slash.xyz/stream/ea4a02b5bafcc6bc1400cfc06c488f31");
        songs.add("https://files1.mp3slash.xyz/stream/2db48affa39918895b880ee3204e2622");
        list=findViewById(R.id.songlist);
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,songs);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentSongPos=i;
                String songurl=songs.get(i);
                StartNewSong(songurl);
            }
        });


    }
    public void playPrev(View view)
    {
        if(currentSongPos!=0)
        {
            String songurlprev=songs.get(currentSongPos-1);
            currentSongPos-=1;
            StartNewSong(songurlprev);
        }
        else
        {
            currentSongPos=songs.size()-1;
            String songurlprev=songs.get(currentSongPos);
            StartNewSong(songurlprev);
        }
    }

    public void playNext(View view)
    {
        if(currentSongPos<songs.size()-1)
        {
            String songurlnext=songs.get(currentSongPos+1);
            currentSongPos+=1;
            StartNewSong(songurlnext);
        }
        else
        {
            currentSongPos=0;
            String songurlnext=songs.get(currentSongPos);
            StartNewSong(songurlnext);
        }
    }

    public void StartNewSong(String url)
    {
        if(mediaPlayer!=null){
            mediaPlayer=null;
        }
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Toast.makeText(MusicPlayerActivity.this, "Playing Song", Toast.LENGTH_SHORT).show();

                mediaPlayer.start();
                isplaying=true;
                pauseb.setVisibility(View.VISIBLE);
                playb.setVisibility(View.INVISIBLE);
                playnextb.setVisibility(View.VISIBLE);
                playprevb.setVisibility(View.VISIBLE);


            }
        });

        scrubSeekBar = (SeekBar) findViewById(R.id.scrubSeekBar);

        scrubSeekBar.setMax(mediaPlayer.getDuration());

        scrubSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if(b)
                {
                    Log.i("Scrub seekbar moved", Integer.toString(i));

                    mediaPlayer.seekTo(i);

                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                scrubSeekBar.setProgress(mediaPlayer.getCurrentPosition());


            }
        }, 0, 300);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                if(currentSongPos<songs.size()-1)
                {
                    String songurlnext=songs.get(currentSongPos+1);
                    currentSongPos+=1;
                    StartNewSong(songurlnext);
                }
                else
                {
                    currentSongPos=0;
                    String songurlnext=songs.get(currentSongPos);
                    StartNewSong(songurlnext);
                }
            }

        });

    }
}
