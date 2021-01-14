package com.myapplication.newsapps.data.response


import com.google.gson.annotations.SerializedName
import com.myapplication.newsapps.data.beans.Article

data class ArticleListResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)