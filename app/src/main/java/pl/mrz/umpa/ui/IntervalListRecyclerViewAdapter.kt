package pl.mrz.umpa.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import pl.mrz.umpa.R

class IntervalListRecyclerViewAdapter(private val intervals: List<IntervalViewModel>) :
    RecyclerView.Adapter<IntervalListRecyclerViewAdapter.ViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.intervallist_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return intervals.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        context ?: return
        if (context !is LifecycleOwner) return

        holder.idTextView.text = intervals[position].id.toString()
        initModelObservers(holder, intervals[position])

        holder.openTimeBtn.setOnClickListener {
            TimePicker(intervals[position].openValveHour, intervals[position].openValveMinute)
                .show((context!! as AppCompatActivity).supportFragmentManager, "")
        }

        holder.closeTimeBtn.setOnClickListener {
            TimePicker(intervals[position].closeValveHour, intervals[position].closeValveMinute)
                .show((context!! as AppCompatActivity).supportFragmentManager, "")
        }

        holder.activeDaysBtn.setOnClickListener {
            DaySelector(intervals[position])
                .show((context!! as AppCompatActivity).supportFragmentManager, "")
        }
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
        val idTextView: TextView = itemView.findViewById(R.id.intervallist_item_id)
        val openTimeBtn: Button = itemView.findViewById(R.id.intervallist_item_open_time_btn)
        val closeTimeBtn: Button = itemView.findViewById(R.id.intervallist_item_close_time_btn)
        val activeDaysBtn: Button = itemView.findViewById(R.id.intervallist_item_active_days_btn)
    }

    private fun initModelObservers(holder: ViewHolder, interval: IntervalViewModel) {
        interval.openValveHour.observe(context!! as LifecycleOwner, Observer {
            val minute = interval.openValveMinute.value ?: 0
            holder.openTimeBtn.text = resolveTimeString(it, minute)
        })

        interval.openValveMinute.observe(context!! as LifecycleOwner, Observer {
            val hour = interval.openValveHour.value ?: 0
            holder.openTimeBtn.text = resolveTimeString(hour, it)
        })

        interval.closeValveHour.observe(context!! as LifecycleOwner, Observer {
            val minute = interval.closeValveMinute.value ?: 0
            holder.closeTimeBtn.text = resolveTimeString(it, minute)
        })

        interval.closeValveMinute.observe(context!! as LifecycleOwner, Observer {
            val hour = interval.closeValveHour.value ?: 0
            holder.closeTimeBtn.text = resolveTimeString(hour, it)
        })

        interval.sunday.observe(context!! as LifecycleOwner, Observer {
            holder.activeDaysBtn.text = resolveDayConfigString(interval)
        })

        interval.monday.observe(context!! as LifecycleOwner, Observer {
            holder.activeDaysBtn.text = resolveDayConfigString(interval)
        })

        interval.tuesday.observe(context!! as LifecycleOwner, Observer {
            holder.activeDaysBtn.text = resolveDayConfigString(interval)
        })

        interval.wednesday.observe(context!! as LifecycleOwner, Observer {
            holder.activeDaysBtn.text = resolveDayConfigString(interval)
        })

        interval.thursday.observe(context!! as LifecycleOwner, Observer {
            holder.activeDaysBtn.text = resolveDayConfigString(interval)
        })

        interval.friday.observe(context!! as LifecycleOwner, Observer {
            holder.activeDaysBtn.text = resolveDayConfigString(interval)
        })

        interval.saturday.observe(context!! as LifecycleOwner, Observer {
            holder.activeDaysBtn.text = resolveDayConfigString(interval)
        })
    }

    private fun resolveTimeString(hour: Int, minute: Int): String {
        val sb = StringBuilder()
        if (hour < 10) sb.append("0")
        sb
            .append(hour.toString())
            .append(":")
        if (minute < 10) sb.append("0")
        sb.append(minute.toString())
        return sb.toString()
    }

    private fun resolveDayConfigString(model: IntervalViewModel): String {
        context ?: return ""

        val sunday = model.sunday.value ?: 0
        val monday = model.monday.value ?: 0
        val tuesday = model.tuesday.value ?: 0
        val wednesday = model.wednesday.value ?: 0
        val thursday = model.thursday.value ?: 0
        val friday = model.friday.value ?: 0
        val saturday = model.saturday.value ?: 0

        val sb = StringBuilder()
        if (sunday == 1)
            sb.append(context!!.getString(R.string.sunday))
        if (monday == 1)
            sb.append(" " + context!!.getString(R.string.monday))
        if (tuesday == 1)
            sb.append(" " + context!!.getString(R.string.tuesday))
        if (wednesday == 1)
            sb.append(" " + context!!.getString(R.string.wednesday))
        if (thursday == 1)
            sb.append(" " + context!!.getString(R.string.thursday))
        if (friday == 1)
            sb.append(" " + context!!.getString(R.string.friday))
        if (saturday == 1)
            sb.append(" " + context!!.getString(R.string.saturday))

        if(sb.isBlank()) sb.append("...")
        return sb.toString()
    }
}