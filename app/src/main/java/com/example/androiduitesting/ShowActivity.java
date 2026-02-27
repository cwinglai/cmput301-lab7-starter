package com.example.androiduitesting;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        TextView cityNameTextView = findViewById(R.id.textView_cityName);
        Button backButton = findViewById(R.id.button_back);

        String cityName = getIntent().getStringExtra("CITY_NAME");
        if (cityName != null) {
            cityNameTextView.setText(cityName);
        }

        backButton.setOnClickListener(v -> finish());
    }
}
