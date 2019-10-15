package com.example.greyvibrant.front;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.greyvibrant.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_user);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_user);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.user_fragment_container, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.music_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.play_list:
                            selectedFragment = new PlaylistFragment();
                            break;
                        case R.id.queue:
                            selectedFragment = new QueueFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.user_fragment_container, selectedFragment).commit();
                    return true;
                }
            };
}
