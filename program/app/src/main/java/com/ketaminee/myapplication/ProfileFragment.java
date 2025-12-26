package com.ketaminee.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private SharedPreferences prefs;

    private EditText editNickname;
    private EditText editEmail;
    private EditText editPassword;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        prefs = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        initViews(view);
        loadProfile();
        initSave(view);
        initLogout(view);

        return view;
    }

    private void initViews(View view) {
        editNickname = view.findViewById(R.id.edit_nickname);
        editEmail = view.findViewById(R.id.edit_email);
        editPassword = view.findViewById(R.id.edit_password);
    }

    private void loadProfile() {
        editNickname.setText(prefs.getString("nickname", ""));
        editEmail.setText(prefs.getString("email", ""));
        editPassword.setText("");
    }

    private void initSave(View view) {
        Button save = view.findViewById(R.id.btn_save_profile);

        save.setOnClickListener(v -> {
            prefs.edit()
                    .putString("nickname", editNickname.getText().toString())
                    .putString("email", editEmail.getText().toString())
                    .apply();

            String password = editPassword.getText().toString();
            if (!password.isEmpty()) {
                prefs.edit().putString("password", password).apply();
            }
        });
    }

    private void initLogout(View view) {
        Button logout = view.findViewById(R.id.btn_logout);
        logout.setOnClickListener(v ->
                ((MainActivity) requireActivity()).logout()
        );
    }
}
