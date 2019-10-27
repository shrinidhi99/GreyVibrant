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
import com.example.greyvibrant.front.RecyclerViewElements.playlistItem;

import java.util.ArrayList;
import java.util.List;

public class PlaylistFragmentAdapter extends RecyclerView.Adapter<PlaylistFragmentAdapter.PlaylistViewHolder> implements Filterable {
    static List<playlistItem> mPlayList;

    private List<playlistItem> playListFull;
    public OnItemClickListener mListener;
    static String URL_REGIST = "https://sabios-97.000webhostapp.com/queue.php";


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    class PlaylistViewHolder extends RecyclerView.ViewHolder {

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
            itemView.setOnClickListener(new View.OnClickListener() {
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
            });
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
        holder.mArtistname.setText(currentItem.getmArtistname());
        holder.mSongname.setText(currentItem.getmSongname());
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
            mPlayList.clear();
            mPlayList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}