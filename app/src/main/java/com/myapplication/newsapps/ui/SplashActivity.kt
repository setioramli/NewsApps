package com.myapplication.newsapps.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.myapplication.newsapps.base.BaseActivity
import com.myapplication.newsapps.databinding.ActivitySplashBinding
import com.myapplication.newsapps.ui.category.CategoriesActivity
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateSplash()
    }

    override fun getBinding(layoutInflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onResume() {
        super.onResume()
        navigateSplash()
    }

    private fun navigateSplash() {
        val splashDuration: Long = 3000 // 3Second
        Timer().schedule(timerTask {

                val intent = Intent(this@SplashActivity, CategoriesActivity::class.java)
                startActivity(intent)
                finish()

        }, splashDuration)
    }

}