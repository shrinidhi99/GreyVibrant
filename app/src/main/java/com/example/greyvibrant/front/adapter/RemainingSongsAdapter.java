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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

public class RemainingSongsAdapter extends RecyclerView.Adapter<RemainingSongsAdapter.RemainingSongsViewHolder> implements Filterable, View.OnClickListener {
    private List<remainingSongsItem> mRemainingSongsList;
    private List<remainingSongsItem> remainingSongsListFull;
    public OnItemClickListener mListener;
    static String URL_REGIST = "https://sabios-97.000webhostapp.com/listens.php";

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

    class RemainingSongsViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private TextView mSongname;
        private TextView mAlbum;
        private TextView mGenre;
        private TextView mLanguage;
        private TextView mArtistname;


        RemainingSongsViewHolder(@NonNull final View itemView) {
            super(itemView);
            mSongname = itemView.findViewById(R.id.song_name_remaining);
            mAlbum = itemView.findViewById(R.id.song_album_remaining);
            mGenre = itemView.findViewById(R.id.genre_remaining);
            mLanguage = itemView.findViewById(R.id.language_remaining);
            mArtistname = itemView.findViewById(R.id.song_artist_remaining);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        final int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                            final remainingSongsItem clickedItem = mRemainingSongsList.get(position);
                            Log.i("On item click", "remaining songs");
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
                            return true;
                        case 2:
                            mListener.onAddToPlaylistClick(position);
                            Log.d("on item click remaining", "onAddToPlaylistClick at position: " + position);
                            return true;
                    }
                    final remainingSongsItem clickedItem = mRemainingSongsList.get(position);
                    Log.i("On item click", "remaining songs");
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

    public RemainingSongsAdapter(ArrayList<remainingSongsItem> exampleList) {
        mRemainingSongsList = exampleList;
        remainingSongsListFull = new ArrayList<>(exampleList);
    }

    @NonNull
    @Override
    public RemainingSongsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.remainingsongsitem, parent, false);
        RemainingSongsViewHolder rsvh = new RemainingSongsViewHolder(v);
        return rsvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RemainingSongsViewHolder holder, int position) {
        remainingSongsItem currentItem = mRemainingSongsList.get(position);
        holder.mSongname.setText(currentItem.getmSongname());
        holder.mAlbum.setText(currentItem.getmAlbum());
        holder.mGenre.setText(currentItem.getmGenre());
        holder.mLanguage.setText(currentItem.getmLanguage());
        holder.mArtistname.setText(currentItem.getmArtistname());
    }

    @Override
    public int getItemCount() {
        return mRemainingSongsList.size();
    }

    @Override
    public Filter getFilter() {
        return remainingSongsFilter;
    }

    private Filter remainingSongsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<remainingSongsItem> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(remainingSongsListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (remainingSongsItem item : remainingSongsListFull) {
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
            mRemainingSongsList.clear();
            mRemainingSongsList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

}