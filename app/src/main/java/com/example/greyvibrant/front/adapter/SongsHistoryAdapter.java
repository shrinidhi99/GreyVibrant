package com.example.greyvibrant.front.adapter;

import android.view.LayoutInflater;
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

public class SongsHistoryAdapter extends RecyclerView.Adapter<SongsHistoryAdapter.SongsHistoryViewHolder> implements Filterable {
    private List<SongsHistoryItem> mSongsHistoryList;
    private List<SongsHistoryItem> songsHistoryListFull;


    static class SongsHistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView mSongname;
        private TextView mArtistname;


        SongsHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mSongname = itemView.findViewById(R.id.song_name_songs_history);
            mArtistname = itemView.findViewById(R.id.song_artist_songs_history);
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
        SongsHistoryViewHolder avh = new SongsHistoryViewHolder(v);
        return avh;
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
                    if (item.getmSongname().toLowerCase().contains(filterPattern) || item.getmArtistname().toLowerCase().contains(filterPattern) || item.getmAlbum().toLowerCase().contains(filterPattern) || item.getmGenre().toLowerCase().contains(filterPattern) || item.getmLanguage().toLowerCase().contains(filterPattern)) {
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
