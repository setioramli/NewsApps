package com.myapplication.newsapps.ui.source

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapplication.newsapps.data.model.NewsCategories
import com.myapplication.newsapps.databinding.LayoutItemCategoriesBinding

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
class CategoryDialogAdapter(private val list: List<NewsCategories>, val onClick: (Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NormalViewHolder(
            LayoutItemCategoriesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NormalViewHolder).bind(list, position)
    }

    inner class NormalViewHolder(private val view: LayoutItemCategoriesBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(list: List<NewsCategories>, position: Int) {
            val data = list[position]
            view.tvStatusName.text = data.categoriesName
            view.ivActive.visibility = if (data.isActive) View.VISIBLE else View.GONE
            view.tvStatusName.setOnClickListener { onClick(position) }
        }
    }
}