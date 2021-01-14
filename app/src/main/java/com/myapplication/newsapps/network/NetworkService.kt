package com.myapplication.newsapps.network

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.myapplication.newsapps.BuildConfig
import com.myapplication.newsapps.base.OnApplication.Companion.app
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
object NetworkService {

    private val getRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val client: OkHttpClient by lazy {
        val chuckerCollector =
            ChuckerCollector(app, BuildConfig.DEBUG, RetentionManager.Period.ONE_DAY)
        val chuckerInterceptor = ChuckerInterceptor(app, chuckerCollector, 250000L)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(chuckerInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    val getApi: ApiService by lazy {
        getRetrofit.create(ApiService::class.java)
    }
}