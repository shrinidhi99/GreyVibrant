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
import com.example.greyvibrant.front.HomeFragment;
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

public class FollowedArtistAdapter extends RecyclerView.Adapter<FollowedArtistAdapter.FollowedArtistViewHolder> implements Filterable {
    static List<followedArtistsItem> mFollowedArtistList;

    private List<followedArtistsItem> followedArtistListFull;
    public OnItemClickListener mListener;
    static String URL_REGIST = "https://sabios-97.000webhostapp.com/unfollow_artist.php";


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    class FollowedArtistViewHolder extends RecyclerView.ViewHolder {

        private TextView mArtistname;

        FollowedArtistViewHolder(@NonNull final View itemView) {
            super(itemView);
            mArtistname = itemView.findViewById(R.id.artist_name_followed);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        final int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                            final followedArtistsItem clickedItem = mFollowedArtistList.get(position);
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
                                                    String artistname = mFollowedArtistList.get(position).getmArtistname();
                                                    int AID = mFollowedArtistList.get(position).getmAID();
                                                    int UID = mFollowedArtistList.get(position).getmUID();
                                                    mFollowedArtistList.remove(position);
                                                    Log.d("Artist insertion", "Success");
                                                    UnfollowedArtistAdapter.mUnfollowedArtistList.add(new unfollowedArtistsItem(R.drawable.ic_done_black_24dp, artistname, AID, UID));
                                                    notifyDataSetChanged();
//                                                    (new UnfollowedArtistAdapter((ArrayList<unfollowedArtistsItem>) UnfollowedArtistAdapter.mUnfollowedArtistList)).notifyDataSetChanged();

                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Log.d("Artist deletion", "Failure");
                                            }

                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d("Artist deletion", "Failure 2");
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
