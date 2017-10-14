package kies.prepass.speedtest.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Shop(

        @SerializedName("company_id")
        @Expose
        @Json(name = "company_id")
        var companyId: Int = 0,

        @SerializedName("shop_id")
        @Expose
        @Json(name = "shop_id")
        var shopId: Int = 0,

        @SerializedName("shop_name")
        @Expose
        @Json(name = "shop_name")
        var shopName: String = "",

        @SerializedName("zip_code")
        @Expose
        @Json(name = "zip_code")
        var zipCode: String = "",

        @SerializedName("address")
        @Expose
        @Json(name = "address")
        var address: String = "",

        @SerializedName("building_address")
        @Expose
        @Json(name = "building_address")
        var buildingAddress: String = "",

        @SerializedName("location")
        @Expose
        @Json(name = "location")
        var location: Location = Location(),

        @SerializedName("tel")
        @Expose
        @Json(name = "tel")
        var tel: String = "",

        @SerializedName("fax")
        @Expose
        @Json(name = "fax")
        var fax: String = "",

        @SerializedName("url")
        @Expose
        @Json(name = "url")
        var url: String = "",

        @SerializedName("open_time")
        @Expose
        @Json(name = "open_time")
        var openTime: String = "",

        @SerializedName("close_time")
        @Expose
        @Json(name = "close_time")
        var closeTime: String = "",

        @SerializedName("pr_message")
        @Expose
        @Json(name = "pr_message")
        var prMessage: String = "",

        @SerializedName("genres")
        @Expose
        @Json(name = "genres")
        var genres: List<Genre> = listOf(),

        @SerializedName("is_baby_station")
        @Expose
        @Json(name = "is_baby_station")
        var isBabyStation: Boolean = false,

        @SerializedName("is_feed_space")
        @Expose
        @Json(name = "is_feed_space")
        var isFeedSpace: Boolean = false,

        @SerializedName("is_change_diaper_space")
        @Expose
        @Json(name = "is_change_diaper_space")
        var isChangeDiaperSpace: Boolean = false,

        @SerializedName("is_microwave_oven")
        @Expose
        @Json(name = "is_microwave_oven")
        var isMicrowaveOven: Boolean = false,

        @SerializedName("can_buy_wet_tissues")
        @Expose
        @Json(name = "can_buy_wet_tissues")
        var canBuyWetTissues: Boolean = false,

        @SerializedName("is_boil_water")
        @Expose
        @Json(name = "is_boil_water")
        var isBoilWater: Boolean = false,

        @SerializedName("is_child_toilet")
        @Expose
        @Json(name = "is_child_toilet")
        var isChildToilet: Boolean = false,

        @SerializedName("is_kids_corner")
        @Expose
        @Json(name = "is_kids_corner")
        var isKidsCorner: Boolean = false,

        @SerializedName("is_lent_stroller")
        @Expose
        @Json(name = "is_lent_stroller")
        var isLentStroller: Boolean = false,

        @SerializedName("is_child_privilege")
        @Expose
        @Json(name = "is_child_privilege")
        var isChildPrivilege: Boolean = false,

        @SerializedName("is_child_menu")
        @Expose
        @Json(name = "is_child_menu")
        var isChildMenu: Boolean = false,

        @SerializedName("is_no_smoking_room")
        @Expose
        @Json(name = "is_no_smoking_room")
        var isNoSmokingRoom: Boolean = false,

        @SerializedName("is_private_room")
        @Expose
        @Json(name = "is_private_room")
        var isPrivateRoom: Boolean = false,

        @SerializedName("is_zashiki")
        @Expose
        @Json(name = "is_zashiki")
        var isZashiki: Boolean = false,

        @SerializedName("antiallergic_support")
        @Expose
        @Json(name = "antiallergic_support")
        var antiallergicSupport: String = "",

        @SerializedName("privileges")
        @Expose
        @Json(name = "privileges")
        var privileges: Privileges = Privileges(),

        @SerializedName("last_update")
        @Expose
        @Json(name = "last_update")
        var lastUpdate: String = ""
)
