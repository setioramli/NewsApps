package com.myapplication.newsapps.ui.article

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.myapplication.newsapps.base.BaseActivity
import com.myapplication.newsapps.databinding.ActivityArticleBinding
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : BaseActivity<ActivityArticleBinding>() {

    private lateinit var url: String

    override fun getBinding(layoutInflater: LayoutInflater): ActivityArticleBinding {
        return ActivityArticleBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {

        if (intent.hasExtra(URL)) {
            intent.getStringExtra(URL)?.let {
                url = it
            }
        }

        webView!!.webChromeClient = object : WebChromeClient() {

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)

                tv_toolbar.text = title
            }
        }

        webView!!.loadUrl(url)
        webView!!.settings.javaScriptEnabled = true
        webView!!.settings.allowFileAccess = true
        webView!!.settings.allowContentAccess = true

        binding.btnBack.setOnClickListener {

            finish()
        }
    }

    companion object {
        const val URL = "URL"
    }

}