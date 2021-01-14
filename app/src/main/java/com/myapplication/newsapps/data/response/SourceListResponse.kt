package com.myapplication.newsapps.data.response


import com.google.gson.annotations.SerializedName
import com.myapplication.newsapps.data.beans.Source

data class SourceListResponse(
    @SerializedName("sources")
    val sources: List<Source>,
    @SerializedName("status")
    val status: String
)