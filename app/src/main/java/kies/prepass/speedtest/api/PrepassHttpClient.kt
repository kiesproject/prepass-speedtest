package kies.prepass.speedtest.api

import okhttp3.OkHttpClient

object PrepassHttpClient {

    val client: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }
}
