package com.myapplication.newsapps.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapplication.newsapps.data.beans.Article
import com.myapplication.newsapps.data.response.ArticleListResponse
import com.myapplication.newsapps.network.NetworkService
import com.myapplication.newsapps.util.Constant
import com.myapplication.newsapps.util.Resource
import com.myapplication.newsapps.util.startRefreshing
import com.myapplication.newsapps.util.stopRefreshing
import kotlinx.android.synthetic.main.activity_news_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
@Suppress("TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
class NewsArticlePresenter(private val activity: NewsArticleActivity) : ViewModel(){

    private var mutableDataArticle = MutableLiveData<Resource<List<Article>>>()
    val articleLiveData: LiveData<Resource<List<Article>>> = mutableDataArticle

    suspend fun presentGetArticleList(category: String, sourceId: String, keyword: String, page: Int) {

        withContext(Dispatchers.IO){
            try {

                activity.binding.swipeRefreshLayout.startRefreshing()
                mutableDataArticle.postValue(Resource.loading())
                val getArticleListresponseCall = NetworkService.getApi.getListArticle(Constant.BEARER + "bb92d364fed7418198f6823b0c175f2d", category, sourceId, keyword, page)
                getArticleListresponseCall.enqueue(object : Callback<ArticleListResponse> {
                    override fun onResponse(
                        call: Call<ArticleListResponse>,
                        response: Response<ArticleListResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { resp ->
                                if (response.code() == 200 && resp.status.equals(Constant.SUCCESS_CODE, ignoreCase = true)) {
                                    resp.articles.let {
                                        if (it.isNullOrEmpty()) {

                                            mutableDataArticle.postValue(Resource.error(Constant.EMPTY_DATA))
                                        } else {

                                            mutableDataArticle.postValue(Resource.success(it))
                                        }
                                    }
                                } else mutableDataArticle.postValue(Resource.error("error"))
                            }
                        } else {

                            when (response.code()) {
                                400, 422, 500, 401, 403, 404 -> try {
                                    val err = response.errorBody()!!.string()
                                    val errorResp = JSONObject(err)

                                    mutableDataArticle.postValue(Resource.error( "(" + errorResp.getString("code") + ") "
                                            + errorResp.getString("message")))

                                } catch (e: Exception) {
                                    Timber.d(e)
                                }

                                426 -> try {
                                    if (activity.isLoadMore) {
                                        activity.swipeRefreshLayout.stopRefreshing()
                                        activity.isLoadMore = false
                                        activity.listArticle.removeAll(setOf(null))
                                        activity.adapter.notifyDataSetChanged()

                                    } else {
                                        mutableDataArticle.postValue(Resource.error(Constant.EMPTY_DATA))
                                    }

                                } catch (e: Exception) {
                                    Timber.d(e)
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<ArticleListResponse>, t: Throwable) {
                        Timber.d(t)
                        activity.isLoadMore = false
                        mutableDataArticle.postValue(Resource.error(t.toString()))
                    }


                })

            } catch (e: Exception) {
                Timber.d(e)
            }
        }
    }
}