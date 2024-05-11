package com.example.a81;

import android.content.Context;
import androidx.annotation.NonNull;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class ChatAPICaller {

    // Interface to handle chat response callbacks
    public interface ChatResponseListener {
        void onResponseReceived(String response);
        void onResponseError(Exception e);
    }

    // Method to get chat response from the server
    public static void getResponse(String message, ChatResponseListener listener) {
        // Create OkHttpClient with custom timeouts
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .build();

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.3:5000/") // Base URL of the server
                .addConverterFactory(GsonConverterFactory.create()) // JSON converter
                .client(okHttpClient) // Set custom OkHttpClient
                .build();

        // Create API interface instance
        ChatRequest request = retrofit.create(ChatRequest.class);

        // Create chat message object
        ChatMessage chatMessage = new ChatMessage(message);

        // Call the API endpoint asynchronously
        Call<ChatResponse> call = request.getQuiz(chatMessage);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(@NonNull Call<ChatResponse> call, @NonNull Response<ChatResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // If response is successful, pass response message to listener
                    String responseMessage = response.body().getResponse();
                    listener.onResponseReceived(responseMessage);
                } else {
                    // If response is not successful, notify listener with error
                    listener.onResponseError(new Exception("Failed to get chat response. Response code: " + response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChatResponse> call, @NonNull Throwable t) {
                // Notify listener of failure with error message
                listener.onResponseError(new Exception("Failed to get chat response. Error: " + t.getMessage()));
            }
        });
    }

    // Retrofit interface for making API requests
    interface ChatRequest {
        @POST("chat") // POST request to "chat" endpoint
        Call<ChatResponse> getQuiz(@Body ChatMessage message); // Method to send ChatMessage object as request body
    }
}
