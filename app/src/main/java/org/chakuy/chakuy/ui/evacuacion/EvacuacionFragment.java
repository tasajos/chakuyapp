package org.chakuy.chakuy.ui.evacuacion;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.chakuy.chakuy.databinding.FragmentEvacuacionBinding;
import org.chakuy.chakuy.R;
import org.chakuy.chakuy.ui.evacuacion.EvacuacionViewModel;

public class EvacuacionFragment extends Fragment {

    private FragmentEvacuacionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EvacuacionViewModel evacuacionViewModel =
                new ViewModelProvider(this).get(EvacuacionViewModel.class);

        binding = FragmentEvacuacionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        WebView webView = binding.wview;
        ProgressBar progressBar = binding.progressBar; // Agregamos la ProgressBar desde el layout

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Set up WebViewClient
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE); // Mostrar ProgressBar cuando comienza la carga
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE); // Ocultar ProgressBar cuando la carga finaliza
            }
        });

        // Load the URL
        webView.loadUrl("http://appyunka.chakuy.com/appd/procedimientos-de-evacuacion/");

        // Set up WebChromeClient
        webView.setWebChromeClient(new WebChromeClient());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
