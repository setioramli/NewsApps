package com.myapplication.newsapps.util

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String): Resource<T> {
            return Resource(Status.ERROR, null, message)
        }

        fun loading(): Resource<Nothing> {
            return Resource(Status.LOADING, null, null)
        }
    }
}