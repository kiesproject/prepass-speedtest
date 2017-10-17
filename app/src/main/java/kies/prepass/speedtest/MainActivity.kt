package kies.prepass.speedtest

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kies.prepass.speedtest.api.PrepassClient
import kies.prepass.speedtest.databinding.ActivityMainBinding
import kies.prepass.speedtest.databinding.ItemResultBinding
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.NumberFormat
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private lateinit var adapter: ResultAdapter

    val formatNumber = NumberFormat.getNumberInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ResultAdapter(this)
        binding.resultList.adapter = adapter

        binding.startBenchmark.setOnClickListener {
            runBenchmark()
            binding.stopBenchmark.visibility = View.VISIBLE
            binding.startBenchmark.visibility = View.GONE
        }

        binding.stopBenchmark.setOnClickListener { stopBenchmark() }
    }

    private fun stopBenchmark() {
        Log.d("SpeedTest:stop", "Cancel ベンチマーク")
        binding.stopBenchmark.visibility = View.VISIBLE
        binding.startBenchmark.visibility = View.GONE
        compositeDisposable.clear()
    }

    private fun runBenchmark() {
        Single.create<Int> { emitter -> emitter.onSuccess(0) }
                .subscribeOn(Schedulers.io())
                .flatMap { _ -> gsonBenchmark() }
                .flatMap { res ->
                    adapter.add(res)
                    moshiBenchmark()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { res ->
                            adapter.add(res)
                            binding.stopBenchmark.visibility = View.GONE
                            binding.startBenchmark.visibility = View.VISIBLE
                        },
                        { e -> Log.e("aa", e.toString()) }
                )
    }

    private fun gsonBenchmark(): Single<Result> {
        return Single.fromCallable {
            var t0 = System.nanoTime()
            PrepassClient.service(GsonConverterFactory.create())
                    .getShop()
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { t0 = System.nanoTime() }
                    .blockingFirst()

            Result("Gson", formatNumber.format(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t0)))
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun moshiBenchmark(): Single<Result> {
        return Single.fromCallable {
            var t0 = System.nanoTime()
            PrepassClient.service(MoshiConverterFactory.create())
                    .getShop()
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { t0 = System.nanoTime() }
                    .blockingFirst()

            Result("Moshi", formatNumber.format(TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t0)))
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    class Result(
            var name: String,
            var time: String
    )

    class ResultAdapter(context: Context) : ArrayAdapter<Result>(context, 0) {

        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val binding = ItemResultBinding.inflate(LayoutInflater.from(context), parent, false)

            val result = getItem(position)
            binding.title.text = result.name
            binding.time.text = "${result.time}ms"

            return binding.root
        }
    }
}
