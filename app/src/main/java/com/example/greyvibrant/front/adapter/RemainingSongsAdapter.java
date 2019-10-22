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
import com.example.greyvibrant.front.RecyclerViewElements.remainingSongsItem;

import java.util.ArrayList;
import java.util.List;

public class RemainingSongsAdapter extends RecyclerView.Adapter<RemainingSongsAdapter.RemainingSongsViewHolder> implements Filterable {
    private List<remainingSongsItem> mRemainingSongsList;
    private List<remainingSongsItem> remainingSongsListFull;


    static class RemainingSongsViewHolder extends RecyclerView.ViewHolder {
        private TextView mSongname;
        private TextView mAlbum;
        private TextView mGenre;
        private TextView mLanguage;
        private TextView mArtistname;


        RemainingSongsViewHolder(@NonNull View itemView) {
            super(itemView);
            mSongname = itemView.findViewById(R.id.song_name_remaining);
            mAlbum = itemView.findViewById(R.id.song_album_remaining);
            mGenre = itemView.findViewById(R.id.genre_remaining);
            mLanguage = itemView.findViewById(R.id.language_remaining);
            mArtistname = itemView.findViewById(R.id.song_artist_remaining);
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