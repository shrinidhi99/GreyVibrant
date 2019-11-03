package com.example.greyvibrant.front.adapter;

import android.content.Context;
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
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.greyvibrant.R;
import com.example.greyvibrant.front.RecyclerViewElements.SongsHistoryItem;
import com.example.greyvibrant.front.RecyclerViewElements.playlistItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SongsHistoryAdapter extends RecyclerView.Adapter<SongsHistoryAdapter.SongsHistoryViewHolder> implements Filterable {
    private Context mContext;
    static List<SongsHistoryItem> mSongsHistoryList;
    private List<SongsHistoryItem> songsHistoryListFull;
    private OnItemClickListener mListener;
    static String URL_REGIST2 = "https://sabios-97.000webhostapp.com/queue.php";
    static String URL_REGIST4 = "https://sabios-97.000webhostapp.com/songhistory_delete.php";



    public SongsHistoryAdapter(Context context, List<SongsHistoryItem> exampleList) {
        mContext = context;
        mSongsHistoryList = exampleList;
        songsHistoryListFull = new ArrayList<>(exampleList);
    }

    @Override
    public SongsHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.songshistoryitem, parent, false);
        return new SongsHistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SongsHistoryViewHolder holder, int position) {
        SongsHistoryItem currentItem = mSongsHistoryList.get(position);
        holder.mSongname.setText(currentItem.getmSongname());
        holder.mArtistname.setText(currentItem.getmArtistname());
    }

    @Override
    public int getItemCount() {
        return mSongsHistoryList.size();
    }

    public class SongsHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private TextView mSongname;
        private TextView mArtistname;

        public SongsHistoryViewHolder(View itemView) {
            super(itemView);
            mSongname = itemView.findViewById(R.id.song_name_songs_history);
            mArtistname = itemView.findViewById(R.id.song_artist_songs_history);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem playSong = menu.add(Menu.NONE, 1, 1, "Add to queue");
            MenuItem deleteFromHistory = menu.add(Menu.NONE, 2, 2, "Remove from history");
            MenuItem showDescription = menu.add(Menu.NONE, 3, 3, "More information");
            playSong.setOnMenuItemClickListener(this);
            deleteFromHistory.setOnMenuItemClickListener(this);
            showDescription.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    final SongsHistoryItem clickedItem = mSongsHistoryList.get(position);

                    switch (item.getItemId()) {
                        case 1:
                            mListener.onPlayClick(position);

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
                            mListener.onDeleteFromHistoryClick(position);

                            final String SIDfinal = String.valueOf(clickedItem.getmSID());

                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL_REGIST4,
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


                                                    Toast.makeText(itemView.getContext(), "Deletion Success", Toast.LENGTH_SHORT).show();





                                                } else {

                                                    Toast.makeText(itemView.getContext(), "Deletion Failed", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();

                                                Toast.makeText(itemView.getContext(), "Deletion Error", Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(itemView.getContext(), "Deletion2 Error", Toast.LENGTH_SHORT).show();

                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("SID", SIDfinal);



                                    return params;
                                }
                            };
                            RequestQueue requestQueue1 = Volley.newRequestQueue(itemView.getContext());
                            requestQueue1.add(stringRequest1);

                            return true;
                        case 3:
                            mListener.onDescriptionClick(position);
                            return true;
                    }
                }
            }
            return false;
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onPlayClick(int position);

        void onDeleteFromHistoryClick(int position);

        void onDescriptionClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public Filter getFilter() {
        return songsHistoryFilter;
    }

    private Filter songsHistoryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<SongsHistoryItem> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(songsHistoryListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (SongsHistoryItem item : songsHistoryListFull) {
                    if (item.getmSongname().toLowerCase().contains(filterPattern)
                            || item.getmArtistname().toLowerCase().contains(filterPattern)
                            || item.getmAlbum().toLowerCase().contains(filterPattern)
                            || item.getmGenre().toLowerCase().contains(filterPattern)
                            || item.getmLanguage().toLowerCase().contains(filterPattern)) {
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
            mSongsHistoryList.clear();
            mSongsHistoryList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}