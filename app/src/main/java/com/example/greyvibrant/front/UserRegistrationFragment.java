package com.example.greyvibrant.front;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.greyvibrant.R;

public class UserRegistrationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_user_fragment, container, false);
        Button userLogin = view.findViewById(R.id.userLogin);
        Button userSignUp = view.findViewById(R.id.userSignUp);
        EditText username = view.findViewById(R.id.userName);
        EditText userEmail = view.findViewById(R.id.userEmailID);
        EditText userPhNo = view.findViewById(R.id.userPhNo);
        EditText userPassword = view.findViewById(R.id.userPassword);
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
                Toast.makeText(getActivity(), "Sign up", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), HomePageUser.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
