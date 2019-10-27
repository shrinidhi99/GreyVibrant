package com.example.greyvibrant.front;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greyvibrant.R;
import com.example.greyvibrant.front.RecyclerViewElements.playlistItem;
import com.example.greyvibrant.front.adapter.PlaylistFragmentAdapter;

import java.util.ArrayList;

public class PlaylistFragment extends Fragment {

    SharedPreferences sharedPreferences;
    private RecyclerView mRecyclerViewPlaylist;
    private PlaylistFragmentAdapter mPlaylistAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<playlistItem> playList = new ArrayList<>();
    String songname, genre, album, language, UIDput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment_playlist, container, false);
        sharedPreferences = getContext().getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);
        UIDput = sharedPreferences.getString("UID", null);
        /* song_retrieval */

        mRecyclerViewPlaylist = view.findViewById(R.id.recycler_view_playlist);
        mRecyclerViewPlaylist.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mPlaylistAdapter = new PlaylistFragmentAdapter(playList);
        mRecyclerViewPlaylist.setLayoutManager(mLayoutManager);
        mRecyclerViewPlaylist.setAdapter(mPlaylistAdapter);
        mPlaylistAdapter.notifyDataSetChanged();
        return view;
    }
}
