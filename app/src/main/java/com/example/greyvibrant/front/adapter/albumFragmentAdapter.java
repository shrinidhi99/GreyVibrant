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
import com.example.greyvibrant.front.RecyclerViewElements.myAlbumItem;

import java.util.ArrayList;
import java.util.List;

public class albumFragmentAdapter extends RecyclerView.Adapter<albumFragmentAdapter.AlbumViewHolder> implements Filterable {
    private List<myAlbumItem> mAlbumList;
    private List<myAlbumItem> albumListFull;


    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        private TextView mSongname;
        private TextView mAlbum;
        private TextView mGenre;
        private TextView mLanguage;


        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            mSongname = itemView.findViewById(R.id.song_name);
            mAlbum = itemView.findViewById(R.id.song_album);
            mGenre = itemView.findViewById(R.id.genre);
            mLanguage = itemView.findViewById(R.id.language);
        }
    }

    public albumFragmentAdapter(ArrayList<myAlbumItem> exampleList) {
        mAlbumList = exampleList;
        albumListFull = new ArrayList<>(exampleList);
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.myalbumitem, parent, false);
        AlbumViewHolder avh = new AlbumViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        myAlbumItem currentItem = mAlbumList.get(position);
        holder.mSongname.setText(currentItem.getmSongname());
        holder.mAlbum.setText(currentItem.getmAlbum());
        holder.mGenre.setText(currentItem.getmGenre());
        holder.mLanguage.setText(currentItem.getmLanguage());
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<myAlbumItem> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(albumListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (myAlbumItem item : albumListFull) {
                    if (item.getmSongname().toLowerCase().contains(filterPattern) || item.getmAlbum().toLowerCase().contains(filterPattern) || item.getmGenre().toLowerCase().contains(filterPattern) || item.getmLanguage().toLowerCase().contains(filterPattern)) {
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
            mAlbumList.clear();
            mAlbumList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

}
