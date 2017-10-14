package kies.prepass.speedtest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.schedulers.Schedulers
import kies.prepass.speedtest.api.PrepassClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Gsonのベンチマーク
        val gsonFactory = GsonConverterFactory.create()
        var gsonStart = 0L
        PrepassClient.service(gsonFactory)
                .getShop()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { gsonStart = System.nanoTime() }
                .subscribe(
                        { d -> Log.d("SpeedTest:gson", "finish -> ${System.nanoTime() - gsonStart}nano sec\nitem size -> ${d.size}") },
                        { t -> Log.e("SpeedTest:gson", "error $t") }
                )

        // Moshiのベンチマーク
        val moshiFactory = MoshiConverterFactory.create()
        var moshiStart = 0L
        PrepassClient.service(moshiFactory)
                .getShop()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { moshiStart = System.nanoTime() }
                .subscribe(
                        { d -> Log.d("SpeedTest:moshi", "finish -> ${System.nanoTime() - moshiStart}nano sec\nitem size -> ${d.size}") },
                        { t -> Log.e("SpeedTest:moshi", "error $t") }
                )
    }
}
