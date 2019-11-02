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
import com.example.greyvibrant.front.RecyclerViewElements.myAlbumItem;

import java.util.ArrayList;
import java.util.List;

public class albumFragmentAdapter extends RecyclerView.Adapter<albumFragmentAdapter.AlbumViewHolder> implements Filterable, View.OnClickListener {

    static List<myAlbumItem> mAlbumList;
    public List<myAlbumItem> albumListFull;
    private OnItemClickListener mListener;


    @Override
    public void onClick(View view) {

    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteFromAlbumClick(int position);

        void onDescriptionClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private TextView mSongname;


        AlbumViewHolder(@NonNull final View itemView) {
            super(itemView);
            mSongname = itemView.findViewById(R.id.song_name);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select Action");
            MenuItem deleteFromAlbum = contextMenu.add(Menu.NONE, 1, 1, "Remove from album");
            MenuItem showDescription = contextMenu.add(Menu.NONE, 2, 2, "More information");
            deleteFromAlbum.setOnMenuItemClickListener(this);
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
                            mListener.onDeleteFromAlbumClick(position);
                            Log.d("on item click remaining", "onDeleteFromItemClick at position: " + position);
                            return true;
                        case 2:
                            mListener.onDescriptionClick(position);
                            Log.d("on item click remaining", "onDescriptionClick at position: " + position);
                            final myAlbumItem clickedItem3 = mAlbumList.get(position);
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
                    final myAlbumItem clickedItem = mAlbumList.get(position);
                }
            }
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
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }

    @Override
    public Filter getFilter() {
        return albumFilter;
    }

    private Filter albumFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<myAlbumItem> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(albumListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (myAlbumItem item : albumListFull) {
                    if (item.getmSongname().toLowerCase().contains(filterPattern)
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
            mAlbumList.clear();
            mAlbumList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

}