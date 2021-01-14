package com.myapplication.newsapps.app.source

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.myapplication.newsapps.data.beans.Source
import com.myapplication.newsapps.network.ApiService
import com.myapplication.newsapps.ui.source.NewsListActivity
import com.myapplication.newsapps.ui.source.NewsListPresenter
import com.myapplication.newsapps.util.*
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Call

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
/*class NewsListPresenterTest {

    @Mock
    var apiService: ApiService? = null

    private var presenter: NewsListPresenter? = null
    private var activity: NewsListActivity? = null
    private var source: Source? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = activity?.let { NewsListPresenter(it) }

        // Mock API Call
        Mockito.`when`(
            (apiService?.getListSource("", "", 0, 0)
                ?.execute()
    }

    private fun makeGetSourceFail() {
        Mockito.`when`(apiService?.getListSource("", "", 0, 0)
            ?.thenReturn(RetrofitTestUtil.createCall(500, "Internal Error")))
    }

    @Test
    fun onStartLoadsContent() {

        activity!!.lifecycleScope.launch {

            presenter?.presentGetListResponse("", 0)
        }
        activity?.let {
            presenter?.sourceLiveData?.observe(it, {

                it?.let { resource ->
                    when(Status.SUCCESS) {
                        Status.SUCCESS -> {
                            if (resource.data.isNullOrEmpty()) {
                                activity!!.binding.rvSource.gone()
                            } else {
                                activity!!.binding.rvSource.visible()
                            }
                        }
                    }
                }
            })
        }

        Mockito.verify<Any>(activity!!.binding.swipeRefreshLayout.stopRefreshing())
    }

    @Test
    fun onStartLoadsContentFail() {
        makeGetSourceFail()
        activity!!.lifecycleScope.launch {

            presenter?.presentGetListResponse("", 0)
        }
        activity?.let {
            presenter?.sourceLiveData?.observe(it, {

                it?.let { resource ->
                    when(Status.ERROR) {
                        Status.ERROR -> {
                            activity!!.showToast(resource.message)
                        }
                    }
                }
            })
        }

        Mockito.verify<Any>(activity!!.binding.swipeRefreshLayout.stopRefreshing())
    }
}*/
