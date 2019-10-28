package com.example.greyvibrant.front;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.greyvibrant.R;
import com.example.greyvibrant.front.RecyclerViewElements.followedArtistsItem;
import com.example.greyvibrant.front.RecyclerViewElements.recommendedSongsItem;
import com.example.greyvibrant.front.RecyclerViewElements.remainingSongsItem;
import com.example.greyvibrant.front.RecyclerViewElements.unfollowedArtistsItem;
import com.example.greyvibrant.front.adapter.FollowedArtistAdapter;
import com.example.greyvibrant.front.adapter.QueueFragmentAdapter;
import com.example.greyvibrant.front.adapter.RecommendedSongsAdapter;
import com.example.greyvibrant.front.adapter.RemainingSongsAdapter;
import com.example.greyvibrant.front.adapter.UnfollowedArtistAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment implements FollowedArtistAdapter.OnItemClickListener, UnfollowedArtistAdapter.OnItemClickListener, RecommendedSongsAdapter.OnItemClickListener, RemainingSongsAdapter.OnItemClickListener {
    static String URL_REGIST1 = "https://sabios-97.000webhostapp.com/artists_retrieval.php";
    static String URL_REGIST2 = "https://sabios-97.000webhostapp.com/recommended_songs.php";
    static String URL_REGIST3 = "https://sabios-97.000webhostapp.com/remaining_songs.php";

    private RecyclerView mRecyclerViewFollowed, mRecyclerViewUnfollowed, mRecyclerViewRecommended, mRecyclerViewRemaining;
    private RecyclerView.LayoutManager mLayoutManager1, mLayoutManager2, mLayoutManager3, mLayoutManager4;

    static ArrayList<unfollowedArtistsItem> unfollowedArtistsItemsList = new ArrayList<>();
    static ArrayList<followedArtistsItem> followedArtistsItemsList = new ArrayList<>();
    ArrayList<recommendedSongsItem> recommendedSongsItemsList = new ArrayList<>();
    ArrayList<remainingSongsItem> remainingSongsItemsList = new ArrayList<>();

    private UnfollowedArtistAdapter unfollowedArtistAdapter;
    private FollowedArtistAdapter followedArtistAdapter;
    private RecommendedSongsAdapter recommendedSongsAdapter;
    private RemainingSongsAdapter remainingSongsAdapter;
    SharedPreferences sharedPreferences;
    String UIDPut;

    ImageView search_button;
    EditText search_box;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment_home, container, false);
        sharedPreferences = getContext().getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);
        UIDPut = sharedPreferences.getString("UID", null);
        unfollowedArtistsItemsList.clear();
        followedArtistsItemsList.clear();
        recommendedSongsItemsList.clear();
        mRecyclerViewFollowed = view.findViewById(R.id.recycler_view_followed);
        mRecyclerViewUnfollowed = view.findViewById(R.id.recycler_view_unfollowed);
        mRecyclerViewRecommended = view.findViewById(R.id.recycler_view_recommended);
        mRecyclerViewRemaining = view.findViewById(R.id.recycler_view_remaining);
        search_box = view.findViewById(R.id.search_box);
        search_button = view.findViewById(R.id.search_button);
        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                startActivity(getActivity().getIntent());
                pullToRefresh.setRefreshing(false);
            }
        });

        getFollowedArtists();

        getRecommendedSongs();

        getRemainingSongs();

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        return view;
    }


    public void getRecommendedSongs() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE FROM PHP for songs", response);
                        try {
                            if (response == null || response.equals(""))
                                Log.i("RESPONSE", "IS NULL");

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("songdetail");


                            if (success.equals("1")) {
//                                Toast.makeText(getContext(), "Recommended Songs", Toast.LENGTH_SHORT).show();


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

                                    recommendedSongsItemsList.add(new recommendedSongsItem(songname, Integer.parseInt(SID), Integer.parseInt(AID), Integer.parseInt(UIDPut), album, genre, language, artistname, songurl));

                                    Log.i("artist :", artistname + " " + AID + " " + songname + " " + songurl + " " + album + " " + language + " " + genre + " " + SID);
                                }

                                mRecyclerViewRecommended.setHasFixedSize(true);
                                mLayoutManager3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                recommendedSongsAdapter = new RecommendedSongsAdapter(recommendedSongsItemsList);
                                mRecyclerViewRecommended.setLayoutManager(mLayoutManager3);
                                mRecyclerViewRecommended.setAdapter(recommendedSongsAdapter);
                                recommendedSongsAdapter.setOnItemClickListener((RecommendedSongsAdapter.OnItemClickListener) getContext());
                                recommendedSongsAdapter.notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(getContext(), "RS Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getContext(), "Artist Error 2", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("UID", UIDPut);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    public void getRemainingSongs() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE FROM PHP for remaining songs", response);
                        try {
                            if (response == null || response.equals(""))
                                Log.i("RESPONSE", "IS NULL");

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("songdetail2");


                            if (success.equals("1")) {
//                                Toast.makeText(getContext(), "Remaining Songs", Toast.LENGTH_SHORT).show();


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

                                    remainingSongsItemsList.add(new remainingSongsItem(songname, Integer.parseInt(SID), Integer.parseInt(AID), Integer.parseInt(UIDPut), album, genre, language, artistname, songurl));

                                    Log.i("artist :", artistname + " " + AID + " " + songname + " " + songurl + " " + album + " " + language + " " + genre + " " + SID);
                                }

                                mRecyclerViewRecommended.setHasFixedSize(true);
                                mLayoutManager4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                remainingSongsAdapter = new RemainingSongsAdapter(remainingSongsItemsList);
                                mRecyclerViewRemaining.setLayoutManager(mLayoutManager4);
                                mRecyclerViewRemaining.setAdapter(remainingSongsAdapter);
                                remainingSongsAdapter.setOnItemClickListener((RemainingSongsAdapter.OnItemClickListener) getContext());
                                remainingSongsAdapter.notifyDataSetChanged();
                                // Toast.makeText(getApplicationContext(), "Log in", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(getContext(), "Remaining songs Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getContext(), "Artist Error 3", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("UID", UIDPut);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }


    public void getFollowedArtists() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE FROM PHP", response);
                        try {
                            if (response == null || response.equals(""))
                                Log.i("RESPONSE", "IS NULL");

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArrayfol = jsonObject.getJSONArray("folartist");
                            JSONArray jsonArrayunfol = jsonObject.getJSONArray("unfolartist");


                            if (success.equals("1")) {
//                                Toast.makeText(getContext(), "Artists", Toast.LENGTH_SHORT).show();


                                for (int i = 0; i < jsonArrayfol.length(); i++) {
                                    JSONObject object = jsonArrayfol.getJSONObject(i);
                                    String artistname = object.getString("artistname");
                                    String AID = object.getString("AID");
                                    followedArtistsItemsList.add(new followedArtistsItem(R.drawable.ic_done_black_24dp, artistname, Integer.parseInt(AID), Integer.parseInt(UIDPut)));

                                    Log.i("artist :", artistname + "  " + " " + AID);
                                }

                                mRecyclerViewFollowed.setHasFixedSize(true);
                                mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                followedArtistAdapter = new FollowedArtistAdapter(followedArtistsItemsList);
                                mRecyclerViewFollowed.setLayoutManager(mLayoutManager1);
                                mRecyclerViewFollowed.setAdapter(followedArtistAdapter);
                                followedArtistAdapter.setOnItemClickListener((FollowedArtistAdapter.OnItemClickListener) getContext());
                                followedArtistAdapter.notifyDataSetChanged();
                                for (int i = 0; i < jsonArrayunfol.length(); i++) {
                                    JSONObject object = jsonArrayunfol.getJSONObject(i);
                                    String artistname = object.getString("artistname");
                                    String AID = object.getString("AID");
                                    unfollowedArtistsItemsList.add(new unfollowedArtistsItem(R.drawable.ic_done_black_24dp, artistname, Integer.parseInt(AID), Integer.parseInt(UIDPut)));

                                    Log.i("artist :", artistname + "  " + " " + AID);
                                }
                                mRecyclerViewUnfollowed.setHasFixedSize(true);
                                mLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                unfollowedArtistAdapter = new UnfollowedArtistAdapter(unfollowedArtistsItemsList);
                                mRecyclerViewUnfollowed.setLayoutManager(mLayoutManager2);
                                mRecyclerViewUnfollowed.setAdapter(unfollowedArtistAdapter);
                                unfollowedArtistAdapter.setOnItemClickListener((UnfollowedArtistAdapter.OnItemClickListener) getContext());
                                unfollowedArtistAdapter.notifyDataSetChanged();


                                // Toast.makeText(getApplicationContext(), "Log in", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(getContext(), "Artist Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getContext(), "Artist Error 2", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("UID", UIDPut);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onPlayClick(int position) {

    }

    @Override
    public void onAddToPlaylistClick(int position) {

    }

    private void search() {
        String string = search_box.getText().toString().trim();
        followedArtistAdapter.getFilter().filter(string);
        unfollowedArtistAdapter.getFilter().filter(string);
        recommendedSongsAdapter.getFilter().filter(string);
        remainingSongsAdapter.getFilter().filter(string);
    }

}
