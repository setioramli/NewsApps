package com.myapplication.newsapps.ui.article

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.myapplication.newsapps.R
import com.myapplication.newsapps.base.BaseActivity
import com.myapplication.newsapps.data.beans.Article
import com.myapplication.newsapps.databinding.ActivityNewsArticleBinding
import com.myapplication.newsapps.util.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask

class NewsArticleActivity : BaseActivity<ActivityNewsArticleBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var presenter: NewsArticlePresenter
    lateinit var adapter: NewsArticleAdapter
    private lateinit var category: String
    var listArticle: MutableList<Article> = ArrayList()
    private lateinit var sourceId: String
    private lateinit var name: String
    private var keyword = ""
    private var page = 0
    var isLoadMore:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        initView()
    }

    override fun getBinding(layoutInflater: LayoutInflater): ActivityNewsArticleBinding {
        return ActivityNewsArticleBinding.inflate(layoutInflater)
    }

    private fun initPresenter() {

        presenter = NewsArticlePresenter(this)
    }

    private fun initView() {

        keyword = ""

        if (intent.hasExtra(NAME)) {
            intent.getStringExtra(NAME)?.let {
                name = it
                binding.toolBarText.text = name
            }
        }

        if (intent.hasExtra(SOURCEID)) {
            intent.getStringExtra(SOURCEID)?.let {
                sourceId = it
            }
        }

        if (intent.hasExtra(CATEGORY)) {
            intent.getStringExtra(CATEGORY)?.let {
                category = it
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener(this)

        lifecycleScope.launch {
            presenter.presentGetArticleList(
                category,
                sourceId,
                "",
                page
            )
        }

        binding.btnBack.setOnClickListener {

            onBackPressed()
        }

        binding.etSearch.setOnEditorActionListener { _, p1, p2 ->
            var handled = false
            if (p1 == EditorInfo.IME_ACTION_SEND || p2.keyCode == KeyEvent.KEYCODE_ENTER) {
                isLoadMore = false
                listArticle.clear()
                keyword = binding.etSearch.text.trim().toString()
                lifecycleScope.launch {
                    presenter.presentGetArticleList(
                        category,
                        sourceId,
                        binding.etSearch.text.toString(),
                        0
                    )
                }
                handled = true
            }
            handled
        }

        presenter.articleLiveData.observe(this, {

            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { response ->

                            if (listArticle.isEmpty()) {

                                initAdapter(response)
                            } else {

                                initList(response)
                            }
                        }
                    }
                    Status.ERROR -> {

                        if (isLoadMore) {
                            binding.rvSource.gone()
                        }

                        if (resource.message == Constant.EMPTY_DATA) emptyData() else showToast(
                            resource.message

                        )
                    }

                    Status.LOADING -> {
                        binding.swipeRefreshLayout.startRefreshing()
                    }
                }
            }
        })
    }

    private fun initAdapter(data: List<Article>) {

        binding.swipeRefreshLayout.stopRefreshing()
        binding.rvSource.visible()
        binding.progressBar.gone()
        adapter = NewsArticleAdapter(data)
        binding.rvSource.adapter = adapter
        adapter.notifyDataSetChanged()
        initList(data)
    }

    private fun emptyData() {
        binding.swipeRefreshLayout.stopRefreshing()
        //when is empty
        showSnackBar(getString(R.string.no_data))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

    override fun onRefresh() {
        lifecycleScope.launch { presenter.presentGetArticleList(category, sourceId, keyword, page) }
    }

    companion object {
        const val CATEGORY = "CATEGORY"
        const val SOURCEID = "SOURCEID"
        const val NAME = "NAME"
    }

    private fun initList(list: List<Article>) {
        isLoadMore = true
        listArticle.clear()
        listArticle.addAll(list)
        adapter.notifyDataSetChanged()
        page++
        lifecycleScope.launch { presenter.presentGetArticleList(category, sourceId, keyword, page) }
    }
}