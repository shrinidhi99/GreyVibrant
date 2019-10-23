package com.example.greyvibrant.front.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greyvibrant.R;
import com.example.greyvibrant.front.RecyclerViewElements.followedArtistsItem;

import java.util.ArrayList;
import java.util.List;

public class FollowedArtistAdapter extends RecyclerView.Adapter<FollowedArtistAdapter.FollowedArtistViewHolder> implements Filterable {
    private List<followedArtistsItem> mFollowedArtistList;
    private List<followedArtistsItem> followedArtistListFull;
    public OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    class FollowedArtistViewHolder extends RecyclerView.ViewHolder {

        private TextView mArtistname;
        private int mAID;

        FollowedArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            mArtistname = itemView.findViewById(R.id.artist_name_followed);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                            followedArtistsItem clickedItem = mFollowedArtistList.get(position);
//                            Toast.makeText(getContext(), clickedItem.getmArtistname(), Toast.LENGTH_SHORT).show();
                            Log.d("onItemClick", clickedItem.getmArtistname());
                        }
                    }
                }
            });
        }
    }

    public FollowedArtistAdapter(ArrayList<followedArtistsItem> exampleList) {
        mFollowedArtistList = exampleList;
        followedArtistListFull = new ArrayList<>(exampleList);
    }

    @NonNull
    @Override
    public FollowedArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.followedartistsitem, parent, false);
        FollowedArtistViewHolder uavh = new FollowedArtistViewHolder(v);
        return uavh;
    }

    @Override
    public void onBindViewHolder(@NonNull FollowedArtistViewHolder holder, int position) {
        followedArtistsItem currentItem = mFollowedArtistList.get(position);
        holder.mArtistname.setText(currentItem.getmArtistname());
    }

    @Override
    public int getItemCount() {
        return mFollowedArtistList.size();
    }

    @Override
    public Filter getFilter() {
        return followArtistsFilter;
    }

    private Filter followArtistsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<followedArtistsItem> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(followedArtistListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (followedArtistsItem item : followedArtistListFull) {
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
            mFollowedArtistList.clear();
            mFollowedArtistList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

}
