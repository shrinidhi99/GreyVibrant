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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class UserFragment extends Fragment {
    Button userLogin;
    EditText userEmail,userPassword;
    static  String URL_REGIST="https://sabios-97.000webhostapp.com/login_greyvibrant.php";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_user_fragment, container, false);
         userLogin = view.findViewById(R.id.userLogin);
        Button userSignUp = view.findViewById(R.id.userSignUp);
       // EditText username = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmailID);
      //  EditText userPhNo = view.findViewById(R.id.userPhNo);
        userPassword = view.findViewById(R.id.userPassword);
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Login();
            }
        });
        userSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Sign up", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    private void Login()
    {
        final String emailfinal=userEmail.getText().toString().trim();
        final String passwordfinal=userPassword.getText().toString().trim();

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
                            JSONArray jsonArray=jsonObject.getJSONArray("login");



                            if(success.equals("1"))
                            {
                                Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();
//
//                                for(int i=0;i<jsonArray.length();i++)
//                                {
//                                    JSONObject object=jsonArray.getJSONObject(i);
//                                    String username=object.getString("username");
//                                    String email=object.getString("email");
//                                    Log.i("USER :",username+"  "+email);
//                                }
                                Toast.makeText(getContext(), "Log in", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), HomePageUser.class);
                                startActivity(intent);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "login Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "login2 Error", Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String> params=new HashMap<>();
                params.put("email",emailfinal);
                params.put("password",passwordfinal);


                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}