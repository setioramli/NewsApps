package com.myapplication.newsapps.util

import android.util.Log
import timber.log.Timber

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR || priority == Log.WARN) {
            return
        }
    }
}