package kies.prepass.speedtest

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kies.prepass.speedtest.api.PrepassClient
import kies.prepass.speedtest.databinding.ActivityMainBinding
import kies.prepass.speedtest.models.ParseTime
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

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
        val formatNumber = NumberFormat.getNumberInstance()

        // Gsonのベンチマーク
        val gsonFactory = GsonConverterFactory.create()
        val gsonParseTime = ParseTime()
        var gsonStart = 0L
        compositeDisposable.add(PrepassClient.service(gsonFactory)
                .getShop()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { gsonStart = System.nanoTime() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { d ->
                            gsonParseTime.time = System.nanoTime() - gsonStart
                            Log.d("SpeedTest:gson", "finish -> ${gsonParseTime.time}nano sec\nitem size -> ${d.size}")
                            binding.gsonTimeText.text = "${formatNumber.format(gsonParseTime.time)}ns"
                        },
                        { t -> Log.e("SpeedTest:gson", "error $t") }
                )
        )

        // Moshiのベンチマーク
        val moshiFactory = MoshiConverterFactory.create()
        val moshiParseTime = ParseTime()
        var moshiStart = 0L
        compositeDisposable.add(PrepassClient.service(moshiFactory)
                .getShop()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { moshiStart = System.nanoTime() }
                .subscribe(
                        { d ->
                            moshiParseTime.time = System.nanoTime() - moshiStart
                            Log.d("SpeedTest:moshi", "finish -> ${moshiParseTime.time}nano sec\nitem size -> ${d.size}")
                            binding.moshiTimeText.text = "${formatNumber.format(moshiParseTime.time)}ns"
                        },
                        { t -> Log.e("SpeedTest:moshi", "error $t") }
                )
        )
    }
}
