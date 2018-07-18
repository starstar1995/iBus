package com.aki.bustool.activities;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aki.bustool.R;

public class GameActivity extends AppCompatActivity {

    private WebView webView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        String load_url = getResources().getString(R.string.web_url);
        //load_url = load_url + "?" + Math.random();
        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        webView = new WebView(this);
        this.setContentView(webView);
        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                //super.onPageFinished(view, url);
            }
        });

        WebSettings webSettings = webView.getSettings();
        //与javascript 交互
        webSettings.setJavaScriptEnabled(true);

        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        //打开本地缓存
        webSettings.setDomStorageEnabled(true);

        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setAllowFileAccess(true);

        //关闭硬件加速
        //webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        webView.clearCache(true);

        //webView.addJavascriptInterface(this,"native");
        webView.loadUrl(load_url);
    }
    /**
     *  强制竖屏
     * **/
    protected void onResume(){
        if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }
}
