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

    class RemainingSongsViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private TextView mSongname;
        private TextView mArtistname;


        RemainingSongsViewHolder(@NonNull final View itemView) {
            super(itemView);
            mSongname = itemView.findViewById(R.id.song_name_remaining);
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
            MenuItem addToQueue = contextMenu.add(Menu.NONE, 1, 1, "Add to queue");
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
                            final remainingSongsItem clickedItem = mRemainingSongsList.get(position);
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
                            final remainingSongsItem clickedItem2 = mRemainingSongsList.get(position);
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
                            final remainingSongsItem clickedItem3 = mRemainingSongsList.get(position);
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