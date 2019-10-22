package com.example.greyvibrant.front.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greyvibrant.R;
import com.example.greyvibrant.front.RecyclerViewElements.unfollowedArtistsItem;

import java.util.ArrayList;
import java.util.List;

public class UnfollowedArtistAdapter extends RecyclerView.Adapter<UnfollowedArtistAdapter.UnfollowedArtistViewHolder> implements Filterable {
    private List<unfollowedArtistsItem> mUnfollowedArtistList;
    private List<unfollowedArtistsItem> unfollowedArtistListFull;


    static class UnfollowedArtistViewHolder extends RecyclerView.ViewHolder {

        private TextView mArtistname;
        private int mAID;
//        private ImageView mImage;

        UnfollowedArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            mArtistname = itemView.findViewById(R.id.artist_name_unfollowed);
//            mImage = itemView.findViewById(R.id.follow_button);
        }
    }

    public UnfollowedArtistAdapter(ArrayList<unfollowedArtistsItem> exampleList) {
        mUnfollowedArtistList = exampleList;
        unfollowedArtistListFull = new ArrayList<>(exampleList);
    }

    @NonNull
    @Override
    public UnfollowedArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.unfollowedartistsitem, parent, false);
        UnfollowedArtistViewHolder uavh = new UnfollowedArtistViewHolder(v);
        return uavh;
    }

    @Override
    public void onBindViewHolder(@NonNull UnfollowedArtistViewHolder holder, int position) {
        unfollowedArtistsItem currentItem = mUnfollowedArtistList.get(position);
        holder.mArtistname.setText(currentItem.getmArtistname());
//        holder.mImage.setImageResource(currentItem.getmImageResource());
    }

    @Override
    public int getItemCount() {
        return mUnfollowedArtistList.size();
    }

    @Override
    public Filter getFilter() {
        return unfollowArtistsFilter;
    }

    private Filter unfollowArtistsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<unfollowedArtistsItem> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(unfollowedArtistListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (unfollowedArtistsItem item : unfollowedArtistListFull) {
                    if (item.getmArtistname().toLowerCase().contains(filterPattern)) {
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
            mUnfollowedArtistList.clear();
            mUnfollowedArtistList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

}