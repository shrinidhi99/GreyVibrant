package com.example.greyvibrant.front;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.greyvibrant.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomePageUser extends AppCompatActivity {
    private DrawerLayout user_home_page_dl;
    private ActionBarDrawerToggle abdt;
    SharedPreferences  sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_user);
//        sharedPreferences=getApplicationContext().getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);
//        String fullname=sharedPreferences.getString("fullname","cannot get fullname");
       // Toast.makeText(this, fullname, Toast.LENGTH_SHORT).show();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_user);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.user_fragment_container, new HomeFragment()).commit();
        user_home_page_dl = findViewById(R.id.user_home_page_dl);
        abdt = new ActionBarDrawerToggle(this, user_home_page_dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        user_home_page_dl.addDrawerListener(abdt);
        abdt.syncState();

        final NavigationView nav_view = findViewById(R.id.user_home_page_navigation_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                int id = menuItem.getItemId();
//                if (id == R.id.profile_page) {
//                    Toast.makeText(HomePageUser.this, "My profile", Toast.LENGTH_SHORT).show();
//                } else if (id == R.id.logout) {
//                    Toast.makeText(HomePageUser.this, "Log out", Toast.LENGTH_SHORT).show();
//                }
                return true;
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (abdt.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void ProfilePage(MenuItem item) {
        Intent intent = new Intent(HomePageUser.this, LoginActivity.class);
        startActivity(intent);
    }

    public void Logout(MenuItem item) {
        sharedPreferences.edit().putBoolean("isloggedin",false).apply();
        finish();
        Intent intent = new Intent(HomePageUser.this, LoginActivity.class);
        startActivity(intent);
    }
}
