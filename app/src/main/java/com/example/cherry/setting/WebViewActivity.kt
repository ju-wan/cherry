package com.example.cherry.setting
import android.os.Build
import com.example.cherry.R
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Button
import com.example.cherry.utils.FirebaseRef
import com.example.cherry.utils.FirebaseUtils

class WebViewActivity : AppCompatActivity() {
    private lateinit var mWebView: WebView // 웹뷰 선언
    private lateinit var mWebSettings: WebSettings // 웹뷰 세팅
    private val uid = FirebaseUtils.getUid()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        mWebView = findViewById(R.id.webView)
        mWebView.webViewClient = WebViewClient()

        mWebSettings = mWebView.settings
        mWebSettings.javaScriptEnabled = true
        mWebSettings.loadWithOverviewMode = true
        mWebSettings.useWideViewPort = true
        mWebSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        mWebSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        mWebSettings.domStorageEnabled = true

        // Intent로 전달받은 URL 로딩
        val webUrl = intent.getStringExtra("WEB_URL")
        if (!webUrl.isNullOrBlank()) {
            mWebView.loadUrl(webUrl)
        }

        val closeWebViewBtn = findViewById<Button>(R.id.closeWebViewBtn)
        closeWebViewBtn.setOnClickListener {
            // 현재 로그인한 사용자의 데이터베이스에 issuccess 값을 true로 설정
            FirebaseRef.userInfoRef.child(uid).child("issuccess").setValue(true)
            finish() // 현재 액티비티 종료
        }
    }
}