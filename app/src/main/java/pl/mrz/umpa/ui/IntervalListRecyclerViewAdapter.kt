package pl.mrz.umpa.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import pl.mrz.umpa.R
import pl.mrz.umpa.model.IntervalConfig

class IntervalListRecyclerViewAdapter(private val intervals: List<MutableLiveData<IntervalConfig>>) :
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

        intervals[position].observe(context!! as LifecycleOwner, Observer<IntervalConfig> {
            holder.idTextView.text = it.id.toString()
            holder.openTimeBtn.text = """${it.openValveHour}:${it.openValveMinute}"""
            holder.closeTimeBtn.text = """${it.closeValveHour}:${it.closeValveMinute}"""

            val sb = StringBuilder("")
            if(it.sunday == 1) sb.append(context!!.getString(R.string.sunday))
            if(it.monday == 1) sb.append(" " + context!!.getString(R.string.monday))
            if(it.tuesday == 1) sb.append(" " + context!!.getString(R.string.tuesday))
            if(it.wednesday == 1) sb.append(" " + context!!.getString(R.string.wednesday))
            if(it.thursday == 1) sb.append(" " + context!!.getString(R.string.thursday))
            if(it.friday == 1) sb.append(" " + context!!.getString(R.string.friday))
            if(it.saturday == 1) sb.append(" " + context!!.getString(R.string.saturday))

            holder.activeDaysBtn.text = sb.toString()
        })

        holder.openTimeBtn.setOnClickListener{}
        holder.closeTimeBtn.setOnClickListener{}
        holder.activeDaysBtn.setOnClickListener{}
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
}