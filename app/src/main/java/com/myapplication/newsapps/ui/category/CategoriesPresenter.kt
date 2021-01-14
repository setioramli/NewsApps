package com.myapplication.newsapps.ui.category

import com.myapplication.newsapps.R
import com.myapplication.newsapps.base.OnApplication
import com.myapplication.newsapps.data.model.NewsCategories

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
class CategoriesPresenter {

    val listCategory = listOf(
        NewsCategories(OnApplication.app.getString(R.string.general), false, R.drawable.img_general),
        NewsCategories(OnApplication.app.getString(R.string.bussiness), false, R.drawable.img_bussiness),
        NewsCategories(OnApplication.app.getString(R.string.sports), false, R.drawable.img_sport),
        NewsCategories(OnApplication.app.getString(R.string.health), false, R.drawable.img_health),
        NewsCategories(OnApplication.app.getString(R.string.technology), false, R.drawable.img_tech),
        NewsCategories(OnApplication.app.getString(R.string.science), false, R.drawable.img_science),
        NewsCategories(OnApplication.app.getString(R.string.entertainment), false, R.drawable.img_entertainment)
    )
}