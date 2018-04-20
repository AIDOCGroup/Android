package me.aidoc.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import cn.miao.lib.MiaoApplication;

/**
 * 网页购买
 *
 * @created 2016_3-25
 * @author shule
 */
public class DeviceBuyActivity extends Activity implements View.OnClickListener {
    private WebView webview;
    private Button next_button;
    private String buy_url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_view);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webview.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webview.pauseTimers();
    }


    public void initView() {
        Intent in = getIntent();
        in.setExtrasClassLoader(MiaoApplication.getMiaoHealthManager().getClass().getClassLoader());
        if (in.hasExtra("buy_url")) {
            buy_url = in.getStringExtra("buy_url");
        }

        next_button = (Button) findViewById(R.id.next_button);
        next_button.setOnClickListener(this);

        webview = (WebView) findViewById(R.id.webview);
        webview.setInitialScale(25);
        String ua = webview.getSettings().getUserAgentString();
        webview.getSettings().setUserAgentString("TestUserAgent");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.requestFocus();
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setDomStorageEnabled(true);
        String dir = webview.getContext().getDir("database", this.MODE_PRIVATE).getPath();
        webview.getSettings().setDatabasePath(dir);
//        webview.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        webview.setWebViewClient(new MyWebViewClient());
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setDefaultTextEncodingName("UTF-8");
        webview.loadUrl(buy_url);
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }

    @Override
    public void onClick(View v) {
    }


    final class MyWebViewClient extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //TLog.e("test", "url Override =====" + url + "  time: " + sDateFormat.format(new java.util.Date()));
            view.loadUrl(url);
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }

        // 页面出错
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Toast.makeText(DeviceBuyActivity.this, "页面出错了", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webview != null && webview.canGoBack()) {
                webview.goBack(); // goBack()表示返回WebView的上一页面
                return true;
            }else {
                this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    final class InJavaScriptLocalObj {
        public void showSource(String html){
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webview != null) {
            webview.destroy();
        }
    }


}