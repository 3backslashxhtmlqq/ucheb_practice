package com.ketaminee.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private SharedPreferences authPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authPrefs = getSharedPreferences("auth", MODE_PRIVATE);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(this::onNavSelected);

        updateMenuState();

        if (savedInstanceState == null) {
            if (isLoggedIn()) {
                openFragment(new ChatFragment(), false);
                bottomNavigationView.setSelectedItemId(R.id.nav_chat);
            } else {
                openFragment(new AuthFragment(), false);
                bottomNavigationView.setSelectedItemId(R.id.nav_auth);
            }
        }
    }

    private boolean isLoggedIn() {
        return authPrefs.getBoolean("isLoggedIn", false);
    }

    private void updateMenuState() {
        Menu menu = bottomNavigationView.getMenu();
        boolean loggedIn = isLoggedIn();

        menu.findItem(R.id.nav_auth).setVisible(!loggedIn);
        menu.findItem(R.id.nav_chat).setEnabled(loggedIn);
        menu.findItem(R.id.nav_settings).setEnabled(loggedIn);
    }

    private boolean onNavSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_auth && !isLoggedIn()) {
            fragment = new AuthFragment();
        } else if (id == R.id.nav_chat && isLoggedIn()) {
            fragment = new ChatFragment();
        } else if (id == R.id.nav_settings && isLoggedIn()) {
            fragment = new SettingsFragment();
        }

        if (fragment != null) {
            openFragment(fragment, false);
            return true;
        }
        return false;
    }

    private void openFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction tx = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment);

        if (addToBackStack) {
            tx.addToBackStack(null);
        }

        tx.commit();
    }

    public void onAuthSuccess() {
        authPrefs.edit().putBoolean("isLoggedIn", true).apply();
        updateMenuState();

        clearBackStack();
        bottomNavigationView.setSelectedItemId(R.id.nav_chat);
        openFragment(new ChatFragment(), false);
    }

    public void logout() {
        authPrefs.edit().putBoolean("isLoggedIn", false).apply();
        updateMenuState();

        clearBackStack();
        bottomNavigationView.setSelectedItemId(R.id.nav_auth);
        openFragment(new AuthFragment(), false);
    }

    private void clearBackStack() {
        getSupportFragmentManager()
                .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
