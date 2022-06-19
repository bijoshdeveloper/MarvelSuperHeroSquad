package com.sample.superherosquad.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sample.superherosquad.BuildConfig.PRIVATE_API_KEY
import com.sample.superherosquad.BuildConfig.PUBLIC_API_KEY
import com.sample.superherosquad.model.remote_source.model.ErrorResponse
import okhttp3.ResponseBody
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Utility functions.
 **/
object Utils {

    /**
     * Function to get MD5 hash for authorizing Marvel API call.
     **/
    fun getAPIMd5Hash(timestamp: String): String {
        val inputString = "$timestamp$PRIVATE_API_KEY$PUBLIC_API_KEY"
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1,md.digest(inputString.toByteArray())).toString(16).padStart(32,'0')
    }

    /**
     * Function to get error response from Retrofit response.
     **/
    fun getErrorBody(responseBody: ResponseBody): ErrorResponse {
        val gson = Gson()
        val type = object : TypeToken<ErrorResponse>() {}.type
        val errorResponse: ErrorResponse? = gson.fromJson(responseBody.charStream(), type)
        return errorResponse!!
    }
}