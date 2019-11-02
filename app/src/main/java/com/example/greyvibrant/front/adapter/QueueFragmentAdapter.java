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
import com.example.greyvibrant.front.QueueFragment;
import com.example.greyvibrant.front.RecyclerViewElements.playlistItem;
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
                    switch (menuItem.getItemId()) {
                        case 1:
                            mListener.onDeleteItemClick(position);
                            Log.d("on item click remaining", "onDeleteItemClick at position: " + position);
                            return true;
                        case 2:
                            mListener.onDescriptionClick(position);
                            Log.d("on item click remaining", "onDescriptionClick at position: " + position);
                            final queueItem clickedItem3 = mQueueItemList.get(position);
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