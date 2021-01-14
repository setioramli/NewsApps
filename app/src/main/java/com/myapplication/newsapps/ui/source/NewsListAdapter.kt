package com.myapplication.newsapps.ui.source

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapplication.newsapps.base.BaseAdapter
import com.myapplication.newsapps.data.beans.Source
import com.myapplication.newsapps.databinding.LayoutListSourceBinding
import com.myapplication.newsapps.ui.article.NewsArticleActivity

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
class NewsListAdapter(
    private val list: List<Source>,
    private val category: String

) : BaseAdapter() {
    override fun onBindNormalViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list[position]
        (holder as CategoryListViewHolder).bind(data, category)
    }

    override fun onCreateNormalViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return CategoryListViewHolder(
            LayoutListSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getDataSize(): Int {
        return list.size
    }

    class CategoryListViewHolder(val view: LayoutListSourceBinding): RecyclerView.ViewHolder(view.root) {

        fun bind(data: Source, category: String) {

            view.tvSource.text = data.name
            view.clContentSource.setOnClickListener {

                val intent = Intent(itemView.context, NewsArticleActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra(NewsArticleActivity.SOURCEID, data.id)
                intent.putExtra(NewsArticleActivity.CATEGORY, category)
                intent.putExtra(NewsArticleActivity.NAME, data.name)
                view.root.context.startActivity(intent)
            }
        }
    }
}