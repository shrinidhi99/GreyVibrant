package com.example.greyvibrant.front;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greyvibrant.R;
import com.example.greyvibrant.front.RecyclerViewElements.SongsHistoryItem;
import com.example.greyvibrant.front.adapter.SongsHistoryAdapter;

import java.util.ArrayList;

public class SongsHistory extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private RecyclerView mRecyclerView;
    private SongsHistoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<SongsHistoryItem> songsHistoryList = new ArrayList<>();
    EditText searchBar;
    ImageView searchString;

    String songname, genre, album, language, UIDput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_history);
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

        songsHistoryList.add(new SongsHistoryItem("s1", "a1", "A1", "g1", "l1"));
        songsHistoryList.add(new SongsHistoryItem("s2", "a2", "A2", "g2", "l2"));
        songsHistoryList.add(new SongsHistoryItem("s3", "a3", "A3", "g3", "l3"));

        mRecyclerView = findViewById(R.id.recycler_view_songs_history);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SongsHistoryAdapter(songsHistoryList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void search() {
        String string = searchBar.getText().toString().trim();
        mAdapter.getFilter().filter(string);
    }
}
