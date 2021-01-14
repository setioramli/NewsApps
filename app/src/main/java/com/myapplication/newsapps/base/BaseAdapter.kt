package com.myapplication.newsapps.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapplication.newsapps.databinding.LayoutLoadingBinding

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
abstract class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var showLoading: Boolean = false

    override fun getItemCount(): Int {
        return getDataSize() + if (showLoading) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_LOADING) {
            LoadingViewHolder(
                LayoutLoadingBinding.inflate(LayoutInflater.from(parent.context))
            )

        } else onCreateNormalViewHolder(parent, viewType)
    }

    fun hideLoading() {
        showLoading = false
        //be careful with the index
        //notifyItemRemoved(itemCount)
    }

    fun showLoading() {
        showLoading = true
        //be careful with the index
        //notifyItemInserted(itemCount - 1)
    }

    override fun getItemViewType(position: Int): Int {
        if (position >= getDataSize()) {
            return VIEW_TYPE_LOADING
        }
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is LoadingViewHolder) {
            onBindNormalViewHolder(holder, position)
        }
    }

    abstract fun onBindNormalViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    abstract fun onCreateNormalViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun getDataSize(): Int

    class LoadingViewHolder(view: LayoutLoadingBinding) : RecyclerView.ViewHolder(view.root)

    companion object {
        private const val VIEW_TYPE_LOADING = 90
    }
}