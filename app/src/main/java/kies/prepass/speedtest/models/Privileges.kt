package kies.prepass.speedtest.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Privileges(

        @SerializedName("two_children")
        @Expose
        @Json(name = "two_children")
        var twoChildren: String = "",

        @SerializedName("three_children")
        @Expose
        @Json(name = "three_children")
        var threeChildren: String = ""
)
