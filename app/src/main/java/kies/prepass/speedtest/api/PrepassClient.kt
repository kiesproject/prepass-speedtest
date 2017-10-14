package kies.prepass.speedtest.api

import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object PrepassClient {

    fun service(factory: Converter.Factory): PrepassService {
        return Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(factory)
                .client(PrepassHttpClient.client)
                .build()
                .create(PrepassService::class.java)
    }
}
