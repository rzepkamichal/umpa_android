package pl.mrz.umpa.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import pl.mrz.umpa.R
import pl.mrz.umpa.model.StationConfig
import pl.mrz.umpa.model.ZoneConfig
import pl.mrz.umpa.util.TwoWayData

class ZoneListRecyclerViewAdapter(
    private val stationConfig: StationConfig,
    private val zones: List<TwoWayData<ZoneViewModel>>
) :
    RecyclerView.Adapter<ZoneListRecyclerViewAdapter.ViewHolder>() {

    private var context: Context? = null
    private var lockSpinnerListener = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.zonelist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        context ?: return
        if (context !is LifecycleOwner) return

        holder.zoneIdTextView.text = zones[position].input.id.toString()


        zones[position].input.forceOpenValve.observe(context!! as LifecycleOwner, Observer {
            holder.forceOpenSwitch.isChecked = it == 1
        })
        zones[position].input.wateringPreset.observe(context!! as LifecycleOwner, Observer {

            //lock only if the new selection is other than the old one
            if (holder.presetSpinner.selectedItemPosition != it)
                holder.presetSpinnerLocked = true
            holder.presetSpinner.setSelection(it)


        })

        holder.presetSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                if (holder.presetSpinnerLocked) {
                    holder.presetSpinnerLocked = false
                    return
                }
                holder.presetSpinner.setSelection(0)
                zones[position].output.wateringPreset.value = 0


            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, id: Long) {
                if (holder.presetSpinnerLocked) {
                    holder.presetSpinnerLocked = false
                    return
                }
                holder.presetSpinner.setSelection(pos)
                zones[position].output.wateringPreset.value = pos

            }
        }

        holder.forceOpenSwitch.setOnClickListener {
            zones[position].output.forceOpenValve.value =
                if (holder.forceOpenSwitch.isChecked) 1 else 0
        }

        holder.intervalConfigBtn.setOnClickListener {
            stationConfig.zones.find { it.id == zones[position].input.id }?.let {
                navigateToIntervalList(it)
            }
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

        var presetSpinnerLocked = false
        val zoneIdTextView: TextView = itemView.findViewById(R.id.zonelist_item_id)
        val presetSpinner: Spinner = itemView.findViewById(R.id.zonelist_item_spinner)
        val forceOpenSwitch: Switch = itemView.findViewById(R.id.zonelist_item_switch)
        val intervalConfigBtn: Button = itemView.findViewById(R.id.zonelist_item_btn)

        init {
            ArrayAdapter.createFromResource(
                presetSpinner.context,
                R.array.watering_presets,
                android.R.layout.simple_spinner_item
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                presetSpinner.adapter = this
            }
        }
    }

    private fun navigateToIntervalList(zoneConfig: ZoneConfig) {
        context ?: return
        val intent = Intent(context!!.applicationContext, IntervalListActivity::class.java)
            .apply {
                putExtra("station", stationConfig)
                putExtra(context!!.getString(R.string.intent_key_zone), zoneConfig)
            }
        context!!.startActivity(intent)
    }
}