package com.nawab.stakinghub;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.net.http.SslError;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private TextView noInternetText;

    @SuppressLint({"SetJavaScriptEnabled", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        noInternetText = findViewById(R.id.noInternetText);

        webView.getSettings().setJavaScriptEnabled(true);  // Enable JavaScript
        webView.getSettings().setDomStorageEnabled(true);  // Enable DOM storage

        // Set up WebViewClient to handle SSL errors and URL loading
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  // Ignore SSL certificate errors
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;  // Allow WebView to load the URL
            }
        });

        // Check network connectivity
        if (isConnected()) {
            webView.loadUrl("https://stakinghub.website");  // Replace with your website URL
            webView.setVisibility(View.VISIBLE);
            noInternetText.setVisibility(View.GONE);
        } else {
            webView.setVisibility(View.GONE);
            noInternetText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();  // If there's history, go back to the previous page
        } else {
            // Show confirmation dialog when user tries to exit
            showExitConfirmation();
        }
    }

    private void showExitConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();  // Exit the app
                    }
                })
                .setNegativeButton("No", null)  // Dismiss the dialog when "No" is clicked
                .show();
    }

    // Method to check if there's internet connectivity
    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
