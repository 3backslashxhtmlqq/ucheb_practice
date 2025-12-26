package com.ketaminee.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private SharedPreferences prefs;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE);

        initNotifications(view);
        initColorTheme(view);
        initTheme(view);
        initProfileButtons(view);
        aboutApplication(view);

        return view;
    }

    private void initNotifications(View view) {
        Switch notifications = view.findViewById(R.id.switch_notifications);
        Switch vibration = view.findViewById(R.id.switch_vibration);

        notifications.setChecked(prefs.getBoolean("notifications", true));
        vibration.setChecked(prefs.getBoolean("vibration", true));

        notifications.setOnCheckedChangeListener(
                (b, checked) -> prefs.edit().putBoolean("notifications", checked).apply()
        );

        vibration.setOnCheckedChangeListener(
                (b, checked) -> prefs.edit().putBoolean("vibration", checked).apply()
        );
    }

    private void initColorTheme(View view) {
        RadioGroup group = view.findViewById(R.id.color_group);
        String current = prefs.getString("color_theme", "green");

        switch (current) {
            case "red": group.check(R.id.color_red); break;
            case "blue": group.check(R.id.color_blue); break;
            case "yellow": group.check(R.id.color_yellow); break;
            case "purple": group.check(R.id.color_purple); break;
            default: group.check(R.id.color_green);
        }

        group.setOnCheckedChangeListener((g, id) -> {
            String selected = "green";

            if (id == R.id.color_red) selected = "red";
            else if (id == R.id.color_blue) selected = "blue";
            else if (id == R.id.color_yellow) selected = "yellow";
            else if (id == R.id.color_purple) selected = "purple";

            prefs.edit().putString("color_theme", selected).apply();
            requireActivity().recreate();
        });
    }


    private void initTheme(View view) {
        Switch darkTheme = view.findViewById(R.id.switch_dark_theme);

        boolean isDark = prefs.getBoolean("dark_theme", false);
        darkTheme.setChecked(isDark);

        darkTheme.setOnCheckedChangeListener((b, checked) -> {
            prefs.edit().putBoolean("dark_theme", checked).apply();
            AppCompatDelegate.setDefaultNightMode(
                    checked
                            ? AppCompatDelegate.MODE_NIGHT_YES
                            : AppCompatDelegate.MODE_NIGHT_NO
            );
        });
    }

    private void initProfileButtons(View view) {
        Button editProfile = view.findViewById(R.id.btn_edit_profile);

        editProfile.setOnClickListener(v ->
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new ProfileFragment())
                        .addToBackStack(null)
                        .commit()
        );
    }

    private void aboutApplication(View view) {
        Button about = view.findViewById(R.id.button2);
        about.setOnClickListener(v -> {
            Toast.makeText(getContext(), "В разработке...", Toast.LENGTH_SHORT).show();
        });
    }

}
