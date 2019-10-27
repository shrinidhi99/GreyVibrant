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
import com.example.greyvibrant.front.RecyclerViewElements.queueItem;

import java.util.ArrayList;
import java.util.List;

public class QueueFragmentAdapter extends RecyclerView.Adapter<QueueFragmentAdapter.QueueViewHolder> implements Filterable {
    static List<queueItem> mQueueItemList;

    private List<queueItem> queueListFull;
    public OnItemClickListener mListener;
    static String URL_REGIST = "https://sabios-97.000webhostapp.com/queue.php";


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    class QueueViewHolder extends RecyclerView.ViewHolder {

        private TextView mSongname;
        private String mAlbum;
        private String mGenre;
        private String mLanguage;
        private TextView mArtistname;
        private String mSongurl;
        private int mAID;
        private int mUID;
        private int mSID;


        QueueViewHolder(@NonNull final View itemView) {
            super(itemView);
            mSongname = itemView.findViewById(R.id.song_name_queue);
            mArtistname = itemView.findViewById(R.id.song_artist_queue);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        final int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                            final queueItem clickedItem = mQueueItemList.get(position);
                        }
                    }
                }
            });
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