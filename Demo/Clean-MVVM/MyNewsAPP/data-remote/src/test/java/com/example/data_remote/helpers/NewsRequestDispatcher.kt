package com.example.data_remote.helpers

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okio.ByteString.Companion.decodeBase64
import java.lang.IllegalArgumentException
import java.net.HttpURLConnection
import java.net.URLDecoder

internal class NewsRequestDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (URLDecoder.decode(request.path, "utf-8")) {
            "/news/get?channel=头条&start=0&num=1&appkey=b0e49ab88a0537fb" -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("json/get_news.json"))
            }
            "/news/get?channel=channel_error&start=0&num=1&appkey=b0e49ab88a0537fb" -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("json/get_news_error.json"))
            }
            "/news/get?channel=connect_error&start=0&num=1&appkey=b0e49ab88a0537fb" -> {
                MockResponse()
                        .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                        .setBody(getJson("json/get_news_error.json"))
            }
            else -> throw IllegalArgumentException("Unknown Request Path ${request.path.toString()}")
        }
    }
}