package com.myapplication.newsapps.ui.article

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.api.load
import coil.request.LoadRequest
import com.myapplication.newsapps.R
import com.myapplication.newsapps.base.BaseAdapter
import com.myapplication.newsapps.data.beans.Article
import com.myapplication.newsapps.databinding.LayoutArticleBinding
import com.myapplication.newsapps.util.DateUtil

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
class NewsArticleAdapter(
    private val list: List<Article>,
): BaseAdapter() {
    override fun onBindNormalViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list[position]
        (holder as ArticleListViewHolder).bind(data)
    }

    override fun onCreateNormalViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return ArticleListViewHolder(
            LayoutArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getDataSize(): Int {
        return list.size
    }

    class ArticleListViewHolder(val view: LayoutArticleBinding): RecyclerView.ViewHolder(view.root) {

        fun bind(data: Article) {

            val imageLoader = Coil.imageLoader(view.root.context)
            val request = LoadRequest.Builder(view.root.context)
                .data(data.urlToImage)
                .target(onStart = { }, onError = {},
                    onSuccess = { result ->
                        view.ivPoster.load(result)
                    }
                )
                .build()
            imageLoader.execute(request)

            view.tvTitle.text = data.title

            if (data.source.name.isNullOrEmpty()) {
                view.tvSource.text = view.root.context.getString(R.string.unknown_source)
            } else {
                view.tvSource.text = data.source.name
            }

            view.tvPublished.text = DateUtil.formatFromIso(data.publishedAt)

            if (data.author.isNullOrEmpty()) {
                view.tvAuthor.text = view.root.context.getString(R.string.unknown_source)
            } else {
                view.tvAuthor.text = data.author
            }

            view.clItemArticle.setOnClickListener {

                val i = Intent(itemView.context, ArticleActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                i.putExtra(ArticleActivity.URL, data.url)
                view.root.context.startActivity(i)
            }

        }
    }
}