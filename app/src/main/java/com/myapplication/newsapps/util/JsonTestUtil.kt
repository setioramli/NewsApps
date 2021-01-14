package com.myapplication.newsapps.util

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException

/**
 *
 * Created by Firwandi S Ramli on 1/14/2021.
 *
 */
object JsonTestUtil {
    private var mObjectMapper: ObjectMapper? = null
    private fun getObjectMapper(): ObjectMapper? {
        if (mObjectMapper == null) {
            mObjectMapper = ObjectMapper()
        }
        return mObjectMapper
    }

    @JsonIgnore
    @Throws(IOException::class)
    fun <M> fromString(jsonString: String?, modelClass: Class<M>?): M {
        return getObjectMapper()!!.readValue(jsonString, modelClass)
    }

    @JsonIgnore
    @Throws(JsonProcessingException::class)
    fun fromJson(`object`: Any?): String {
        return getObjectMapper()!!.writeValueAsString(`object`)
    }
}
