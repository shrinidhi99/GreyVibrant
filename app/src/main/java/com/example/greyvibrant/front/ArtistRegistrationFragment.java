package com.example.greyvibrant.front;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.greyvibrant.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ArtistRegistrationFragment extends Fragment {
    EditText artistName, artistEmail, artistPhNo, artistPassword, artistFullName;
    static String URL_REGIST = "https://sabios-97.000webhostapp.com/artist_register.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_artist_fragment, container, false);
        Button artistLogin = view.findViewById(R.id.artistLogin);
        Button artistSignUp = view.findViewById(R.id.artistSignUp);
        artistName = view.findViewById(R.id.artistName);
        artistEmail = view.findViewById(R.id.artistEmailID);
        artistPhNo = view.findViewById(R.id.artistphNo);
        artistPassword = view.findViewById(R.id.artistPassword);
        artistFullName = view.findViewById(R.id.artistFullName);
        artistLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        artistSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (artistName.getText().toString().trim().isEmpty()) {
                    artistName.setError("Artist name is empty");
                    artistName.requestFocus();
                    return;
                } else if (artistFullName.getText().toString().trim().isEmpty()) {
                    artistFullName.setError("Full name is empty");
                    artistFullName.requestFocus();
                    return;
                } else if (artistPassword.getText().toString().trim().isEmpty()) {
                    artistPassword.setError("Password is empty");
                    artistPassword.requestFocus();
                    return;
                } else if (artistEmail.getText().toString().trim().isEmpty()) {
                    artistEmail.setError("Email is empty");
                    artistEmail.requestFocus();
                    return;
                } else if (artistPhNo.getText().toString().trim().isEmpty()) {
                    artistPhNo.setError("Phone number is empty");
                    artistPhNo.requestFocus();
                    return;
                }
                Register();
            }
        });
        return view;
    }

    private void Register() {

        final String artistname = this.artistName.getText().toString().trim();
        final String email = this.artistEmail.getText().toString().trim();
        final String fullname = this.artistFullName.getText().toString().trim();
        final String password = this.artistPassword.getText().toString().trim();
        final String phNo = this.artistPhNo.getText().toString().trim();


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
                                Toast.makeText(getContext(), "Register Success", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Register Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Register Error", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("fullname", fullname);
                params.put("phNo", phNo);
                params.put("artistname", artistname);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}
