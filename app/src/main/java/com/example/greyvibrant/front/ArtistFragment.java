package com.example.greyvibrant.front;

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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_artist_fragment, container, false);
        Button artistLogin = view.findViewById(R.id.artistLogin);
        Button artistSignUp = view.findViewById(R.id.artistSignUp);
        EditText artistName = view.findViewById(R.id.artistName);
        EditText artistEmail = view.findViewById(R.id.artistEmailID);
        EditText artistPhNo = view.findViewById(R.id.artistPhNo);
        EditText artistPassword = view.findViewById(R.id.artistPassword);
        artistLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Log in", Toast.LENGTH_SHORT).show();
            }
        });
        artistSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Sign up", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
