package com.myapplication.newsapps.util

import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
object DateUtil {

    fun formatFromIso(inputDate: String?): String {
        val parsed: Date
        var outputDate = ""
        val dateInput =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val dateOutput =
            SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.getDefault())
        dateOutput.timeZone = TimeZone.getDefault()
        dateInput.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
        try {
            parsed = dateInput.parse(inputDate!!)!!
            outputDate = dateOutput.format(parsed)
        } catch (e: ParseException) {
            Timber.d(e)
        }
        return outputDate
    }
}