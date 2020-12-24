package com.example.mynewsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.mynewsapp.R
import com.example.mynewsapp.models.NewsPresentation

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var news: NewsPresentation

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        news = intent.extras?.get("data") as NewsPresentation

        webView = findViewById(R.id.news_detail)
        val newsContent = news.content.replace("<img", "<img style=\"max-width:100%;height:auto")
        webView.loadDataWithBaseURL(null, newsContent, "text/html" , "utf-8", null)
    }
}