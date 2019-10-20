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
    EditText userName, userEmail, userPhNo, userPassword, userFullName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_user_fragment, container, false);
        Button userLogin = view.findViewById(R.id.userLogin);
        Button userSignUp = view.findViewById(R.id.userSignUp);
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

    private void Register() {
        Toast.makeText(getActivity(), "Log in", Toast.LENGTH_SHORT).show();
    }
}
