package pl.mrz.umpa.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.mrz.umpa.R
import pl.mrz.umpa.model.ZoneConfig
import pl.mrz.umpa.service.ApiService
import pl.mrz.umpa.service.DisposableService
import java.util.*


class StationListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: StationListRecyclerViewAdapter

    private val zones: LinkedList<MutableLiveData<ZoneConfig>> = LinkedList()
    private val todayRain = MutableLiveData<Float>()
    private val maxRain = MutableLiveData<Float>()

    private lateinit var todayRainTextVew: TextView
    private lateinit var maxRainTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stationlist)

        todayRainTextVew = findViewById(R.id.stationlist_text_rain_today)
        maxRainTextView = findViewById(R.id.stationlist_text_max_rain)
        recyclerView = findViewById(R.id.stationlist_item_recycler_view)
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        initDataObservers()
        loadData()
    }

    override fun onPause() {
        DisposableService.clear()
        super.onPause()
    }

    private fun initRecyclerView() {
        recyclerViewAdapter = StationListRecyclerViewAdapter(zones)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initDataObservers() {
        todayRain.observe(this, Observer<Float> {
            todayRainTextVew.text = """$it mm"""
        })
        maxRain.observe(this, Observer<Float> {
            maxRainTextView.text = it.toString()
        })
    }


    private fun loadData() {
        ApiService.getRain()
            .subscribe(
                {
                    todayRain.value = it.rainToday
                },
                {}
            )
            .also { DisposableService.add(it) }

        ApiService.getConfiguration()
            .subscribe(
                {
                    maxRain.value = it.maxRainInDay
                    if (zones.isEmpty()) {
                        zones.addAll(it.zones.map {
                            MutableLiveData<ZoneConfig>().apply { value = it }
                        })
                    }else {
                        zones.forEach { oldZone-> oldZone.value = it.zones.find{newZone -> oldZone.value?.id == newZone.id} }
                    }
                    recyclerViewAdapter.notifyDataSetChanged()
                },
                {}
            )
            .also { DisposableService.add(it) }


    }
}