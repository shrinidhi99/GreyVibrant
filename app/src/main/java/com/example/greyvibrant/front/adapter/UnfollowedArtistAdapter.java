package com.example.greyvibrant.front.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.greyvibrant.R;
import com.example.greyvibrant.front.RecyclerViewElements.followedArtistsItem;
import com.example.greyvibrant.front.RecyclerViewElements.unfollowedArtistsItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UnfollowedArtistAdapter extends RecyclerView.Adapter<UnfollowedArtistAdapter.UnfollowedArtistViewHolder> implements Filterable {
    static List<unfollowedArtistsItem> mUnfollowedArtistList;

    private List<unfollowedArtistsItem> unfollowedArtistListFull;
    public OnItemClickListener mListener;
    static String URL_REGIST = "https://sabios-97.000webhostapp.com/follow_artist.php";

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    class UnfollowedArtistViewHolder extends RecyclerView.ViewHolder {

        private TextView mArtistname;
        private int mAID;
//        private ImageView mImage;

        UnfollowedArtistViewHolder(@NonNull final View itemView) {
            super(itemView);
            mArtistname = itemView.findViewById(R.id.artist_name_unfollowed);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        final int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                            final unfollowedArtistsItem clickedItem = mUnfollowedArtistList.get(position);
                            Log.d("onItemClick", clickedItem.getmArtistname());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.i("RESPONSE FROM PHP", response);
                                            try {
                                                if (response == null || response.equals(""))
                                                    Log.i("RESPONSE", "IS NULL");

                                                JSONObject jsonObject = new JSONObject(response);


                                                String success = jsonObject.getString("success");
                                                if (success.equals("1")) {
                                                    String artistname = mUnfollowedArtistList.get(position).getmArtistname();
                                                    int AID = mUnfollowedArtistList.get(position).getmAID();
                                                    int UID = mUnfollowedArtistList.get(position).getmUID();
                                                    mUnfollowedArtistList.remove(position);
                                                    Log.d("Artist insertion", "Success");
                                                    FollowedArtistAdapter.mFollowedArtistList.add(new followedArtistsItem(R.drawable.ic_done_black_24dp,artistname,AID,UID));
                                                    notifyDataSetChanged();
//                                                    (new FollowedArtistAdapter((ArrayList<followedArtistsItem>) FollowedArtistAdapter.mFollowedArtistList)).notifyDataSetChanged();

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Log.d("Artist insertion", "Failure");
                                            }

                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d("Artist insertion", "Failure 2");
                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();

                                    params.put("AID", String.valueOf(clickedItem.getmAID()));
                                    params.put("UID", String.valueOf(clickedItem.getmUID()));


                                    return params;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(itemView.getContext());
                            requestQueue.add(stringRequest);

                        }
                    }
                }
            });
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