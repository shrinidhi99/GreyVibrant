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
import android.widget.Toast;

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
import com.example.greyvibrant.front.RecyclerViewElements.myAlbumItem;
import com.example.greyvibrant.front.adapter.albumFragmentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlbumFragment extends Fragment implements albumFragmentAdapter.OnItemClickListener {

    SharedPreferences sharedPreferences;
    private RecyclerView mRecyclerView;
    private albumFragmentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<myAlbumItem> albumList = new ArrayList<>();

    EditText searchBox;
    ImageView searchString;

    String songname, genre, album, language, AIDput;
    static String URL_REGIST = "https://sabios-97.000webhostapp.com/song_retrieval.php";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artist_fragment_album, container, false);

        searchBox = view.findViewById(R.id.searchBox);
        searchString = view.findViewById(R.id.search_string);
        searchString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        sharedPreferences = getContext().getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);
        AIDput = sharedPreferences.getString("AID", null);
        //song_retrieval

        mRecyclerView = view.findViewById(R.id.recycler_view);
        albumList.clear();
        Song_Retrieval();

        return view;
    }

    private void Song_Retrieval() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE FROM PHP", response);
                        try {
                            if (response == null || response.equals(""))
                                Log.i("RESPONSE", "IS NULL");

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("album");


                            if (success.equals("1")) {
                                Toast.makeText(getContext(), "Your Album", Toast.LENGTH_SHORT).show();

//                                Log.i("JsonArray length b4 loop",String.valueOf(jsonArray.length()));

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    songname = object.getString("songname");
                                    genre = object.getString("genre");
                                    language = object.getString("language");
                                    album = object.getString("album");
                                    albumList.add(new myAlbumItem(songname, album, genre, language));

                                    Log.i("SONG DETAILS", songname + " " + album + "\n\n");
                                }
                                mRecyclerView.setHasFixedSize(true);
                                mLayoutManager = new LinearLayoutManager(getContext());
                                mAdapter = new albumFragmentAdapter(albumList);
                                mRecyclerView.setLayoutManager(mLayoutManager);
                                mRecyclerView.setAdapter(mAdapter);
                                mAdapter.setOnItemClickListener((albumFragmentAdapter.OnItemClickListener) getContext());
                                mAdapter.notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "login Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "login2 Error", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("AID", AIDput);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void search() {
        String string = searchBox.getText().toString().trim();
        mAdapter.getFilter().filter(string);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onDeleteFromAlbumClick(int position) {

    }

    @Override
    public void onDescriptionClick(int position) {

    }
}
