package com.zaycev.vmusic.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zaycev.vmusic.R;
import com.zaycev.vmusic.config.Constants;
import com.zaycev.vmusic.entity.Account;
import com.zaycev.vmusic.utils.vksdk.api.Auth;

public class LoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Account.isLogged()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        prepareWebView();

        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

    }

    private class VkWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            parseUrl(url);
        }
    }

    private void prepareWebView() {
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);
        webView.setWebViewClient(new VkWebViewClient());
        String url = Auth.getUrl(Constants.API_ID, Auth.getSettings());
        webView.loadUrl(url);
    }

    private void parseUrl(String url) {
        try {
            if (url == null) return;
            if (url.startsWith(Auth.redirect_url)) {
                if (!url.contains("error=")) {
                    String[] auth = Auth.parseRedirectUrl(url);
                    Account.saveAccount(Long.parseLong(auth[1]), auth[0]);
                    startActivity(new Intent(this, MainActivity.class));
                }
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}