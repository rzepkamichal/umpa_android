package pl.mrz.umpa.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import pl.mrz.umpa.R
import pl.mrz.umpa.model.StationConfig
import pl.mrz.umpa.model.ZoneConfig
import pl.mrz.umpa.service.ApiService
import pl.mrz.umpa.service.DisposableService
import java.util.*


class StationListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: StationListRecyclerViewAdapter

    private val stationConfig = StationConfig()
    private val zones: LinkedList<MutableLiveData<ZoneConfig>> = LinkedList()
    private val todayRain = MutableLiveData<Float>()
    private val maxRain = MutableLiveData<Float>()

    private lateinit var todayRainTextVew: TextView
    private lateinit var maxRainTextView: TextView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stationlist)
        bindViews()
        initRecyclerView()

    }

    override fun onResume() {
        super.onResume()
        loadData()
        initDataObservers()
    }

    override fun onPause() {
        DisposableService.clear()
        super.onPause()
    }

    private fun bindViews(){
        todayRainTextVew = findViewById(R.id.stationlist_text_rain_today)
        maxRainTextView = findViewById(R.id.stationlist_text_max_rain)
        recyclerView = findViewById(R.id.stationlist_item_recycler_view)
        swipeRefresh = findViewById(R.id.stationlist_swipe_refresh)
        swipeRefresh.setOnRefreshListener { loadData() }
    }

    private fun initRecyclerView() {
        recyclerViewAdapter = StationListRecyclerViewAdapter(zones, stationConfig)
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
                    stationConfig.update(it)
                    maxRain.value = it.maxRainInDay
                    if (zones.isEmpty()) {
                        zones.addAll(it.zones.map {
                            MutableLiveData<ZoneConfig>().apply { value = it }
                        })
                    }else {
                        zones.forEach { oldZone-> oldZone.value = it.zones.find{newZone -> oldZone.value?.id == newZone.id} }
                    }
                    recyclerViewAdapter.notifyDataSetChanged()

                    if(swipeRefresh.isRefreshing)
                        swipeRefresh.isRefreshing = false
                },
                {}
            )
            .also { DisposableService.add(it) }


    }
}