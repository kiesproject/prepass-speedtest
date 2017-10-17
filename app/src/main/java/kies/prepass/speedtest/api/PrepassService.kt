package kies.prepass.speedtest.api

import io.reactivex.Flowable
import kies.prepass.speedtest.models.Shop
import retrofit2.http.GET

interface PrepassService {
    @GET("/kiesproject/prepass-speedtest/data/shops.json")
    fun getShop(): Flowable<List<Shop>>
}
