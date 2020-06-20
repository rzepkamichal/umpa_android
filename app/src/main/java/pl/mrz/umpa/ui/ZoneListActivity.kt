package pl.mrz.umpa.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import pl.mrz.umpa.R
import pl.mrz.umpa.model.StationConfig
import pl.mrz.umpa.service.ApiService
import pl.mrz.umpa.service.DelayedUpdateService
import pl.mrz.umpa.service.DisposableService
import pl.mrz.umpa.service.ToastingService
import pl.mrz.umpa.util.TwoWayData
import java.util.*


class ZoneListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: ZoneListRecyclerViewAdapter

    private val stationConfig = StationConfig()
    private val stationModel = TwoWayData(StationViewModel(), StationViewModel())
    private val zoneModels: LinkedList<TwoWayData<ZoneViewModel>> = LinkedList()

    private lateinit var rainToday: TextView
    private lateinit var maxRainTextView: TextView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var progressDialog = ProgressDialog()

    private val maxRainTextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (p0.toString().isBlank()) return
            stationModel.output.maxRainInDay.value = p0.toString().toFloat()
        }

    }

    private var canNotifyObservers = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zonelist)

        bindViews()
        initRecyclerView()
        initStationModelObservers()
        observeUpdateService()
    }

    override fun onResume() {
        super.onResume()
        canNotifyObservers = false
        progressDialog.show(supportFragmentManager, "")
        loadData()
    }

    override fun onDestroy() {
        DisposableService.clear()
        super.onDestroy()
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.stationlist_item_recycler_view)
        rainToday = findViewById(R.id.zonelist_text_rain_today)
        maxRainTextView = findViewById(R.id.zonelist_text_max_rain)
        swipeRefresh = findViewById(R.id.stationlist_swipe_refresh)

        val d: EditText

        maxRainTextView.addTextChangedListener(maxRainTextWatcher)

        swipeRefresh.setOnRefreshListener { loadData() }
    }

    private fun initRecyclerView() {
        recyclerViewAdapter = ZoneListRecyclerViewAdapter(stationConfig, zoneModels)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }


    private fun loadData() {

        ApiService.getConfiguration()
            .subscribe(
                {
                    canNotifyObservers = false
                    ApiService.getRain()
                        .doFinally {
                            progressDialog.dismiss()
                        }
                        .subscribe(
                            {
                                canNotifyObservers = false
                                stationModel.input.rainToday.value = it.rainToday
                                canNotifyObservers = true
                            },
                            { ToastingService.toastConnectionError(applicationContext) }
                        )
                        .also { DisposableService.add(it) }

                    stationConfig.update(it)
                    stationModel.input.maxRainInDay.value = it.maxRainInDay

                    if (zoneModels.isEmpty()) {
                        zoneModels.addAll(it.zones.map {
                            val zoneModel = ZoneViewModel().apply { update(it) }
                            return@map TwoWayData(zoneModel, zoneModel)
                        })

                        initZoneModelObservers()

                    } else {
                        zoneModels.forEach { oldZone ->
                            it.zones.find { zone -> zone.id == oldZone.input.id }
                                ?.let { newZone -> oldZone.input.update(newZone) }
                        }
                    }
                    recyclerViewAdapter.notifyDataSetChanged()
                    canNotifyObservers = true
                    if (swipeRefresh.isRefreshing)
                        swipeRefresh.isRefreshing = false
                },
                {
                    progressDialog.dismiss()
                    ToastingService.toastConnectionError(applicationContext)
                }
            )
            .also { DisposableService.add(it) }
    }

    private fun initStationModelObservers() {
        stationModel.input.rainToday.observe(this, Observer<Float> {
            rainToday.text = it.toString()
        })
        stationModel.input.maxRainInDay.observe(this, Observer<Float> {
            maxRainTextView.removeTextChangedListener(maxRainTextWatcher)
            maxRainTextView.text = it.toString()
            maxRainTextView.addTextChangedListener(maxRainTextWatcher)
        })
        stationModel.output.maxRainInDay.observe(this, Observer<Float> {
            notifyUpdateService()
        })

    }

    private fun initZoneModelObservers() {
        zoneModels.forEach { model ->
            model.output.forceOpenValve.observe(this, Observer {
                notifyUpdateService()
            })
            model.output.wateringPreset.observe(this, Observer {
                notifyUpdateService()
            })
        }
    }

    private fun notifyUpdateService() {
        if (!canNotifyObservers) return
        zoneModels.forEach { model ->
            stationConfig.zones.find { model.output.id == it.id }
                ?.let { stationConfig.updateWithZoneModel(model.output, model.output.id) }
        }
        stationConfig.updateWithStationModel(stationModel.output)

        DelayedUpdateService.notifyChange(stationConfig)
    }

    private fun observeUpdateService() {
        DelayedUpdateService.getResults().subscribe(
            {},
            { ToastingService.toastDataSaveFailed(applicationContext) }
        ).also { DisposableService.add(it) }

    }
}