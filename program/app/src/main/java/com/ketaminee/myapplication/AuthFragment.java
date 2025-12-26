package com.ketaminee.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class AuthFragment extends Fragment {

    private SharedPreferences authPrefs;
    private SharedPreferences userPrefs;

    private EditText emailInput;
    private EditText passwordInput;
    private EditText nicknameInput;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_auth, container, false);

        authPrefs = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
        userPrefs = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        emailInput = view.findViewById(R.id.edit_email);
        passwordInput = view.findViewById(R.id.edit_password);
        nicknameInput = view.findViewById(R.id.edit_nickname);

        view.findViewById(R.id.btn_login).setOnClickListener(v -> login());
        view.findViewById(R.id.btn_register).setOnClickListener(v -> register());

        return view;
    }

    private void login() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        String savedEmail = authPrefs.getString("email", null);
        String savedPassword = authPrefs.getString("password", null);

        if (email.equals(savedEmail) && password.equals(savedPassword)) {
            ((MainActivity) requireActivity()).onAuthSuccess();
        }
    }

    private void register() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String nickname = nicknameInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || nickname.isEmpty()) return;

        authPrefs.edit()
                .putString("email", email)
                .putString("password", password)
                .apply();

        userPrefs.edit()
                .putString("nickname", nickname)
                .putString("email", email)
                .apply();

        ((MainActivity) requireActivity()).onAuthSuccess();
    }
}


