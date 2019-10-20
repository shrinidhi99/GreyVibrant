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

public class ArtistFragment extends Fragment {
    EditText artistName, artistPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_artist_fragment, container, false);
        Button artistLogin = view.findViewById(R.id.artistLogin);
        Button artistSignUp = view.findViewById(R.id.artistSignUp);
        artistName = view.findViewById(R.id.artistName);
        artistPassword = view.findViewById(R.id.artistPassword);
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
        return view;
    }

    private void Login() {

        Toast.makeText(getActivity(), "Log in", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), HomePageArtist.class);
        startActivity(intent);
    }
}
