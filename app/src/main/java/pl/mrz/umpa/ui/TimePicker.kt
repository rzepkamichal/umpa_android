package pl.mrz.umpa.ui

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData


class TimePicker(private val hour: MutableLiveData<Int>, private val minute: MutableLiveData<Int>) :
    DialogFragment(),
    TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val hour = this.hour.value ?: 0
        val minute = this.minute.value ?: 0
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        this.hour.value = hour
        this.minute.value = minute
    }
}