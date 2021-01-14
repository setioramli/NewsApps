package com.myapplication.newsapps.ui.source

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.myapplication.newsapps.R
import com.myapplication.newsapps.base.BaseActivity
import com.myapplication.newsapps.data.beans.Source
import com.myapplication.newsapps.data.model.NewsCategories
import com.myapplication.newsapps.databinding.ActivityNewsListBinding
import com.myapplication.newsapps.databinding.LayoutDialogCategoriesBinding
import com.myapplication.newsapps.util.*
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
@Suppress("TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
class NewsListActivity : BaseActivity<ActivityNewsListBinding>(),
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var presenter: NewsListPresenter
    private lateinit var dialogAdapter: CategoryDialogAdapter
    lateinit var adapter: NewsListAdapter
    private lateinit var category: String
    private var categoryList = listOf<NewsCategories>()
    var sourceList : MutableList<Source> = ArrayList()
    private var selectedCategories = 0
    private var page = 0
    var isLoadMore : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        initView()
    }

    override fun getBinding(layoutInflater: LayoutInflater): ActivityNewsListBinding {
        return ActivityNewsListBinding.inflate(layoutInflater)
    }

    private fun initPresenter() {
        presenter = NewsListPresenter(this)
        categoryList = presenter.listCategory
    }

    private fun initView() {

        if (intent.hasExtra(CATEGORY)) {
            intent.getStringExtra(CATEGORY)?.let {
                category = it
                binding.toolBarText.text = category
                binding.tvCategory.text = category
                lifecycleScope.launch { presenter.presentGetListResponse(category, page) }
            }
        }

        binding.tvCategory.apply {
            setOnClickListener { initStatusDialog() }
            text = categoryList[selectedCategories].categoriesName
            addTextChangedListener(textWatcher)
        }

        binding.btnBack.setOnClickListener {

            onBackPressed()
        }

        binding.swipeRefreshLayout.setOnRefreshListener(this)

        binding.etSearch.setOnEditorActionListener { _, p1, p2 ->
            var handled = false

            if (p1 == EditorInfo.IME_ACTION_SEND || p2.keyCode == KeyEvent.KEYCODE_ENTER) {
                isLoadMore = false
                sourceList.removeAll(setOf(null))
                lifecycleScope.launch { presenter.presentGetListResponse(binding.etSearch.text.toString(), 0) }
                handled = true
                category = binding.etSearch.text.trim().toString()
            }
            handled
        }

        presenter.sourceLiveData.observe(this, {

            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { response ->

                            if (sourceList.isEmpty()) {

                                initAdapter(response)
                            } else {
                                isLoadMore = true
                                initList(response)
                            }

                        }
                    }

                    Status.ERROR -> {
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

    override fun onRefresh() {
        lifecycleScope.launch { presenter.presentGetListResponse(category, page) }
    }

    private fun initAdapter(data: List<Source>) {

        binding.swipeRefreshLayout.stopRefreshing()
        binding.rvSource.visible()
        binding.progressBar.gone()
        adapter = NewsListAdapter(data, category)
        binding.rvSource.adapter = adapter
        initList(data)

    }

    private fun initStatusDialog() {
        val view = LayoutDialogCategoriesBinding.inflate(LayoutInflater.from(this))
        val builder = AlertDialog.Builder(this).setView(view.root)
        val alertDialog = builder.show()
        categoryList[selectedCategories].isActive = true
        dialogAdapter = CategoryDialogAdapter(categoryList) {
            categoryList[selectedCategories].isActive = false
            categoryList[it].isActive = true
            selectedCategories = it
            dialogAdapter.notifyDataSetChanged()
            binding.tvCategory.text = categoryList[selectedCategories].categoriesName
            binding.toolBarText.text = categoryList[selectedCategories].categoriesName
            alertDialog.dismiss()
        }
        view.rvStatusOrder.adapter = dialogAdapter
    }


    private fun emptyData() {
        binding.swipeRefreshLayout.stopRefreshing()
        //when is empty
        binding.rvSource.gone()
        showSnackBar(getString(R.string.no_data))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            category = categoryList[selectedCategories].categoriesName
            sourceList.clear()
            lifecycleScope.launch {
                presenter.presentGetListResponse(s.toString(), 0)
            }
        }
    }

    companion object {
        const val CATEGORY = "CATEGORY"
    }

    private fun initList(list: List<Source>) {

        isLoadMore = true
        sourceList.clear()
        sourceList.addAll(list)
        adapter.notifyDataSetChanged()
        page++
        lifecycleScope.launch { presenter.presentGetListResponse(category, page) }
    }
}