package pl.mrz.umpa.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import pl.mrz.umpa.R
import pl.mrz.umpa.model.ZoneConfig

class StationListRecyclerViewAdapter(private val zones: List<MutableLiveData<ZoneConfig>>) :
    RecyclerView.Adapter<StationListRecyclerViewAdapter.ViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.stationlist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        context ?: return
        if (context !is LifecycleOwner) return

        zones[position].observe(context!! as LifecycleOwner, Observer<ZoneConfig> {
            holder.zoneIdTextView.text = it.id.toString()
            holder.presetSpinner.setSelection(it.wateringPreset)
            holder.forceOpenSwitch.isChecked = it.forceOpenValve == 1
        })

        holder.presetSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

        }

        holder.intervalConfigBtn.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return zones.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        context = null
        super.onDetachedFromRecyclerView(recyclerView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val zoneIdTextView: TextView = itemView.findViewById(R.id.stationlist_item_id)
        val presetSpinner: Spinner = itemView.findViewById(R.id.stationlist_item_spinner)
        val forceOpenSwitch: Switch = itemView.findViewById(R.id.stationlist_item_switch)
        val intervalConfigBtn: Button = itemView.findViewById(R.id.stationlist_item_btn)

        init {
            ArrayAdapter.createFromResource(
                presetSpinner.context,
                R.array.watering_presets,
                android.R.layout.simple_spinner_item
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                presetSpinner.adapter = this
                presetSpinner.setSelection(0)
            }
        }
    }
}