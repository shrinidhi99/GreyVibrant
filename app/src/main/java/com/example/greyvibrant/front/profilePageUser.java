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
import com.example.greyvibrant.front.adapter.QueueFragmentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class profilePageUser extends AppCompatActivity {
    TextView usernametext, fullnametext, emailtext, phNotext;
    String usernamePut;
    SharedPreferences sharedPreferences;
    static String URL_REGIST = "https://sabios-97.000webhostapp.com/user_profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page_user);
        usernametext = findViewById(R.id.userName);
        emailtext = findViewById(R.id.userEmailID);
        phNotext = findViewById(R.id.userphNo);
        fullnametext = findViewById(R.id.userFullName);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);
        usernamePut = sharedPreferences.getString("username", null);
        setProfileData();
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
                                Toast.makeText(getApplicationContext(), "User Profile", Toast.LENGTH_SHORT).show();


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String username = object.getString("username");
                                    String UID = object.getString("UID");
                                    String fullname = object.getString("fullname");
                                    String email = object.getString("email");
                                    String phNo = object.getString("phNo");
                                    usernametext.setText(username);
                                    fullnametext.setText(fullname);
                                    emailtext.setText(email);
                                    phNotext.setText(phNo);
                                    Log.i("USER :", username + "  " + " " + UID);
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
                params.put("username", usernamePut);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

}
