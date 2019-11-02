package com.example.greyvibrant.front.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.greyvibrant.R;
import com.example.greyvibrant.front.HomePageUser;
import com.example.greyvibrant.front.QueueFragment;
import com.example.greyvibrant.front.RecyclerViewElements.playlistItem;
import com.example.greyvibrant.front.RecyclerViewElements.queueItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueueFragmentAdapter extends RecyclerView.Adapter<QueueFragmentAdapter.QueueViewHolder> implements Filterable {
    static List<queueItem> mQueueItemList;

    private List<queueItem> queueListFull;
    public OnItemClickListener mListener;
    static String URL_REGIST = "https://sabios-97.000webhostapp.com/queue_delete.php";


    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDescriptionClick(int position);

        void onDeleteItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    class QueueViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        private TextView mSongname;
        private TextView mArtistname;


        QueueViewHolder(@NonNull final View itemView) {
            super(itemView);
            mSongname = itemView.findViewById(R.id.song_name_queue);
            mArtistname = itemView.findViewById(R.id.song_artist_queue);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        final int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                            final queueItem clickedItem = mQueueItemList.get(position);
                            Log.i("SONG NAME AND POSITION CLICKED", clickedItem.getmSongname() + " " + clickedItem.getmSongurl());
                            QueueFragment.currentSongPos = position;
                            // String songurl=clickedItem.getmSongurl();
                            QueueFragment.StartNewSong(position);

                        }
                    }
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");
            MenuItem deleteFromQueue = contextMenu.add(Menu.NONE, 1, 1, "Remove from queue");
            MenuItem showDescription = contextMenu.add(Menu.NONE, 2, 2, "More information");
            deleteFromQueue.setOnMenuItemClickListener(this);
            showDescription.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (mListener != null) {
                final int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
//                    mListener.onItemClick(position);
                    final queueItem clickedItem3 = mQueueItemList.get(position);

                    switch (menuItem.getItemId()) {
                        case 1:
                            mListener.onDeleteItemClick(position);
                            Log.d("on item click remaining", "onDeleteItemClick at position: " + position);

                            final String SIDfinal = String.valueOf(clickedItem3.getmSID());

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
                            RequestQueue requestQueue = Volley.newRequestQueue(itemView.getContext());
                            requestQueue.add(stringRequest);


                            return true;
                        case 2:
                            mListener.onDescriptionClick(position);
                            Log.d("on item click remaining", "onDescriptionClick at position: " + position);
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
    }

    public QueueFragmentAdapter(ArrayList<queueItem> exampleList) {
        mQueueItemList = exampleList;
        queueListFull = new ArrayList<>(exampleList);
    }

    @NonNull
    @Override
    public QueueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.queueitem, parent, false);
        QueueViewHolder qvh = new QueueViewHolder(v);
        return qvh;
    }

    @Override
    public void onBindViewHolder(@NonNull QueueViewHolder holder, int position) {
        queueItem currentItem = mQueueItemList.get(position);
        holder.mArtistname.setText(currentItem.getmArtistname());
        holder.mSongname.setText(currentItem.getmSongname());
    }

    @Override
    public int getItemCount() {
        return mQueueItemList.size();
    }

    @Override
    public Filter getFilter() {
        return queueFilter;
    }

    private Filter queueFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<queueItem> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(queueListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (queueItem item : queueListFull) {
                    if (item.getmArtistname().toLowerCase().contains(filterPattern) || item.getmSongname().toLowerCase().contains(filterPattern)) {
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
            mQueueItemList.clear();
            mQueueItemList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}