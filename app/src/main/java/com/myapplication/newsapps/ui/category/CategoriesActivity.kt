package com.myapplication.newsapps.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import com.myapplication.newsapps.base.BaseActivity
import com.myapplication.newsapps.data.model.NewsCategories
import com.myapplication.newsapps.databinding.ActivityListCategoriesBinding
import com.myapplication.newsapps.util.visible

class CategoriesActivity: BaseActivity<ActivityListCategoriesBinding>() {

    private lateinit var presenter: CategoriesPresenter
    private var categoryList = listOf<NewsCategories>()
    private lateinit var adapter: NewsCategoriesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        initView()
    }

    override fun getBinding(layoutInflater: LayoutInflater): ActivityListCategoriesBinding {
        return ActivityListCategoriesBinding.inflate(layoutInflater)
    }

    private fun initPresenter() {
        presenter = CategoriesPresenter()
        categoryList = presenter.listCategory
    }

    private fun initView() {

        initAdapter()
    }

    private fun initAdapter() {
        binding.rv.visible()
        adapter = NewsCategoriesAdapter(categoryList)
        binding.rv.adapter = adapter
    }


}