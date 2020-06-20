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

        return activity?.let { it ->
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

            checkBoxSunday.setOnClickListener { clickedView ->
                model.sunday.value = if ((clickedView as CheckBox).isChecked) 1 else 0
            }

            checkBoxMonday.setOnClickListener { clickedView ->
                model.monday.value = if ((clickedView as CheckBox).isChecked) 1 else 0
            }

            checkBoxTuesday.setOnClickListener { clickedView ->
                model.tuesday.value = if ((clickedView as CheckBox).isChecked) 1 else 0
            }

            checkBoxWednesday.setOnClickListener { clickedView ->
                model.wednesday.value = if ((clickedView as CheckBox).isChecked) 1 else 0
            }

            checkBoxThursday.setOnClickListener { clickedView ->
                model.thursday.value = if ((clickedView as CheckBox).isChecked) 1 else 0
            }

            checkBoxFriday.setOnClickListener { clickedView ->
                model.friday.value = if ((clickedView as CheckBox).isChecked) 1 else 0
            }

            checkBoxSaturday.setOnClickListener { clickedView ->
                model.saturday.value = if ((clickedView as CheckBox).isChecked) 1 else 0
            }

            builder.setView(view)
            builder.create()
        }
            ?: throw IllegalStateException("Activity cannot be null")

    }
}