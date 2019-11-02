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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.greyvibrant.R;
import com.example.greyvibrant.front.RecyclerViewElements.playlistItem;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlaylistFragmentAdapter extends RecyclerView.Adapter<PlaylistFragmentAdapter.PlaylistViewHolder> implements Filterable, View.OnClickListener {

    static List<playlistItem> mPlayList;
    private List<playlistItem> playListFull;
    private OnItemClickListener mListener;
    static String URL_REGIST = "https://sabios-97.000webhostapp.com/listens.php";
    static String URL_REGIST2 = "https://sabios-97.000webhostapp.com/queue.php";
    static String URL_REGIST3 = "https://sabios-97.000webhostapp.com/playlist_name.php";
    static String URL_REGIST4 = "https://sabios-97.000webhostapp.com/playlist_delete.php";



    @Override
    public void onClick(View view) {

    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onPlayClick(int position);

        void onDeleteFromPlaylistClick(int position);

        void onDescriptionClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    class PlaylistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private TextView mSongname;
        private String mAlbum;
        private String mGenre;
        private String mLanguage;
        private TextView mArtistname;
        private String mSongurl;
        private int mAID;
        private int mUID;
        private int mSID;


        PlaylistViewHolder(@NonNull final View itemView) {
            super(itemView);
            mSongname = itemView.findViewById(R.id.song_name_playlist);
            mArtistname = itemView.findViewById(R.id.song_artist_playlist);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");
            MenuItem addToQueue = contextMenu.add(Menu.NONE, 1, 1, "Play now");
            MenuItem deleteFromPlaylist = contextMenu.add(Menu.NONE, 2, 2, "Remove From playlist");
            MenuItem showDescription = contextMenu.add(Menu.NONE, 3, 3, "More information");
            addToQueue.setOnMenuItemClickListener(this);
            deleteFromPlaylist.setOnMenuItemClickListener(this);
            showDescription.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (mListener != null) {
                final int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
//                    mListener.onItemClick(position);
                    final playlistItem clickedItem = mPlayList.get(position);

                    switch (menuItem.getItemId()) {
                        case 1:
                            mListener.onPlayClick(position);
                            Log.d("on item click remaining", "onPlayClick at position: " + position);

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
                            mListener.onDeleteFromPlaylistClick(position);
                            Log.d("on item click remaining", "onDeleteFromPlaylistClick at position: " + position);
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
                            Log.d("on item click remaining", "onDescriptionClick at position: " + position);
                            final playlistItem clickedItem3 = mPlayList.get(position);
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

                }
            }
            return false;
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                final int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                    final playlistItem clickedItem = mPlayList.get(position);
                }
            }
        }
    }

    public PlaylistFragmentAdapter(ArrayList<playlistItem> exampleList) {
        mPlayList = exampleList;
        playListFull = new ArrayList<>(exampleList);
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlistitem, parent, false);
        PlaylistViewHolder pvh = new PlaylistViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        playlistItem currentItem = mPlayList.get(position);
        holder.mSongname.setText(currentItem.getmSongname());
        holder.mArtistname.setText(currentItem.getmArtistname());
    }

    @Override
    public int getItemCount() {
        return mPlayList.size();
    }

    @Override
    public Filter getFilter() {
        return playlistFilter;
    }

    private Filter playlistFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<playlistItem> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(playListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (playlistItem item : playListFull) {
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
            mPlayList.clear();
            mPlayList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

}