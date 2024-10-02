package com.nawab.stakinghub;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Handler to delay splash screen for a few seconds (e.g., 3 seconds)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start WelcomeActivity after the splash screen
                Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish(); // Finish SplashActivity so the user cannot return to it
            }
        }, 3000); // Delay of 3 seconds
    }
}
