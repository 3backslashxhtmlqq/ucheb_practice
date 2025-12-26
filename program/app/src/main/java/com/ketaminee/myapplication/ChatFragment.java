package com.ketaminee.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    private List<Message> messages = new ArrayList<>();
    private MessageAdapter adapter;
    private SharedPreferences userPrefs;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        userPrefs = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String nickname = userPrefs.getString("nickname", "User");

        RecyclerView recycler = view.findViewById(R.id.recycler_messages);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new MessageAdapter(messages);
        recycler.setAdapter(adapter);

        EditText input = view.findViewById(R.id.edit_message);
        ImageButton send = view.findViewById(R.id.btn_send);
        ImageButton file = view.findViewById(R.id.btn_file);

        send.setOnClickListener(v -> {
            String text = input.getText().toString().trim();
            if (!text.isEmpty()) {
                addMessage(new Message(nickname, text, false));
                input.setText("");
                recycler.scrollToPosition(messages.size() - 1);
            }
        });

        file.setOnClickListener(v -> {
            addMessage(new Message(nickname, "example_file.txt", true));
            recycler.scrollToPosition(messages.size() - 1);
        });

        return view;
    }

    private void addMessage(Message message) {
        messages.add(message);
        adapter.notifyItemInserted(messages.size() - 1);
    }
}
