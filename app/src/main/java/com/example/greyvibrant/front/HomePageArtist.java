package com.example.greyvibrant.front;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.greyvibrant.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageArtist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_artist);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_artist);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.artist_fragment_container, new AlbumFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.album:
                            selectedFragment = new AlbumFragment();
                            break;
                        case R.id.file_upload:
                            selectedFragment = new UploadFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.artist_fragment_container, selectedFragment).commit();
                    return true;
                }
            };
}
