package pl.mrz.umpa.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.CheckBox
import androidx.fragment.app.DialogFragment
import pl.mrz.umpa.R


class DaySelector(private val model: IntervalViewModel) : DialogFragment() {

    private lateinit var checkBoxSunday: CheckBox
    private lateinit var checkBoxMonday: CheckBox
    private lateinit var checkBoxTuesday: CheckBox
    private lateinit var checkBoxWednesday: CheckBox
    private lateinit var checkBoxThursday: CheckBox
    private lateinit var checkBoxFriday: CheckBox
    private lateinit var checkBoxSaturday: CheckBox


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = it.layoutInflater
            val view = inflater.inflate(R.layout.day_selection, null)

            checkBoxSunday = view.findViewById(R.id.day_selection_sunday)
            checkBoxMonday = view.findViewById(R.id.day_selecion_monday)
            checkBoxTuesday = view.findViewById(R.id.day_selection_tuesday)
            checkBoxWednesday = view.findViewById(R.id.day_selection_wednesday)
            checkBoxThursday = view.findViewById(R.id.day_selection_thursday)
            checkBoxFriday = view.findViewById(R.id.day_selection_friday)
            checkBoxSaturday = view.findViewById(R.id.day_selection_saturday)

            checkBoxSunday.isChecked = model.sunday.value ?: 0 == 1
            checkBoxMonday.isChecked = model.monday.value ?: 0 == 1
            checkBoxTuesday.isChecked = model.tuesday.value ?: 0 == 1
            checkBoxWednesday.isChecked = model.wednesday.value ?: 0 == 1
            checkBoxThursday.isChecked = model.thursday.value ?: 0 == 1
            checkBoxFriday.isChecked = model.friday.value ?: 0 == 1
            checkBoxSaturday.isChecked = model.saturday.value ?: 0 == 1

            checkBoxSunday.setOnCheckedChangeListener { _, isChecked ->
                model.sunday.value = if (isChecked) 1 else 0
            }

            checkBoxMonday.setOnCheckedChangeListener { _, isChecked ->
                model.monday.value = if (isChecked) 1 else 0
            }

            checkBoxTuesday.setOnCheckedChangeListener { _, isChecked ->
                model.tuesday.value = if (isChecked) 1 else 0
            }

            checkBoxWednesday.setOnCheckedChangeListener { _, isChecked ->
                model.wednesday.value = if (isChecked) 1 else 0
            }

            checkBoxThursday.setOnCheckedChangeListener { _, isChecked ->
                model.thursday.value = if (isChecked) 1 else 0
            }

            checkBoxFriday.setOnCheckedChangeListener { _, isChecked ->
                model.friday.value = if (isChecked) 1 else 0
            }

            checkBoxSaturday.setOnCheckedChangeListener { _, isChecked ->
                model.saturday.value = if (isChecked) 1 else 0
            }

            builder.setView(view)
            builder.create()
        }
            ?: throw IllegalStateException("Activity cannot be null")

    }
}