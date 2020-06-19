package pl.mrz.umpa.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.mrz.umpa.model.Rain
import pl.mrz.umpa.model.StationConfig

class StationViewModel: ViewModel() {

    val rainToday = MutableLiveData<Float>()
    val maxRainInDay = MutableLiveData<Float>()

    fun update(stationConfig: StationConfig, rain: Rain){
        rainToday.value = rain.rainToday
        maxRainInDay.value = stationConfig.maxRainInDay
    }

}