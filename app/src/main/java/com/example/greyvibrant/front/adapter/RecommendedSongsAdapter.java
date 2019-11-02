package com.example.greyvibrant.front.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

        void onDescriptionClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    class RecommendedSongsViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private TextView mSongname;
        private TextView mArtistname;


        RecommendedSongsViewHolder(@NonNull final View itemView) {
            super(itemView);
            mSongname = itemView.findViewById(R.id.song_name_recommended);
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
            MenuItem showDescription = contextMenu.add(Menu.NONE, 3, 3, "More information");
            addToQueue.setOnMenuItemClickListener(this);
            addToPlaylist.setOnMenuItemClickListener(this);
            showDescription.setOnMenuItemClickListener(this);
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
                        case 3:
                            mListener.onDescriptionClick(position);
                            Log.d("on item click remaining", "onDescriptionClick at position: " + position);
                            final recommendedSongsItem clickedItem3 = mRecommendedSongsList.get(position);
                            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                            builder.setTitle("More Info")
                                    .setIcon(R.drawable.ic_audiotrack_black_24dp)
                                    .setMessage("Song name:   " + clickedItem3.getmSongname() + "\n" +
                                            "Artist name:  " + clickedItem3.getmArtistname() + "\n" +
                                            "Album:           " + clickedItem3.getmAlbum() + "\n" +
                                            "Genre:            " + clickedItem3.getmGenre() + "\n" +
                                            "Language:     " + clickedItem3.getmLanguage())
                                    .setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                            builder.show();
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