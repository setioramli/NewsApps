package com.myapplication.newsapps.data.beans


import com.google.gson.annotations.SerializedName

data class ArticleSource(
    @SerializedName("id")
    val id: Any,
    @SerializedName("name")
    val name: String
)