package pl.mrz.umpa.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.mrz.umpa.R
import pl.mrz.umpa.model.StationConfig
import pl.mrz.umpa.service.DisposableService

class MainActivity : AppCompatActivity() {


    private lateinit var stationConfig: StationConfig
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onResume() {
        super.onResume()
        startActivity(Intent(applicationContext, StationListActivity::class.java))
    }

    override fun onPause() {
        DisposableService.clear()
        super.onPause()
    }
}