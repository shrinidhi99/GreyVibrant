package com.example.greyvibrant.front.adapter;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.greyvibrant.R;
import com.example.greyvibrant.front.RecyclerViewElements.recommendedSongsItem;
import com.example.greyvibrant.front.RecyclerViewElements.remainingSongsItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecommendedSongsAdapter extends RecyclerView.Adapter<RecommendedSongsAdapter.RecommendedSongsViewHolder> implements Filterable, View.OnClickListener {

    static List<recommendedSongsItem> mRecommendedSongsList;
    private List<recommendedSongsItem> recommendedSongsListFull;
    public OnItemClickListener mListener;
    static String URL_REGIST = "https://sabios-97.000webhostapp.com/listens.php";
    static String URL_REGIST2 = "https://sabios-97.000webhostapp.com/queue.php";
    static String URL_REGIST3 = "https://sabios-97.000webhostapp.com/playlist_name.php";


    @Override
    public void onClick(View view) {

    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onPlayClick(int position);

        void onAddToPlaylistClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    class RecommendedSongsViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private TextView mSongname;
        private TextView mAlbum;
        private TextView mGenre;
        private TextView mLanguage;
        private TextView mArtistname;


        RecommendedSongsViewHolder(@NonNull final View itemView) {
            super(itemView);
            mSongname = itemView.findViewById(R.id.song_name_recommended);
            mAlbum = itemView.findViewById(R.id.song_album_recommended);
            mGenre = itemView.findViewById(R.id.genre_recommended);
            mLanguage = itemView.findViewById(R.id.language_recommended);
            mArtistname = itemView.findViewById(R.id.song_artist_recommended);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        final int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                            final recommendedSongsItem clickedItem = mRecommendedSongsList.get(position);
                            Log.i("On item click", "recommended songs");
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
                                                if (success.equals("1")) {
                                                    Log.i("LISTENS", "SUCCESS");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Log.i("LISTENS", "ERROR");

                                            }

                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.i("LISTENS", "ERROR 2");

                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();

                                    params.put("UID", String.valueOf(clickedItem.getmUID()));
                                    params.put("SID", String.valueOf(clickedItem.getmSID()));
                                    return params;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(itemView.getContext());
                            requestQueue.add(stringRequest);
                        }
                    }
                }
            });
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");
            MenuItem addToQueue = contextMenu.add(Menu.NONE, 1, 1, "Play now");
            MenuItem addToPlaylist = contextMenu.add(Menu.NONE, 2, 2, "Add to playlist");
            addToQueue.setOnMenuItemClickListener(this);
            addToPlaylist.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (mListener != null) {
                final int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
//                    mListener.onItemClick(position);
                    switch (menuItem.getItemId()) {
                        case 1:
                            mListener.onPlayClick(position);
                            Log.d("on item click remaining", "onPlayClick at position: " + position);
                            final recommendedSongsItem clickedItem = mRecommendedSongsList.get(position);
                            Log.i("On item click", "remaining songs");
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST2,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.i("RESPONSE FROM PHP", response);
                                            try {
                                                if (response == null || response.equals(""))
                                                    Log.i("RESPONSE", "IS NULL");

                                                JSONObject jsonObject = new JSONObject(response);


                                                String success = jsonObject.getString("success");
                                                if (success.equals("1")) {
                                                    Log.i("QUEUE", "SUCCESS");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Log.i("QUEUE", "ERROR");

                                            }

                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.i("QUEUE", "ERROR 2");

                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();

                                    params.put("UID", String.valueOf(clickedItem.getmUID()));
                                    params.put("SID", String.valueOf(clickedItem.getmSID()));
                                    return params;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(itemView.getContext());
                            requestQueue.add(stringRequest);
                            return true;
                        case 2:
                            mListener.onAddToPlaylistClick(position);
                            Log.d("on item click remaining", "onAddToPlaylistClick at position: " + position);
                            final recommendedSongsItem clickedItem2 = mRecommendedSongsList.get(position);
                            Log.i("On item click", "remaining songs");
                            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, URL_REGIST3,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.i("RESPONSE FROM PHP", response);
                                            try {
                                                if (response == null || response.equals(""))
                                                    Log.i("RESPONSE", "IS NULL");

                                                JSONObject jsonObject = new JSONObject(response);


                                                String success = jsonObject.getString("success");
                                                if (success.equals("1")) {
                                                    Log.i("PLAYLIST", "SUCCESS");
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Log.i("PLAYLIST", "ERROR");

                                            }

                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.i("QUEUE", "ERROR 2");

                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();

                                    params.put("UID", String.valueOf(clickedItem2.getmUID()));
                                    params.put("SID", String.valueOf(clickedItem2.getmSID()));
                                    return params;
                                }
                            };
                            RequestQueue requestQueue2 = Volley.newRequestQueue(itemView.getContext());
                            requestQueue2.add(stringRequest2);
                            return true;
                    }
                    final recommendedSongsItem clickedItem = mRecommendedSongsList.get(position);
                    Log.i("On item click", "recommended songs normal click");
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
                                        if (success.equals("1")) {
                                            Log.i("LISTENS", "SUCCESS");
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.i("LISTENS", "ERROR");

                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.i("LISTENS", "ERROR 2");

                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();

                            params.put("UID", String.valueOf(clickedItem.getmUID()));
                            params.put("SID", String.valueOf(clickedItem.getmSID()));
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(itemView.getContext());
                    requestQueue.add(stringRequest);
                }
            }
            return false;
        }
    }

    public RecommendedSongsAdapter(ArrayList<recommendedSongsItem> exampleList) {
        mRecommendedSongsList = exampleList;
        recommendedSongsListFull = new ArrayList<>(exampleList);
    }

    @NonNull
    @Override
    public RecommendedSongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommendedsongsitem, parent, false);
        RecommendedSongsViewHolder rsvh = new RecommendedSongsViewHolder(v);
        return rsvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedSongsViewHolder holder, int position) {
        recommendedSongsItem currentItem = mRecommendedSongsList.get(position);
        holder.mSongname.setText(currentItem.getmSongname());
        holder.mAlbum.setText(currentItem.getmAlbum());
        holder.mGenre.setText(currentItem.getmGenre());
        holder.mLanguage.setText(currentItem.getmLanguage());
        holder.mArtistname.setText(currentItem.getmArtistname());
    }

    @Override
    public int getItemCount() {
        return mRecommendedSongsList.size();
    }

    @Override
    public Filter getFilter() {
        return recommendedSongsFilter;
    }

    private Filter recommendedSongsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<recommendedSongsItem> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(recommendedSongsListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (recommendedSongsItem item : recommendedSongsListFull) {
                    if (item.getmSongname().toLowerCase().contains(filterPattern) || item.getmAlbum().toLowerCase().contains(filterPattern) || item.getmGenre().toLowerCase().contains(filterPattern) || item.getmLanguage().toLowerCase().contains(filterPattern) || item.getmArtistname().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mRecommendedSongsList.clear();
            mRecommendedSongsList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

}