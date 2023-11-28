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

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cherry.message.Message
import com.google.firebase.database.FirebaseDatabase

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.UUID

class WebViewActivity : AppCompatActivity() {
    private lateinit var mWebView: WebView // 웹뷰 선언
    private lateinit var mWebSettings: WebSettings // 웹뷰 세팅
    private val uid = FirebaseUtils.getUid()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        mWebView = findViewById(R.id.webView)
        mWebView.webViewClient = MyWebViewClient()

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
    }

    inner class MyWebViewClient : WebViewClient() {
        @SuppressLint("NewApi")
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url = request?.url.toString()
            return handleUrl(url)
        }

        @Suppress("DEPRECATION")
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return handleUrl(url)
        }

        private fun handleUrl(url: String?): Boolean {
            if (url != null && url.startsWith("https://jeongju10325.wixsite.com/my-site")) {
                // Instagram OAuth에서 리디렉션된 URL 처리
                val uri = Uri.parse(url)
                val code = uri.getQueryParameter("code")
                if (!code.isNullOrBlank()) {
                    // 사용자가 로그인에 성공한 경우
                    FirebaseRef.userInfoRef.child(uid).child("issuccess").setValue(true)
                }
                finish() // 웹뷰 종료
                Toast.makeText(this@WebViewActivity, "인스타그램 연동 성공!", Toast.LENGTH_SHORT).show()
                return true
            }
            return false
        }
    }
}