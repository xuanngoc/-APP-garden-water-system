package xuanngoc.myapplication.screen.listgarden

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import xuanngoc.myapplication.model.*
import xuanngoc.myapplication.network.GardenApi


enum class GardenApiStatus { LOADING, ERROR, DONE }

class ListGardenViewModel: ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<GardenApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<GardenApiStatus>
        get() = _status

    private val _gardens = MutableLiveData<List<Garden>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val gardens: LiveData<List<Garden>>   
        get() = _gardens


    private val _plants = MutableLiveData<List<Plant>>()
    val plants: LiveData<List<Plant>>
        get() = _plants


    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getGardens()
        getPlants()

    }


    private fun getGardens() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            val getPropertiesDeferred = GardenApi.retrofitService.getListGardenAsync()
            try {
                _status.value = GardenApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _status.value = GardenApiStatus.DONE
                _gardens.value = listResult
            } catch (e: Exception) {
                _status.value = GardenApiStatus.ERROR
                _gardens.value = ArrayList()
            }
        }
    }

    private fun getPlants() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            val getPropertiesDeferred = GardenApi.retrofitService.getListPlantAsync()
            try {
                _status.value = GardenApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _status.value = GardenApiStatus.DONE
                _plants.value = listResult
            } catch (e: Exception) {
                _status.value = GardenApiStatus.ERROR
                _plants.value = ArrayList()
            }
        }
    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }



}