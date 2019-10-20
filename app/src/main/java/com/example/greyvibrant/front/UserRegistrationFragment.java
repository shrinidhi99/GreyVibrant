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
import com.example.greyvibrant.old.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserRegistrationFragment extends Fragment {
    EditText userName, userEmail, userPhNo, userPassword, userFullName;
    Button userSignUp;
    static  String URL_REGIST="https://sabios-97.000webhostapp.com/register_greyvibrant.php";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_user_fragment, container, false);
        Button userLogin = view.findViewById(R.id.userLogin);
        userSignUp = view.findViewById(R.id.userSignUp);

        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmailID);
        userPhNo = view.findViewById(R.id.userPhNo);
        userPassword = view.findViewById(R.id.userPassword);
        userFullName = view.findViewById(R.id.userFullName);
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Log in", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        userSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userName.getText().toString().trim().isEmpty()) {
                    userName.setError("User name is empty");
                    userName.requestFocus();
                    return;
                } else if (userFullName.getText().toString().trim().isEmpty()) {
                    userFullName.setError("Full name is empty");
                    userFullName.requestFocus();
                    return;
                } else if (userPassword.getText().toString().trim().isEmpty()) {
                    userPassword.setError("Password is empty");
                    userPassword.requestFocus();
                    return;
                } else if (userEmail.getText().toString().trim().isEmpty()) {
                    userEmail.setError("Email is empty");
                    userEmail.requestFocus();
                    return;
                } else if (userPhNo.getText().toString().trim().isEmpty()) {
                    userPhNo.setError("Phone number is empty");
                    userPhNo.requestFocus();
                    return;
                }

                Register();
            }
        });
        return view;
    }

    private void Register()
    {

        final String username=this.userName.getText().toString().trim();
        final String email=this.userEmail.getText().toString().trim();
        final String fullname=this.userFullName.getText().toString().trim();
        final String password=this.userName.getText().toString().trim();
        final String phNo=this.userPhNo.getText().toString().trim();
        final String playlistname=this.userName.getText().toString().trim();



        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE FROM PHP",response);
                        try {
                            if(response==null || response.equals(""))
                                Log.i("RESPONSE","IS NULL");

                            JSONObject jsonObject=new JSONObject(response);




                            String success=jsonObject.getString("success");
                            if(success.equals("1"))
                            {
                                Toast.makeText(getContext(), "Register Success", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                                Intent intent = new Intent(getActivity(), HomePageUser.class);
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
                })
        {
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String> params=new HashMap<>();

                params.put("fullname",fullname);
                params.put("phNo",phNo);
                params.put("username",username);
                params.put("email",email);
                params.put("password",password);
                params.put("playlist_name",playlistname);



                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }}
