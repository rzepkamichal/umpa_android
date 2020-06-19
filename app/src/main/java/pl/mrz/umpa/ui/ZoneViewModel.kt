package pl.mrz.umpa.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.mrz.umpa.model.ZoneConfig

class ZoneViewModel: ViewModel() {
    var id = 0
        private set

    val wateringPreset = MutableLiveData<Int>()
    val forceOpenValve = MutableLiveData<Int>()

    fun update(zoneConfig: ZoneConfig){
        id = zoneConfig.id
        wateringPreset.value = zoneConfig.wateringPreset
        forceOpenValve.value = zoneConfig.forceOpenValve
    }
}