package com.myapplication.newsapps.ui.category

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapplication.newsapps.base.BaseAdapter
import com.myapplication.newsapps.data.model.NewsCategories
import com.myapplication.newsapps.databinding.LayoutCategoriesBinding
import com.myapplication.newsapps.ui.source.NewsListActivity

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
class NewsCategoriesAdapter(private val list: List<NewsCategories>): BaseAdapter() {
    override fun onBindNormalViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list[position]
        (holder as NewsCategoryViewHolder).bind(data)
    }

    override fun onCreateNormalViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return NewsCategoryViewHolder(
            LayoutCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getDataSize(): Int {
        return list.size
    }

    class NewsCategoryViewHolder(private val view: LayoutCategoriesBinding) :
            RecyclerView.ViewHolder(view.root) {

        fun bind(data: NewsCategories) {

            view.iv.setImageResource(data.img)
            view.tvCategory.text = data.categoriesName
            view.clItem.setOnClickListener {

                val intent = Intent(itemView.context, NewsListActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra(NewsListActivity.CATEGORY, data.categoriesName)
                view.root.context.startActivity(intent)
            }
        }
    }
}