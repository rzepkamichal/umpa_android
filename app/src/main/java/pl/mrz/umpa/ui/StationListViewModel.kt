package pl.mrz.umpa.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.mrz.umpa.model.StationConfig

class StationListViewModel : ViewModel() {

    val currentState = MutableLiveData<StationConfig>()

}