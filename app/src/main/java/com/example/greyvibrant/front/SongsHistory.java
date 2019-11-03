package com.example.greyvibrant.front;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.greyvibrant.R;
import com.example.greyvibrant.front.RecyclerViewElements.SongsHistoryItem;
import com.example.greyvibrant.front.adapter.SongsHistoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SongsHistory extends AppCompatActivity implements SongsHistoryAdapter.OnItemClickListener {

    SharedPreferences sharedPreferences;
    private RecyclerView mRecyclerViewSongsHistory;
    private SongsHistoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<SongsHistoryItem> songsHistoryList = new ArrayList<>();
    EditText searchBar;
    ImageView searchString;
    static String URL_REGIST = "https://sabios-97.000webhostapp.com/songhistory.php";


    String songname, genre, album, language, UIDput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_history);
        mRecyclerViewSongsHistory = findViewById(R.id.recycler_view_songs_history);
        searchBar = findViewById(R.id.searchBox);
        searchString = findViewById(R.id.search_string);
        searchString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        sharedPreferences = getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);
        UIDput = sharedPreferences.getString("UID", null);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE FROM PHP Songs History", response);
                        try {
                            if (response == null || response.equals(""))
                                Log.i("RESPONSE", "IS NULL");

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("songhistory");


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

                                    songsHistoryList.add(new SongsHistoryItem(songname, album, genre, language, artistname));

                                    Log.i("artist :", artistname + " " + AID + " " + songname + " " + songurl + " " + album + " " + language + " " + genre + " " + SID);
                                }

                                mRecyclerViewSongsHistory.setHasFixedSize(true);
                                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                mAdapter = new SongsHistoryAdapter(SongsHistory.this, songsHistoryList);
                                mRecyclerViewSongsHistory.setLayoutManager(mLayoutManager);
                                mRecyclerViewSongsHistory.setAdapter(mAdapter);
                                mAdapter.setOnItemClickListener(SongsHistory.this);
                                mAdapter.notifyDataSetChanged();
                                // Toast.makeText(getApplicationContext(), "Log in", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SongsHistory.this, "RS Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SongsHistory.this, "Artist Error 2", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("UID", UIDput);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void search() {
        String string = searchBar.getText().toString().trim();
        mAdapter.getFilter().filter(string);
    }


    @Override
    public void onItemClick(int position) {
        Log.d("itemClick", "normal one");
        Toast.makeText(SongsHistory.this, "onItemClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlayClick(int position) {
        Log.d("itemClick", "normal one");
//        Toast.makeText(SongsHistory.this, "onItemClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteFromHistoryClick(int position) {
        Log.d("itemClick", "delete one");
//        Toast.makeText(SongsHistory.this, "onItemClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDescriptionClick(int position) {
        Log.d("itemClick", "description one");
//        Toast.makeText(SongsHistory.this, "onItemClick", Toast.LENGTH_SHORT).show();
        final SongsHistoryItem clickedItem3 = songsHistoryList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("More Info")
                .setIcon(R.drawable.ic_audiotrack_black_24dp)
                .setMessage("Song name:   " + clickedItem3.getmSongname() + "\n" +
                        "Artist name:   " + clickedItem3.getmArtistname() + "\n" +
                        "Album:           " + clickedItem3.getmAlbum() + "\n" +
                        "Genre:            " + clickedItem3.getmGenre() + "\n" +
                        "Language:     " + clickedItem3.getmLanguage())
                .setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.show();
    }
}
