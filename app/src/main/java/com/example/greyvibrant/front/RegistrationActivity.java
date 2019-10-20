package com.example.greyvibrant.front;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.greyvibrant.R;
import com.google.android.material.tabs.TabLayout;

public class RegistrationActivity extends AppCompatActivity {
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // setup the ViewPager with the sections adapter
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserRegistrationFragment(), "User");
        adapter.addFragment(new ArtistRegistrationFragment(), "Artist");
        viewPager.setAdapter(adapter);
    }
}
