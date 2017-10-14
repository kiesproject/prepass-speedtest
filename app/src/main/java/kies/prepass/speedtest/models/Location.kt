package kies.prepass.speedtest.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Location(

        @SerializedName("lat")
        @Expose
        @Json(name = "lat")
        var lat: Double = 0.0,

        @SerializedName("lon")
        @Expose
        @Json(name = "lon")
        var lon: Double = 0.0
)
