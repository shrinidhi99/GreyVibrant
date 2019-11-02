package com.example.greyvibrant.front;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.greyvibrant.R;
import com.example.greyvibrant.front.RecyclerViewElements.queueItem;
import com.example.greyvibrant.front.adapter.QueueFragmentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.AUDIO_SERVICE;

public class QueueFragment extends Fragment implements QueueFragmentAdapter.OnItemClickListener {
    SharedPreferences sharedPreferences;
    private RecyclerView mRecyclerViewQueue;
    private QueueFragmentAdapter mQueueAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public static View view;


    public static ArrayList<queueItem> queueList = new ArrayList<>();

    //Music Player variables


    public static MediaPlayer mediaPlayer;
    public static SeekBar scrubSeekBar;
    public static TextView songText, remTime, tottime;
    public static Boolean threadSwitch = false;
    AudioManager audioManager;
    public static int currentSongPos;
    String url;
    public static ImageView playb, pauseb, playnextb, playprevb;
    ListView list;
    public static boolean isplaying = true;

    String songname, genre, album, language, UIDput;
    static String URL_REGIST = "https://sabios-97.000webhostapp.com/queue_retrieval.php";

//Music Player Function

    public void playPrev(View view) {
        if (currentSongPos != 0) {
            // String songurlprev=queueList.get(currentSongPos-1).getmSongurl();
            currentSongPos -= 1;
            StartNewSong(currentSongPos);
        } else {
            currentSongPos = queueList.size() - 1;
            // String songurlprev=queueList.get(currentSongPos).getmSongurl();
            StartNewSong(currentSongPos);
        }
    }

    public void playNext(View view) {
        if (currentSongPos < queueList.size() - 1) {
            // String songurlnext=queueList.get(currentSongPos+1).getmSongurl();
            currentSongPos += 1;
            StartNewSong(currentSongPos);
        } else {
            currentSongPos = 0;
            //  String songurlnext=queueList.get(currentSongPos).getmSongurl();
            StartNewSong(currentSongPos);
        }
    }

    public void playpause(View view) {

        if (!isplaying) {
            mediaPlayer.start();
            isplaying = true;
            playb.setEnabled(false);
            playb.setVisibility(View.INVISIBLE);
            pauseb.setEnabled(true);
            pauseb.setVisibility(View.VISIBLE);

        } else {

            mediaPlayer.pause();
            isplaying = false;
            playb.setEnabled(true);
            playb.setVisibility(View.VISIBLE);
            pauseb.setEnabled(false);
            pauseb.setVisibility(View.INVISIBLE);

        }

    }


    public static void StartNewSong(final int pos) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = new MediaPlayer();

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        try {
            mediaPlayer.setDataSource(queueList.get(pos).getmSongurl());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                Log.i("SONG STATE", "SONG READY TO PLAY");
                songText.setText(queueList.get(pos).getmSongname() + "\n\n\n\n" + queueList.get(pos).getmArtistname() + "\n" + queueList.get(pos).getmAlbum());
                mediaPlayer.start();
                isplaying = true;
                pauseb.setVisibility(View.VISIBLE);
                playb.setVisibility(View.INVISIBLE);
                playnextb.setVisibility(View.VISIBLE);
                playprevb.setVisibility(View.VISIBLE);


            }
        });

        scrubSeekBar = (SeekBar) view.findViewById(R.id.scrubSeekBar);

        scrubSeekBar.setMax(mediaPlayer.getDuration());


        scrubSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                if (b) {
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
                if (threadSwitch)
                    return;
                try {

                    scrubSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                    try {
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                int s = mediaPlayer.getDuration() / 1000;
                                String minst = String.valueOf(s / 60);
                                String secst = String.valueOf(s % 60);
                                tottime.setText(minst + ":" + secst);

                                int ss = mediaPlayer.getCurrentPosition() / 1000;
                                String mins = String.valueOf(ss / 60);
                                String secs = String.valueOf(ss % 60);

                                remTime.setText(mins + ":" + secs);
                            }
                        };
                        Handler mHandler = new Handler(Looper.getMainLooper());
                        mHandler.post(runnable);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        scrubSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                        try {
                            Runnable runnable = new Runnable() {
                                @Override
                                public void run() {
                                    int s = mediaPlayer.getDuration() / 1000;
                                    String minst = String.valueOf(s / 60);
                                    String secst = String.valueOf(s % 60);
                                    tottime.setText(minst + ":" + secst);

                                    int ss = mediaPlayer.getCurrentPosition() / 1000;
                                    String mins = String.valueOf(ss / 60);
                                    String secs = String.valueOf(ss % 60);

                                    remTime.setText(mins + ":" + secs);
                                }
                            };
                            Handler mHandler = new Handler(Looper.getMainLooper());
                            mHandler.post(runnable);

                        } catch (Exception er) {
                            er.printStackTrace();
                        }

                    } catch (Exception er) {
                        er.printStackTrace();
                    }

                }


            }
        }, 0, 300);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                if (currentSongPos < queueList.size() - 1) {
                    // String songurlnext = queueList.get(currentSongPos + 1).getmSongurl();
                    currentSongPos += 1;
                    StartNewSong(currentSongPos);
                } else {
                    currentSongPos = 0;
                    // String songurlnext = queueList.get(currentSongPos).getmSongurl();
                    Log.i("SONG NAME AND URL", queueList.get(currentSongPos).getmSongname() + "<==>" + queueList.get(currentSongPos).getmSongurl());
                    StartNewSong(currentSongPos);
                }
            }

        });

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_fragment_queue, container, false);
        sharedPreferences = getContext().getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);
        UIDput = sharedPreferences.getString("UID", null);
        playb = view.findViewById(R.id.playbutton);
        pauseb = view.findViewById(R.id.pausebutton);
        playnextb = view.findViewById(R.id.playnext);
        playprevb = view.findViewById(R.id.playprev);
        songText = view.findViewById(R.id.songText);
        remTime = view.findViewById(R.id.timer);
        tottime = view.findViewById(R.id.timer2);
        queueList.clear();
        audioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);
        playb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playpause(view);
            }
        });
        pauseb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playpause(view);
            }
        });
        playnextb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playNext(view);
            }
        });

        playprevb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPrev(view);
            }
        });
        /* song_retrieval */


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE FROM PHP queueList History", response);
                        try {
                            if (response == null || response.equals(""))
                                Log.i("RESPONSE", "IS NULL");

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("songdetail");


                            if (success.equals("1")) {
                                //Log

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String artistname = object.getString("artistname");
                                    String AID = object.getString("AID");
                                    String songname = object.getString("songname");
                                    String songurl = object.getString("songurl");
                                    String album = object.getString("album");
                                    String language = object.getString("language");
                                    String genre = object.getString("genre");
                                    String SID = object.getString("SID");

                                    queueList.add(new queueItem(songname, Integer.parseInt(SID), Integer.parseInt(AID), Integer.parseInt(UIDput), album, genre, language, artistname, songurl));

                                    Log.i("artist :", artistname + " " + AID + " " + songname + " " + songurl + " " + album + " " + language + " " + genre + " " + SID);
                                }
                                Log.i("CUREENT QUEUELIST SIZE=", String.valueOf(queueList.size()));

                                mRecyclerViewQueue.setHasFixedSize(true);
                                mLayoutManager = new LinearLayoutManager(getContext());
                                mQueueAdapter = new QueueFragmentAdapter(queueList);
                                mQueueAdapter.setOnItemClickListener((QueueFragmentAdapter.OnItemClickListener) getContext());
                                mRecyclerViewQueue.setLayoutManager(mLayoutManager);
                                mRecyclerViewQueue.setAdapter(mQueueAdapter);
                                mQueueAdapter.notifyDataSetChanged();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "QF Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "QF Error 2", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("UID", UIDput);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


        mRecyclerViewQueue = view.findViewById(R.id.recycler_view_queue);
        queueList.clear();
        mRecyclerViewQueue.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mQueueAdapter = new QueueFragmentAdapter(queueList);
        mRecyclerViewQueue.setLayoutManager(mLayoutManager);
        mRecyclerViewQueue.setAdapter(mQueueAdapter);
        mQueueAdapter.notifyDataSetChanged();
        return view;


    }

    @Override
    public void onItemClick(int position) {
    }

    @Override
    public void onDescriptionClick(int position) {

    }

    @Override
    public void onDeleteItemClick(int position) {

    }
}
