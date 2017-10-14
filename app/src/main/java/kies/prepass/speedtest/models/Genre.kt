package kies.prepass.speedtest.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Genre(

        @SerializedName("big_genre")
        @Expose
        @Json(name = "big_genre")
        var bigGenre: String = "",

        @SerializedName("genre")
        @Expose
        @Json(name = "genre")
        var genre: String = ""
)
