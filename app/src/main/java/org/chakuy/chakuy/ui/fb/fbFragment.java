package org.chakuy.chakuy.ui.fb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.chakuy.chakuy.databinding.FragmentFbBinding;

public class fbFragment extends Fragment {

    private FragmentFbBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFbBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set up the WebView
        WebView webView = binding.wview1;
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript if your webpage requires it

        // Load the URL
        webView.loadUrl("https://www.facebook.com/yunkabo");

        // Set up WebViewClient to handle page navigation within WebView
        webView.setWebViewClient(new WebViewClient());

        // Handle opening Facebook app or external browser
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("fb://")) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        return true;
                    } catch (Exception e) {
                        // Handle exception if the Facebook app is not installed or unable to open
                    }
                } else {
                    // Open non-Facebook URLs in external browser
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
