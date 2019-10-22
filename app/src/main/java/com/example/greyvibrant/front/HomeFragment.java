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
import com.example.greyvibrant.front.RecyclerViewElements.followedArtistsItem;
import com.example.greyvibrant.front.RecyclerViewElements.recommendedSongsItem;
import com.example.greyvibrant.front.RecyclerViewElements.remainingSongsItem;
import com.example.greyvibrant.front.RecyclerViewElements.unfollowedArtistsItem;
import com.example.greyvibrant.front.adapter.FollowedArtistAdapter;
import com.example.greyvibrant.front.adapter.RecommendedSongsAdapter;
import com.example.greyvibrant.front.adapter.RemainingSongsAdapter;
import com.example.greyvibrant.front.adapter.UnfollowedArtistAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView mRecyclerViewFollowed, mRecyclerViewUnfollowed, mRecyclerViewRecommended, mRecyclerViewRemaining;
    private RecyclerView.LayoutManager mLayoutManager1, mLayoutManager2, mLayoutManager3, mLayoutManager4;

    ArrayList<unfollowedArtistsItem> unfollowedArtistsItemsList = new ArrayList<>();
    ArrayList<followedArtistsItem> followedArtistsItemsList = new ArrayList<>();
    ArrayList<recommendedSongsItem> recommendedSongsItemsList = new ArrayList<>();
    ArrayList<remainingSongsItem> remainingSongsItemsList = new ArrayList<>();

    private UnfollowedArtistAdapter unfollowedArtistAdapter;
    private FollowedArtistAdapter followedArtistAdapter;
    private RecommendedSongsAdapter recommendedSongsAdapter;
    private RemainingSongsAdapter remainingSongsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment_home, container, false);
        mRecyclerViewFollowed = view.findViewById(R.id.recycler_view_followed);
        mRecyclerViewUnfollowed = view.findViewById(R.id.recycler_view_unfollowed);
        mRecyclerViewRecommended = view.findViewById(R.id.recycler_view_recommended);
        mRecyclerViewRemaining = view.findViewById(R.id.recycler_view_remaining);

        followedArtistsItemsList.add(new followedArtistsItem(R.drawable.ic_done_black_24dp, "Arijit", 1));
        followedArtistsItemsList.add(new followedArtistsItem(R.drawable.ic_done_black_24dp, "Atif", 2));

        unfollowedArtistsItemsList.add(new unfollowedArtistsItem(R.drawable.ic_add_circle_black_24dp, "Shreya", 3));
        unfollowedArtistsItemsList.add(new unfollowedArtistsItem(R.drawable.ic_add_circle_black_24dp, "Sunidhi", 4));

        recommendedSongsItemsList.add(new recommendedSongsItem("A", "B", "C", "D", "E"));
        recommendedSongsItemsList.add(new recommendedSongsItem("F", "G", "H", "I", "J"));

        remainingSongsItemsList.add(new remainingSongsItem("AA", "BB", "CC", "DD", "EE"));
        remainingSongsItemsList.add(new remainingSongsItem("FF", "GG", "HH", "II", "JJ"));

        mRecyclerViewFollowed.setHasFixedSize(true);
        mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        followedArtistAdapter = new FollowedArtistAdapter(followedArtistsItemsList);
        mRecyclerViewFollowed.setLayoutManager(mLayoutManager1);
        mRecyclerViewFollowed.setAdapter(followedArtistAdapter);
        followedArtistAdapter.notifyDataSetChanged();

        mRecyclerViewUnfollowed.setHasFixedSize(true);
        mLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        unfollowedArtistAdapter = new UnfollowedArtistAdapter(unfollowedArtistsItemsList);
        mRecyclerViewUnfollowed.setLayoutManager(mLayoutManager2);
        mRecyclerViewUnfollowed.setAdapter(unfollowedArtistAdapter);
        unfollowedArtistAdapter.notifyDataSetChanged();

        mRecyclerViewRecommended.setHasFixedSize(true);
        mLayoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recommendedSongsAdapter = new RecommendedSongsAdapter(recommendedSongsItemsList);
        mRecyclerViewRecommended.setLayoutManager(mLayoutManager3);
        mRecyclerViewRecommended.setAdapter(recommendedSongsAdapter);
        recommendedSongsAdapter.notifyDataSetChanged();

        mRecyclerViewRemaining.setHasFixedSize(true);
        mLayoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        remainingSongsAdapter = new RemainingSongsAdapter(remainingSongsItemsList);
        mRecyclerViewRemaining.setLayoutManager(mLayoutManager4);
        mRecyclerViewRemaining.setAdapter(remainingSongsAdapter);
        remainingSongsAdapter.notifyDataSetChanged();

        return view;
    }
}
