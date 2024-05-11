package com.example.a81;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    // UI elements
    private RecyclerView chatRecyclerView;
    private TextView messageInput;
    private Button sendButton;

    // List to store messages
    private List<Message> messages = new ArrayList<Message>();

    // Adapter for RecyclerView
    MessageViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        // Initialize user
        User user = User.getInstance();

        // Add welcome message
        Message message = new Message("Chat", "Welcome, " + user.getUsername() + "!", false);
        messages.add(message);

        // Initialize UI elements
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        // Send button click listener
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get message from input field
                String message = messageInput.getText().toString().trim();
                if (message.isEmpty()) {
                    Toast.makeText(ChatActivity.this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Create user message and add to list
                Message userMessage = new Message(user.getUsername(), message, true);
                messages.add(userMessage);
                adapter.notifyItemInserted(messages.size() - 1);
                chatRecyclerView.smoothScrollToPosition(messages.size() - 1);
                // Fetch chat response
                fetchChatResponse(message);
            }
        });

        // Initialize RecyclerView
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        adapter = new MessageViewAdapter(messages, this);
        chatRecyclerView.setAdapter(adapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Method to fetch chat response from API
    private void fetchChatResponse(String message) {
        // Disable input fields while fetching response
        messageInput.setEnabled(false);
        sendButton.setEnabled(false);
        messageInput.setText("");
        // Call API for response
        ChatAPICaller.getResponse(message, new ChatAPICaller.ChatResponseListener() {
            @Override
            public void onResponseReceived(String response) {
                try {
                    // Add API response message to list
                    Message message = new Message("Chat", response, false);
                    messages.add(message);
                    adapter.notifyItemInserted(messages.size() - 1);
                    chatRecyclerView.smoothScrollToPosition(messages.size() - 1);
                    // Enable input fields after response received
                    messageInput.setEnabled(true);
                    sendButton.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResponseError(Exception e) {
                // Log error and retry fetching response
                Log.d("API", e.getMessage());
                fetchChatResponse(message);
            }
        });
    }
}
