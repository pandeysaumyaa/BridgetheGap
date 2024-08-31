package com.example.bridgethegap;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;

public class messageuser extends AppCompatActivity {

    private EditText editTextSearch;
    private Button buttonInbox;
    private Button buttonCommunity;
    private LinearLayout layoutChatItems;
    // Sample chat items
    private List<String> chatItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messageuser);

        editTextSearch = findViewById(R.id.edit_text_search);
        buttonInbox = findViewById(R.id.button_inbox);
        buttonCommunity = findViewById(R.id.button_community);
        layoutChatItems = findViewById(R.id.layout_chat);
// Populate sample chat items
        chatItems.add("Atharv Shimpi");
        chatItems.add("Willow Homes");
        chatItems.add("Women's NGO");
        chatItems.add("Aaditya Mishra-Gorakpur");
        chatItems.add("Ritesh Sir");
        chatItems.add("xxxxxxx");
        chatItems.add("suyog sir");
        chatItems.add("nitin sir");
        chatItems.add("kaizbeeeee....");
        chatItems.add("Piyush Sir");
        chatItems.add("Saumya Pandey");
        chatItems.add("Ishita Jain");
// Set listeners
        buttonInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Handle Inbox button click
                Toast.makeText(messageuser.this, "", Toast.LENGTH_SHORT).show();
            }
        });
        buttonCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Handle Community button click
                Toast.makeText(messageuser.this, "Community button clicked", Toast.LENGTH_SHORT).show();
            }
        });
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
// Filter chat items based on search query
                filterChatItems(s.toString());
            }
        });
    }
    private void filterChatItems(String query) {
// Iterate through chat items
        for (int i = 0; i < chatItems.size(); i++) {
            String chatItem = chatItems.get(i);
            boolean match = chatItem.toLowerCase().contains(query.toLowerCase());
// Show or hide the chat item based on whether it matches the query
            View chatItemView = layoutChatItems.getChildAt(i);
            if (chatItemView != null) {
                chatItemView.setVisibility(match ? View.VISIBLE : View.GONE);
            } else if (match) {
// If the chat item view doesn't exist but there's a match, add it to the layout
                addChatItemToLayout(chatItem);
            }
        }
// Ensure search box remains visible
        editTextSearch.setVisibility(View.VISIBLE);
    }
    private void addChatItemToLayout(String chatItem) {
// Create a TextView for the chat item
        TextView textView = new TextView(this);
        textView.setText(chatItem);
        textView.setTextColor(Color.BLACK);
// Add some margin to the TextView
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(16, 16, 16, 0);
        textView.setLayoutParams(params);
// Add the TextView to the layout
        layoutChatItems.addView(textView);
    }


    }
