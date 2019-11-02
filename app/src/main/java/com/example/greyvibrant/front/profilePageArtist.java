package com.example.greyvibrant.front;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.greyvibrant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class profilePageArtist extends AppCompatActivity {
    TextView artistnametext, fullnametext, emailtext, phNotext;
    String artistnamePut;
    String AID;
    Button deleteArtist;
    SharedPreferences sharedPreferences;
    static String URL_REGIST = "https://sabios-97.000webhostapp.com/artist_profile.php";
    static String URL_REGIST2 = "https://sabios-97.000webhostapp.com/delete_artist.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page_artist);
        artistnametext = findViewById(R.id.artistName);
        emailtext = findViewById(R.id.artistEmailID);
        phNotext = findViewById(R.id.artistphNo);
        fullnametext = findViewById(R.id.artistFullName);
        deleteArtist=findViewById(R.id.deleteArtist);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);
        artistnamePut = sharedPreferences.getString("artistname", null);
        setProfileData();
        deleteArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST2,
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
                                        Log.i("ARTIST DELETION", "SUCCESS");
                                        sharedPreferences.edit().putBoolean("isloggedin_artist", false).apply();
                                        sharedPreferences.edit().putString("artistname", "").apply();
                                        sharedPreferences.edit().putString("AID", "").apply();
                                        Intent intent=new Intent(profilePageArtist.this,LoginActivity.class);
                                        startActivity(intent);

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.i("ARTIST DELETION", "ERROR");

                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("ARTIST DELETION", "ERROR 2");

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();

                        params.put("AID", String.valueOf(AID));

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
    }

    private void setProfileData() {

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
                            JSONArray jsonArray = jsonObject.getJSONArray("profile");


                            if (success.equals("1")) {
                                Toast.makeText(getApplicationContext(), "artist Profile", Toast.LENGTH_SHORT).show();


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String artistname = object.getString("artistname");
                                     AID = object.getString("AID");
                                    String fullname = object.getString("fullname");
                                    String email = object.getString("email");
                                    String phNo = object.getString("phNo");
                                    artistnametext.setText(artistname);
                                    fullnametext.setText(fullname);
                                    emailtext.setText(email);
                                    phNotext.setText(phNo);
                                    Log.i("artist :", artistname + "  " + " " + AID);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Profile Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Profile Error 2", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("artistname", artistnamePut);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}
