package com.example.word;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

         homeFragment = new HomeFragment();
        swapFragment(homeFragment);

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.navigation_home) {
                   if (homeFragment==null) {
                       homeFragment = new HomeFragment();
                   }
                    swapFragment(homeFragment);
                    return true;
                } else if (menuItem.getItemId() == R.id.nav_profile) {
                    if (profileFragment==null) {
                        profileFragment = new ProfileFragment();
                    }
                    swapFragment(profileFragment);
                    return true;
                }
                return false;
            }
        });
    }

    private void swapFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl, fragment);
        fragmentTransaction.commit();
    }

}
