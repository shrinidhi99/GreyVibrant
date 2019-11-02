package com.example.greyvibrant.front;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.greyvibrant.R;
import com.example.greyvibrant.front.RecyclerViewElements.playlistItem;
import com.example.greyvibrant.front.adapter.PlaylistFragmentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlaylistFragment extends Fragment implements PlaylistFragmentAdapter.OnItemClickListener {

    SharedPreferences sharedPreferences;
    private RecyclerView mRecyclerViewPlaylist;
    private PlaylistFragmentAdapter mPlaylistAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<playlistItem> playList = new ArrayList<>();
    String songname, genre, album, language, UIDput;

    static String URL_REGIST = "https://sabios-97.000webhostapp.com/playlist_retrieval.php";

    EditText searchBox;
    ImageView searchString;

    Button clearAll, playAll;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment_playlist, container, false);
        sharedPreferences = getContext().getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);
        UIDput = sharedPreferences.getString("UID", null);
        /* song_retrieval */
        mRecyclerViewPlaylist = view.findViewById(R.id.recycler_view_playlist);
        playList.clear();
        searchBox = view.findViewById(R.id.searchBox);
        clearAll = view.findViewById(R.id.clearAll);
        playAll = view.findViewById(R.id.playAll);
        searchString = view.findViewById(R.id.search_string);
        searchString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
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

                                    playList.add(new playlistItem(songname, Integer.parseInt(SID), Integer.parseInt(AID), Integer.parseInt(UIDput), album, genre, language, artistname, songurl));

                                    Log.i("artist :", artistname + " " + AID + " " + songname + " " + songurl + " " + album + " " + language + " " + genre + " " + SID);
                                }

                                mRecyclerViewPlaylist.setHasFixedSize(true);
                                mLayoutManager = new LinearLayoutManager(getContext());
                                mPlaylistAdapter = new PlaylistFragmentAdapter(playList);
                                mRecyclerViewPlaylist.setLayoutManager(mLayoutManager);
                                mRecyclerViewPlaylist.setAdapter(mPlaylistAdapter);
                                mPlaylistAdapter.setOnItemClickListener((PlaylistFragmentAdapter.OnItemClickListener) getContext());
                                mPlaylistAdapter.notifyDataSetChanged();


                                // Toast.makeText(getApplicationContext(), "Log in", Toast.LENGTH_SHORT).show();


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "PL Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "PL Error 2", Toast.LENGTH_SHORT).show();

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


        return view;
    }

    private void search() {
        String string = searchBox.getText().toString().trim();
        mPlaylistAdapter.getFilter().filter(string);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onPlayClick(int position) {

    }

    @Override
    public void onDeleteFromPlaylistClick(int position) {

    }
}