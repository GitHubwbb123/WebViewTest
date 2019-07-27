package com.wxb.webviewtest;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(AndPermission.hasPermission(this,Manifest.permission.INTERNET)){
            WebView webView=(WebView)findViewById(R.id.webview);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl("https://www.baidu.com");//安卓9.0必须使用https
        }
        else {
            AndPermission.with(this)
                    .requestCode(100)
                    .permission(Manifest.permission.INTERNET)
                    .send();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AndPermission.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    @PermissionYes(100)
    private void getPermission(List<String> grantedPermission){
        WebView webView=(WebView)findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.baidu.com");//安卓9.0必须使用https
        Toast.makeText(MainActivity.this,"接受了权限",Toast.LENGTH_SHORT).show();

    }
    @PermissionNo(100)
    private void refusePermission(List<String> grantedPermission){

        Toast.makeText(MainActivity.this,"拒接了权限",Toast.LENGTH_SHORT).show();

    }


}
