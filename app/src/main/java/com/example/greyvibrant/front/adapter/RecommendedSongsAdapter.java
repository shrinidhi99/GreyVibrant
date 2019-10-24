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
import com.example.greyvibrant.front.RecyclerViewElements.recommendedSongsItem;

import java.util.ArrayList;
import java.util.List;

public class RecommendedSongsAdapter extends RecyclerView.Adapter<RecommendedSongsAdapter.RecommendedSongsViewHolder> implements Filterable {

    private List<recommendedSongsItem> mRecommendedSongsList;
    private List<recommendedSongsItem> recommendedSongsListFull;


    static class RecommendedSongsViewHolder extends RecyclerView.ViewHolder {
        private TextView mSongname;
        private TextView mAlbum;
        private TextView mGenre;
        private TextView mLanguage;
        private TextView mArtistname;


        RecommendedSongsViewHolder(@NonNull View itemView) {
            super(itemView);
            mSongname = itemView.findViewById(R.id.song_name_recommended);
            mAlbum = itemView.findViewById(R.id.song_album_recommended);
            mGenre = itemView.findViewById(R.id.genre_recommended);
            mLanguage = itemView.findViewById(R.id.language_recommended);
            mArtistname = itemView.findViewById(R.id.song_artist_recommended);
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