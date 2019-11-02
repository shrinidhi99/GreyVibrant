package com.example.greyvibrant.front;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.greyvibrant.R;
import com.example.greyvibrant.front.adapter.FollowedArtistAdapter;
import com.example.greyvibrant.front.adapter.PlaylistFragmentAdapter;
import com.example.greyvibrant.front.adapter.QueueFragmentAdapter;
import com.example.greyvibrant.front.adapter.RecommendedSongsAdapter;
import com.example.greyvibrant.front.adapter.RemainingSongsAdapter;
import com.example.greyvibrant.front.adapter.UnfollowedArtistAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomePageUser extends AppCompatActivity implements PlaylistFragmentAdapter.OnItemClickListener, QueueFragmentAdapter.OnItemClickListener, FollowedArtistAdapter.OnItemClickListener, UnfollowedArtistAdapter.OnItemClickListener, RecommendedSongsAdapter.OnItemClickListener, RemainingSongsAdapter.OnItemClickListener {
    private DrawerLayout user_home_page_dl;
    private ActionBarDrawerToggle abdt;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_user);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);
        QueueFragment.threadSwitch = false;
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
        Intent intent = new Intent(HomePageUser.this, profilePageUser.class);
        startActivity(intent);
    }

    public void SongsHistory(MenuItem item) {
        Intent intent = new Intent(HomePageUser.this, SongsHistory.class);
        startActivity(intent);
    }

    public void Logout(MenuItem item) {

        sharedPreferences.edit().putBoolean("isloggedin_user", false).apply();
        try {
            QueueFragment.threadSwitch = true;
            QueueFragment.mediaPlayer.stop();
            QueueFragment.mediaPlayer.release();
            QueueFragment.mediaPlayer = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(HomePageUser.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override

    public void onBackPressed() {
        super.onBackPressed();
        sharedPreferences.edit().putBoolean("isloggedin_user", false).apply();
        Intent intent = new Intent(HomePageUser.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onItemClick(int position) {


    }

    @Override
    public void onPlayClick(int position) {

    }

    @Override
    public void onDeleteFromPlaylistClick(int position) {

    }

    @Override
    public void onAddToPlaylistClick(int position) {

    }

    @Override
    public void onDescriptionClick(int position) {

    }

    @Override
    public void onDeleteItemClick(int position) {

    }

}

