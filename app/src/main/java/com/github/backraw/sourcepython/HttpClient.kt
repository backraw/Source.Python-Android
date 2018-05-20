package com.github.backraw.sourcepython


import okhttp3.*
import java.util.concurrent.TimeUnit


const val URL_PREFIX = "https://forums.sourcepython.com"


object HttpClient {

    // TODO: Store cookies


    private val http = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

    fun prependUrl(part: String): String {
        val newPart: String = (if (part.startsWith("./")) part.substring(2) else part)
        return "$URL_PREFIX/$newPart"
    }


    /* MUST be run in a separate thread! */
    fun get(url: String): Response {
        val request = Request.Builder()
                .url(url)
                .build()

        return http.newCall(request).execute()
    }

    /* MUST be run in a separate thread! */
    fun post(url: String, params: RequestBody): Response {
        val request = Request.Builder()
                .url(url)
                .post(params)
                .build()

        return http.newCall(request).execute()
    }

}
