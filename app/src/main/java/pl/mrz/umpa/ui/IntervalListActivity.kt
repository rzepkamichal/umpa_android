package pl.mrz.umpa.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import pl.mrz.umpa.R
import pl.mrz.umpa.model.IntervalConfig
import pl.mrz.umpa.model.ZoneConfig
import java.util.*

class IntervalListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: IntervalListRecyclerViewAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var backBtn: ImageButton
    private lateinit var saveBtn: ImageButton

    private lateinit var zoneConfig: ZoneConfig
    private val intervals: LinkedList<MutableLiveData<IntervalConfig>> = LinkedList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intervallist)

        zoneConfig = intent.getSerializableExtra(getString(R.string.intent_key_zone)) as ZoneConfig
        intervals.addAll(zoneConfig.intervals.map {
            MutableLiveData<IntervalConfig>().apply { value = it }
        })

        bindViews()
        initRecyclerView()

    }

    private fun bindViews(){
        recyclerView = findViewById(R.id.intervallist_item_recycler_view)
        swipeRefresh = findViewById(R.id.intervallist_swipe_refresh)
        backBtn = findViewById(R.id.return_save_toolbar_return_btn)
        saveBtn = findViewById(R.id.return_save_toolbar_save_btn)

        backBtn.setOnClickListener{
            startActivity(Intent(this, StationListActivity::class.java))
        }
    }

    private fun initRecyclerView(){
        recyclerViewAdapter = IntervalListRecyclerViewAdapter(intervals)
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

}