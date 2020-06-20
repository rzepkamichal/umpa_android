package pl.mrz.umpa.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import pl.mrz.umpa.R
import pl.mrz.umpa.model.StationConfig
import pl.mrz.umpa.model.ZoneConfig
import pl.mrz.umpa.service.DelayedUpdateService
import pl.mrz.umpa.service.DisposableService
import java.util.*

class IntervalListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: IntervalListRecyclerViewAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var backBtn: ImageButton

    private lateinit var stationConfig: StationConfig
    private lateinit var zoneConfig: ZoneConfig
    private val models: LinkedList<IntervalViewModel> = LinkedList()

    private var canNotifyUpdateService = false
    private var backPossible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intervallist)

        stationConfig = intent.getSerializableExtra("station") as StationConfig
        zoneConfig = intent.getSerializableExtra(getString(R.string.intent_key_zone)) as ZoneConfig
        models.addAll(zoneConfig.intervals.map { IntervalViewModel().apply { update(it) } })

        bindViews()
        initRecyclerView()
        initModelObservers()
        observeUpdateService()
    }

    override fun onResume() {
        super.onResume()
        canNotifyUpdateService = true
        backPossible = true
    }

    override fun onPause() {
        DisposableService.clear()
        super.onPause()
    }

    override fun onDestroy() {
        DisposableService.clear()
        super.onDestroy()
    }

    private fun bindViews() {
        recyclerView = findViewById(R.id.intervallist_item_recycler_view)
        swipeRefresh = findViewById(R.id.intervallist_swipe_refresh)
        backBtn = findViewById(R.id.return_save_toolbar_return_btn)

        backBtn.setOnClickListener {
            if (backPossible) {startActivity(Intent(applicationContext, ZoneListActivity::class.java))
            DisposableService.clear()
            }
        }
    }

    private fun initRecyclerView() {
        recyclerViewAdapter = IntervalListRecyclerViewAdapter(models)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initModelObservers() {
        models.forEach { model ->
            model.openValveHour.observe(this, Observer { notifyUpdateService(model) })
            model.openValveMinute.observe(this, Observer { notifyUpdateService(model) })
            model.closeValveHour.observe(this, Observer { notifyUpdateService(model) })
            model.closeValveMinute.observe(this, Observer { notifyUpdateService(model) })
            model.sunday.observe(this, Observer { notifyUpdateService(model) })
            model.monday.observe(this, Observer { notifyUpdateService(model) })
            model.tuesday.observe(this, Observer { notifyUpdateService(model) })
            model.wednesday.observe(this, Observer { notifyUpdateService(model) })
            model.thursday.observe(this, Observer { notifyUpdateService(model) })
            model.friday.observe(this, Observer { notifyUpdateService(model) })
            model.saturday.observe(this, Observer { notifyUpdateService(model) })
        }
    }

    private fun notifyUpdateService(model: IntervalViewModel) {
        if (!canNotifyUpdateService) return
        stationConfig.updateWithIntervalModel(model, zoneId = zoneConfig.id, intervalId = model.id)
        backPossible = false
        DelayedUpdateService.notifyChange(stationConfig)

    }

    private fun observeUpdateService() {
        DelayedUpdateService.getResults()
            .subscribe(
                {},
                {}
            ).also { DisposableService.add(it) }
    }

}