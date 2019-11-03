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
import com.example.greyvibrant.front.dialog.UserDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class UserFragment extends Fragment {
    Button userLogin;
    View view;
    EditText username, userPassword;
    SharedPreferences sharedPreferences;
    ProgressBar spinner;
    static String URL_REGIST = "https://sabios-97.000webhostapp.com/user_login.php";
    TextView userForgotPassword;
    String newPassword, emailVerify, usernameVerify;
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_user_fragment, container, false);
        userLogin = view.findViewById(R.id.userLogin);
        Button userSignUp = view.findViewById(R.id.userSignUp);
        // EditText username = view.findViewById(R.id.userName);
        spinner = view.findViewById(R.id.progressBar);
        username = view.findViewById(R.id.userName);
        userForgotPassword = view.findViewById(R.id.userForgotPassword);
        userPassword = view.findViewById(R.id.userPassword);
        sharedPreferences = getContext().getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean("isloggedin_user", false)) {
            Intent intent = new Intent(getActivity(), HomePageUser.class);
            startActivity(intent);
        }

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().trim().isEmpty()) {
                    username.setError("Email is empty");
                    username.requestFocus();
                    return;
                } else if (userPassword.getText().toString().trim().isEmpty()) {
                    userPassword.setError("Password is empty");
                    userPassword.requestFocus();
                    return;
                }
                Login();
            }
        });
        userSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Sign up", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), RegistrationActivity.class);
                startActivity(intent);
            }
        });
        userForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        return view;
    }


    private void Login() {
        spinner.setVisibility(View.VISIBLE);
        final String usernameFinal = username.getText().toString().trim();
        final String passwordfinal = userPassword.getText().toString().trim();

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

//                                Log.i("JsonArray length b4 loop",String.valueOf(jsonArray.length()));

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String username = object.getString("username");
                                    String UID = object.getString("UID");
                                    sharedPreferences = getContext().getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);
                                    sharedPreferences.edit().putString("username", username).apply();
                                    sharedPreferences.edit().putString("UID", UID).apply();
                                    sharedPreferences.edit().putBoolean("isloggedin_user", true).apply();

                                    Log.i("USER :", username + "  " + " " + UID);
                                }

                                Toast.makeText(getContext(), "Logging in...", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                                Intent intent = new Intent(getActivity(), HomePageUser.class);
                                startActivity(intent);

                            } else {
                                spinner.setVisibility(View.GONE);

                                Toast.makeText(getContext(), "Login failed", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), "login2 Error", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", usernameFinal);
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
        editTextUsername = view.findViewById(R.id.verify_username);
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
                        String username = editTextUsername.getText().toString().trim();
                        String email = editTextEmail.getText().toString().trim();
                        String password = editTextPassword.getText().toString().trim();
                        Toast.makeText(getContext(), username + email + password, Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }

}
