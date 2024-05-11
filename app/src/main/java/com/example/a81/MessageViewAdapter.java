package com.example.a81;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageViewAdapter extends RecyclerView.Adapter<MessageViewAdapter.ViewHolder> {
    private List<Message> messages;
    private Context context;

    // Constructor to initialize the adapter with data and context
    public MessageViewAdapter(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    // Create new views
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for the item
        View itemView = LayoutInflater.from(context).inflate(R.layout.message, parent, false);
        return new ViewHolder(itemView);
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position
        // Replace the contents of the view with that element
        Message message = messages.get(position);

        // Set the alignment of the message views based on the sender
        if (message.isUser()) {
            // If the message is from the user, align it to the right
            holder.messageNameTextView.setGravity(Gravity.END);
            holder.messageMessageTextView.setGravity(Gravity.END);
        } else {
            // If the message is from the chat, align it to the left
            holder.messageNameTextView.setGravity(Gravity.START);
            holder.messageMessageTextView.setGravity(Gravity.START);
        }
        // Set the name and message content
        holder.messageNameTextView.setText(message.getName());
        holder.messageMessageTextView.setText(message.getMessage());
    }

    // Return the size of the dataset
    @Override
    public int getItemCount() {
        return messages.size();
    }

    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView messageNameTextView;
        public TextView messageMessageTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Get references to the views
            messageNameTextView = itemView.findViewById(R.id.messageName);
            messageMessageTextView = itemView.findViewById(R.id.messageMessage);
        }
    }
}
