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

import com.example.greyvibrant.R;
import com.example.greyvibrant.front.RecyclerViewElements.SongsHistoryItem;

import java.util.ArrayList;
import java.util.List;

public class SongsHistoryAdapter extends RecyclerView.Adapter<SongsHistoryAdapter.SongsHistoryViewHolder> implements Filterable, View.OnClickListener {

    static List<SongsHistoryItem> mSongsHistoryList;
    private List<SongsHistoryItem> songsHistoryListFull;
    private OnItemClickListener mListener;


    @Override
    public void onClick(View view) {

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

    class SongsHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private TextView mSongname;
        private TextView mArtistname;


        SongsHistoryViewHolder(@NonNull final View itemView) {
            super(itemView);
            mSongname = itemView.findViewById(R.id.song_name_songs_history);
            mArtistname = itemView.findViewById(R.id.song_artist_songs_history);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");
            MenuItem playSong = contextMenu.add(Menu.NONE, 1, 1, "Add to queue");
            MenuItem deleteFromHistory = contextMenu.add(Menu.NONE, 2, 2, "Remove from history");
            MenuItem showDescription = contextMenu.add(Menu.NONE, 3, 3, "More information");
            playSong.setOnMenuItemClickListener(this);
            deleteFromHistory.setOnMenuItemClickListener(this);
            showDescription.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (mListener != null) {
                final int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    final SongsHistoryItem clickedItem3 = mSongsHistoryList.get(position);
                    switch (menuItem.getItemId()) {
                        case 1:
                            mListener.onPlayClick(position);
                            Log.d("on item click remaining", "onPlayClick at position: " + position);
                            return true;
                        case 2:
                            mListener.onDeleteFromHistoryClick(position);
                            Log.d("on item click remaining", "onDeleteFromItemClick at position: " + position);
                            return true;
                        case 3:
                            mListener.onDescriptionClick(position);
                            Log.d("on item click remaining", "onDescriptionClick at position: " + position);

                            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                            builder.setTitle("More Info")
                                    .setIcon(R.drawable.ic_audiotrack_black_24dp)
                                    .setMessage("Song name:   " + clickedItem3.getmSongname() + "\n" +
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
                    final SongsHistoryItem clickedItem = mSongsHistoryList.get(position);
                }
            }
        }
    }

    public SongsHistoryAdapter(ArrayList<SongsHistoryItem> exampleList) {
        mSongsHistoryList = exampleList;
        songsHistoryListFull = new ArrayList<>(exampleList);
    }

    @NonNull
    @Override
    public SongsHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.songshistoryitem, parent, false);
        SongsHistoryViewHolder shvh = new SongsHistoryViewHolder(v);
        return shvh;
    }

    @Override
    public void onBindViewHolder(@NonNull SongsHistoryViewHolder holder, int position) {
        SongsHistoryItem currentItem = mSongsHistoryList.get(position);
        holder.mSongname.setText(currentItem.getmSongname());
        holder.mArtistname.setText(currentItem.getmArtistname());
    }

    @Override
    public int getItemCount() {
        return mSongsHistoryList.size();
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