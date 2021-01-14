package com.myapplication.newsapps.util

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun SwipeRefreshLayout.startRefreshing() {
    isRefreshing = true
}

fun SwipeRefreshLayout.stopRefreshing() {
    isRefreshing = false
}