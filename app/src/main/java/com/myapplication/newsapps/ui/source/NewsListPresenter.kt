package com.myapplication.newsapps.ui.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.myapplication.newsapps.R
import com.myapplication.newsapps.base.OnApplication
import com.myapplication.newsapps.data.beans.Source
import com.myapplication.newsapps.data.model.NewsCategories
import com.myapplication.newsapps.data.response.SourceListResponse
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

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
@Suppress("TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
class NewsListPresenter(private val activity: NewsListActivity) {
    
    private var mutableDataSource = MutableLiveData<Resource<List<Source>>>()
    val sourceLiveData: LiveData<Resource<List<Source>>> = mutableDataSource

    suspend fun presentGetListResponse(category: String, page: Int) {

        withContext(Dispatchers.IO) {

            try {

                activity.binding.swipeRefreshLayout.startRefreshing()
                mutableDataSource.postValue(Resource.loading())
                val getSourceListResponseCall = NetworkService.getApi.getListSource(
                    Constant.BEARER + "bb92d364fed7418198f6823b0c175f2d",
                    category,
                    page,
                    20
                )
                getSourceListResponseCall.enqueue(object : Callback<SourceListResponse> {
                    override fun onResponse(
                        call: Call<SourceListResponse>,
                        response: Response<SourceListResponse>
                    ) {

                        if (response.isSuccessful) {
                            response.body()?.let { resp ->
                                if (response.code() == 200 && resp.status.equals(
                                        Constant.SUCCESS_CODE,
                                        ignoreCase = true
                                    )
                                ) {
                                    resp.sources.let {
                                        if (it.isNullOrEmpty()) {
                                            mutableDataSource.postValue(Resource.error(Constant.EMPTY_DATA))
                                        } else {
                                            mutableDataSource.postValue(Resource.success(it))
                                        }
                                    }
                                } else mutableDataSource.postValue(Resource.error("error"))
                            }
                        } else {
                            when (response.code()) {
                                400, 422, 500, 401, 403, 404 -> try {

                                    val err = response.errorBody()!!.string()
                                    val errorResp = JSONObject(err)

                                    mutableDataSource.postValue(Resource.error( "(" + errorResp.getString("code") + ") "
                                            + errorResp.getString("message")))

                                } catch (e: Exception) {
                                    Timber.d(e)
                                }

                                426 -> try {
                                    if (activity.isLoadMore) {
                                        activity.swipeRefreshLayout.stopRefreshing()
                                        activity.isLoadMore = false
                                        activity.sourceList.removeAll(setOf(null))
                                        activity.adapter.notifyDataSetChanged()

                                    } else {
                                        mutableDataSource.postValue(Resource.error(Constant.EMPTY_DATA))
                                    }

                                } catch (e: Exception) {
                                    Timber.d(e)
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<SourceListResponse>, t: Throwable) {
                        Timber.d(t)
                        mutableDataSource.postValue(Resource.error(t.toString()))
                    }


                })

            } catch (e: Exception) {
                Timber.d(e)
            }
        }
    }

    val listCategory = listOf(
        NewsCategories(
            OnApplication.app.getString(R.string.general),
            false,
            R.drawable.img_general
        ),
        NewsCategories(
            OnApplication.app.getString(R.string.bussiness),
            false,
            R.drawable.img_bussiness
        ),
        NewsCategories(OnApplication.app.getString(R.string.sports), false, R.drawable.img_sport),
        NewsCategories(OnApplication.app.getString(R.string.health), false, R.drawable.img_health),
        NewsCategories(
            OnApplication.app.getString(R.string.technology),
            false,
            R.drawable.img_tech
        ),
        NewsCategories(
            OnApplication.app.getString(R.string.science),
            false,
            R.drawable.img_science
        ),
        NewsCategories(
            OnApplication.app.getString(R.string.entertainment),
            false,
            R.drawable.img_entertainment
        )
    )

}