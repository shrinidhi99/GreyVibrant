package com.example.greyvibrant.front;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView artistnametext,fullnametext,emailtext,phNotext;
    String artistnamePut;
    SharedPreferences sharedPreferences;
    static String URL_REGIST = "https://sabios-97.000webhostapp.com/artist_profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page_artist);
        artistnametext= findViewById(R.id.artistName);
        emailtext= findViewById(R.id.artistEmailID);
        phNotext= findViewById(R.id.artistphNo);
        fullnametext= findViewById(R.id.artistFullName);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);
        artistnamePut=sharedPreferences.getString("artistname",null);
        setProfileData();
    }

    private void setProfileData() {
//        final String artistnameFinal = artistname.getText().toString().trim();
//        final String passwordfinal = artistPassword.getText().toString().trim();

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
                                    String UID = object.getString("AID");
                                    String fullname = object.getString("fullname");
                                    String email = object.getString("email");
                                    String phNo = object.getString("phNo");
                                    artistnametext.setText("Artist name : "+artistname);
                                    fullnametext.setText("Full name : "+fullname);
                                    emailtext.setText("Email ID : "+email);
                                    phNotext.setText("Phone number : "+phNo);
                                    Log.i("artist :", artistname + "  " + " " + UID);
                                }


                                // Toast.makeText(getApplicationContext(), "Log in", Toast.LENGTH_SHORT).show();


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
