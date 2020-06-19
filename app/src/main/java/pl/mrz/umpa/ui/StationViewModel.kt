package pl.mrz.umpa.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StationViewModel: ViewModel() {

    val rainToday = MutableLiveData<Float>()
    val maxRainInDay = MutableLiveData<Float>()

}