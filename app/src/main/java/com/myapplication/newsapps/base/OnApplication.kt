package com.myapplication.newsapps.base

import android.app.Application
import androidx.viewbinding.BuildConfig
import com.myapplication.newsapps.util.ReleaseTree
import timber.log.Timber

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
class OnApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        app = this
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return String.format(
                        "%s, Line: %s, Method: %s",
                        super.createStackElementTag(element),
                        element.lineNumber,
                        element.methodName
                    )
                }
            })
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    companion object {
        lateinit var app: OnApplication
    }

}