package com.ketaminee.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private final List<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nickname, message;

        ViewHolder(View itemView) {
            super(itemView);
            nickname = itemView.findViewById(R.id.text_nickname);
            message = itemView.findViewById(R.id.text_message);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message msg = messages.get(position);
        holder.nickname.setText(msg.nickname);
        holder.message.setText(
                msg.isFile ? "Файл: " + msg.content : msg.content
        );
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}

