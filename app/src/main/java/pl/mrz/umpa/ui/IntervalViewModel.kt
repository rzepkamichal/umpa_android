package pl.mrz.umpa.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.mrz.umpa.model.IntervalConfig

class IntervalViewModel : ViewModel() {

    var id = 0
        private set

    val openValveHour = MutableLiveData<Int>()
    val openValveMinute = MutableLiveData<Int>()
    val closeValveHour = MutableLiveData<Int>()
    val closeValveMinute = MutableLiveData<Int>()
    val sunday = MutableLiveData<Int>()
    val monday = MutableLiveData<Int>()
    val tuesday = MutableLiveData<Int>()
    val wednesday = MutableLiveData<Int>()
    val thursday = MutableLiveData<Int>()
    val friday = MutableLiveData<Int>()
    val saturday = MutableLiveData<Int>()

    fun update(interval: IntervalConfig) {
        id = interval.id
        openValveHour.value = interval.openValveHour
        openValveMinute.value = interval.openValveMinute
        closeValveHour.value = interval.closeValveHour
        closeValveMinute.value = interval.openValveMinute
        sunday.value = interval.sunday
        monday.value = interval.monday
        tuesday.value = interval.tuesday
        wednesday.value = interval.wednesday
        thursday.value = interval.thursday
        friday.value = interval.friday
        saturday.value = interval.saturday
    }

}