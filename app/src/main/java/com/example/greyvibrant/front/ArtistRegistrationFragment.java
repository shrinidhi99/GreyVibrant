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

public class ArtistRegistrationFragment extends Fragment {
    EditText artistName, artistEmail, artistPhNo, artistPassword, artistFullName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registration_artist_fragment, container, false);
        Button artistLogin = view.findViewById(R.id.artistLogin);
        Button artistSignUp = view.findViewById(R.id.artistSignUp);
        artistName = view.findViewById(R.id.artistName);
        artistEmail = view.findViewById(R.id.artistEmailID);
        artistPhNo = view.findViewById(R.id.artistPhNo);
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
        Toast.makeText(getActivity(), "Log in", Toast.LENGTH_SHORT).show();
    }
}
