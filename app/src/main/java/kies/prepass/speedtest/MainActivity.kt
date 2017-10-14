package kies.prepass.speedtest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kies.prepass.speedtest.api.PrepassClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_benchmark.setOnClickListener {
            runBenchmark()
            stop_benchmark.visibility = View.VISIBLE
            start_benchmark.visibility = View.GONE
        }

        stop_benchmark.setOnClickListener {
            stopBenchmark()
            stop_benchmark.visibility = View.GONE
            start_benchmark.visibility = View.VISIBLE
        }
    }

    private fun stopBenchmark() {
        Log.d("SpeedTest:stop", "Cancel ベンチマーク")
        compositeDisposable.clear()
    }

    private fun runBenchmark() {

        // Gsonのベンチマーク
        val gsonFactory = GsonConverterFactory.create()
        var gsonStart = 0L
        compositeDisposable.add(PrepassClient.service(gsonFactory)
                .getShop()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { gsonStart = System.nanoTime() }
                .subscribe(
                        { d -> Log.d("SpeedTest:gson", "finish -> ${System.nanoTime() - gsonStart}nano sec\nitem size -> ${d.size}") },
                        { t -> Log.e("SpeedTest:gson", "error $t") }
                )
        )

        // Moshiのベンチマーク
        val moshiFactory = MoshiConverterFactory.create()
        var moshiStart = 0L
        compositeDisposable.add(PrepassClient.service(moshiFactory)
                .getShop()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { moshiStart = System.nanoTime() }
                .subscribe(
                        { d -> Log.d("SpeedTest:moshi", "finish -> ${System.nanoTime() - moshiStart}nano sec\nitem size -> ${d.size}") },
                        { t -> Log.e("SpeedTest:moshi", "error $t") }
                )
        )
    }
}
