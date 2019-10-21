package com.example.greyvibrant.front;

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
import com.example.greyvibrant.front.RecyclerViewElements.myAlbumItem;
import com.example.greyvibrant.front.adapter.albumFragmentAdapter;

import java.util.ArrayList;

public class AlbumFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private albumFragmentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<myAlbumItem> albumList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artist_fragment_album, container, false);
//        setHasOptionsMenu(true);
        albumList.add(new myAlbumItem("No good in goodbye", "The Script", "Pop", "English"));
        albumList.add(new myAlbumItem("Roar", "Katy Perry", "Rock", "English"));
        albumList.add(new myAlbumItem("Champions", "Personal", "Rock", "English"));
        albumList.add(new myAlbumItem("CWC 15 theme song", "CWC", "Theme", "English"));
        albumList.add(new myAlbumItem("Super heroes", "The Script", "Pop", "English"));
        albumList.add(new myAlbumItem("The Scientist", "ColdPlay", "Pop", "English"));
        albumList.add(new myAlbumItem("Hall of fame", "The Script", "Rock", "English"));
        albumList.add(new myAlbumItem("In the end", "Linkin Park", "Rock", "English"));
        albumList.add(new myAlbumItem("Imagine", "John Lennon", "Pop", "English"));
        albumList.add(new myAlbumItem("Hey Jude", "The Beatles", "Free style", "English"));
        albumList.add(new myAlbumItem("Sky full of stars", "ColdPlay", "Pop", "English"));
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new albumFragmentAdapter(albumList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getActivity().getMenuInflater();
//        inflater.inflate(R.menu.example_menu, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                mAdapter.getFilter().filter(s);
//                return false;
//            }
//        });
//        return true;
//    }
}
