package com.example.greyvibrant.front;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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

public class ArtistFragment extends Fragment {
    EditText artistName, artistPassword;
    SharedPreferences sharedPreferences;
    ProgressBar spinner;

    private EditText editTextArtistname;
    private EditText editTextEmail;
    private EditText editTextPassword;

    static String URL_REGIST = "https://sabios-97.000webhostapp.com/artist_login.php";
    TextView artistForgotPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_artist_fragment, container, false);
        Button artistLogin = view.findViewById(R.id.artistLogin);
        Button artistSignUp = view.findViewById(R.id.artistSignUp);
        artistName = view.findViewById(R.id.artistName);
        artistPassword = view.findViewById(R.id.artistPassword);
        spinner = view.findViewById(R.id.progressBar);
        artistForgotPassword = view.findViewById(R.id.artistForgotPassword);
        sharedPreferences = getContext().getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean("isloggedin_artist", false)) {
            Intent intent = new Intent(getActivity(), HomePageArtist.class);
            startActivity(intent);
        }


        artistLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artistName.getText().toString().trim().isEmpty()) {
                    artistName.setError("Artist name is empty");
                    artistName.requestFocus();
                    return;
                }
                if (artistPassword.getText().toString().trim().isEmpty()) {
                    artistPassword.setError("Password is empty");
                    artistPassword.requestFocus();
                    return;
                }
                Login();
            }
        });
        artistSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Sign up", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), RegistrationActivity.class);
                startActivity(intent);
            }
        });
        artistForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        artistForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        return view;
    }

    private void Login() {
        spinner.setVisibility(View.VISIBLE);
        final String artistnameFinal = artistName.getText().toString().trim();
        final String passwordfinal = artistPassword.getText().toString().trim();

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
                            JSONArray jsonArray = jsonObject.getJSONArray("login");


                            if (success.equals("1")) {
                                spinner.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();


                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String artistname = object.getString("artistname");
                                    String AID = object.getString("AID");
                                    sharedPreferences = getContext().getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);
                                    sharedPreferences.edit().putString("artistname", artistname).apply();
                                    sharedPreferences.edit().putString("AID", AID).apply();
                                    sharedPreferences.edit().putBoolean("isloggedin_artist", true).apply();

                                    Log.i("ARTIST :", artistname + "  " + " " + AID);
                                }

                                Toast.makeText(getContext(), "Log in", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                                Intent intent = new Intent(getActivity(), HomePageArtist.class);
                                startActivity(intent);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            spinner.setVisibility(View.GONE);

                            Toast.makeText(getContext(), "login Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        spinner.setVisibility(View.GONE);

                        Toast.makeText(getContext(), "login2 Error", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("artistname", artistnameFinal);
                params.put("password", passwordfinal);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        editTextArtistname = view.findViewById(R.id.verify_username);
        editTextEmail = view.findViewById(R.id.verify_email);
        editTextPassword = view.findViewById(R.id.new_password);
        builder.setView(view)
                .setTitle("Forgot Password")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = editTextArtistname.getText().toString().trim();
                        String email = editTextEmail.getText().toString().trim();
                        String password = editTextPassword.getText().toString().trim();
                        Toast.makeText(getContext(), username + email + password, Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }

}
