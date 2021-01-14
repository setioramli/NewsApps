package com.myapplication.newsapps.network

import com.myapplication.newsapps.data.response.ArticleListResponse
import com.myapplication.newsapps.data.response.SourceListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *
 * Created by Firwandi S Ramli on 11/29/2020.
 *
 */
interface ApiService {

    @GET("sources")
    fun getListSource(
        @Header("Authorization") contentType: String,
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Call<SourceListResponse>

    @GET("top-headlines")
    fun getListArticle(
        @Header("Authorization") contentType: String,
        @Query("category") category: String,
        @Query("source") source: String,
        @Query("q") keyword: String,
        @Query("page") page: Int
    ): Call<ArticleListResponse>
}