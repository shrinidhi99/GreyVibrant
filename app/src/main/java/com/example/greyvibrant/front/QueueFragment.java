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
import com.example.greyvibrant.front.RecyclerViewElements.queueItem;
import com.example.greyvibrant.front.adapter.QueueFragmentAdapter;

import java.util.ArrayList;

public class QueueFragment extends Fragment {

    SharedPreferences sharedPreferences;
    private RecyclerView mRecyclerViewQueue;
    private QueueFragmentAdapter mQueueAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<queueItem> queueList = new ArrayList<>();
    String songname, genre, album, language, UIDput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment_queue, container, false);
        sharedPreferences = getContext().getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);
        UIDput = sharedPreferences.getString("UID", null);
        /* song_retrieval */
        mRecyclerViewQueue.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mQueueAdapter = new QueueFragmentAdapter(queueList);
        mRecyclerViewQueue.setLayoutManager(mLayoutManager);
        mRecyclerViewQueue.setAdapter(mQueueAdapter);
        mQueueAdapter.notifyDataSetChanged();
        mRecyclerViewQueue = view.findViewById(R.id.recycler_view_queue);
        return view;
    }
}
