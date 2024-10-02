package com.nawab.stakinghub;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.net.http.SslError;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @SuppressLint({"SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true); // Ensure DOM storage is enabled for websites that require it

        // WebViewClient to handle SSL errors and page navigation within WebView
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  // Ignore SSL certificate errors (optional)
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true; // Stay inside the WebView for all URL navigation
            }
        });

        // Load the URL of your website
        webView.loadUrl("https://stakinghub.website");  // Replace with your website URL
    }

    @Override
    public void onBackPressed() {
        // Check if the WebView can go back in history
        if (webView.canGoBack()) {
            webView.goBack();  // Go back to the previous page
        } else {
            // Show exit confirmation dialog
            showExitConfirmation();
        }
    }

    // Method to show the exit confirmation dialog
    private void showExitConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();  // Dismiss the dialog
                        finishAffinity();  // Close the app
                    }
                })
                .setNegativeButton("No", null)  // If "No" is clicked, dismiss the dialog
                .show();
    }

    // Optional: Check if there's internet connectivity
    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
