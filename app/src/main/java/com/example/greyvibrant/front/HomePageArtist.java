package com.example.greyvibrant.front;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.greyvibrant.R;
import com.example.greyvibrant.front.adapter.albumFragmentAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomePageArtist extends AppCompatActivity implements albumFragmentAdapter.OnItemClickListener {
    private DrawerLayout artist_home_page_dl;
    private ActionBarDrawerToggle abdt;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_artist);
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.greyvibrant.front", Context.MODE_PRIVATE);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_artist);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.artist_fragment_container, new AlbumFragment()).commit();
        artist_home_page_dl = findViewById(R.id.artist_home_page_dl);
        abdt = new ActionBarDrawerToggle(this, artist_home_page_dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        artist_home_page_dl.addDrawerListener(abdt);
        abdt.syncState();

        final NavigationView nav_view = findViewById(R.id.artist_home_page_navigation_view);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (abdt.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void ProfilePage(MenuItem item) {
        Intent intent = new Intent(HomePageArtist.this, profilePageArtist.class);
        startActivity(intent);
    }

    public void Logout(MenuItem item) {
        sharedPreferences.edit().putBoolean("isloggedin_artist", false).apply();

        Intent intent = new Intent(HomePageArtist.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sharedPreferences.edit().putBoolean("isloggedin_artist", false).apply();
        Intent intent = new Intent(HomePageArtist.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onDeleteFromAlbumClick(int position) {

    }

    @Override
    public void onDescriptionClick(int position) {

    }
}
