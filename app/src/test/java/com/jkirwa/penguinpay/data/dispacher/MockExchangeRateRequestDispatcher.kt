package com.jkirwa.penguinpay.data.dispacher

import com.google.common.io.Resources
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.File
import java.net.HttpURLConnection

class MockExchangeRateRequestDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            "/latest.json?app_id=$APP_ID" -> {
                MockResponse()
                    .setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(getJson("resources/latest.json"))
            }
            else -> throw IllegalArgumentException("Unknown Request Path ${request.path}")
        }
    }

    companion object UrlPaths {
        const val APP_ID = "api-key"
    }
}

fun getJson(path: String): String {
    val uri = Resources.getResource(path)
    val file = File(uri.path)
    return String(file.readBytes())
}