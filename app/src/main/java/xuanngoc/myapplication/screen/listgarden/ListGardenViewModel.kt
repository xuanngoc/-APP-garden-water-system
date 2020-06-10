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

    private val _deviceTypes = MutableLiveData<List<DeviceType>>()
    val deviceTypes: LiveData<List<DeviceType>>
        get() = _deviceTypes

    private val _devices = MutableLiveData<List<Device>>()
    val devices: LiveData<List<Device>>
        get() = _devices

    private val _sensorTypes = MutableLiveData<List<SensorType>>()
    val sensorTypes: LiveData<List<SensorType>>
        get() = _sensorTypes

    private val _sensors = MutableLiveData<List<Sensor>>()
    val sensors: LiveData<List<Sensor>>
        get() = _sensors

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getGardens()
        getPlants()
        getDevices()
        getDeviceTypes()
        getSensorTypes()
        getSensors()
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

    }

    private fun getDeviceTypes() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            val getPropertiesDeferred = GardenApi.retrofitService.getListDeviceTypeAsync()
            try {
                _status.value = GardenApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _status.value = GardenApiStatus.DONE
                _deviceTypes.value = listResult
            } catch (e: Exception) {
                _status.value = GardenApiStatus.ERROR
                _deviceTypes.value = ArrayList()
            }
        }
    }

    private fun getDevices() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            val getPropertiesDeferred = GardenApi.retrofitService.getListDeviceAsync()
            try {
                _status.value = GardenApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _status.value = GardenApiStatus.DONE
                _devices.value = listResult
            } catch (e: Exception) {
                _status.value = GardenApiStatus.ERROR
                _devices.value = ArrayList()
            }
        }
    }

    private fun getSensorTypes() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            val getPropertiesDeferred = GardenApi.retrofitService.getListSensorTypeAsync()
            try {
                _status.value = GardenApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _status.value = GardenApiStatus.DONE
                _sensorTypes.value = listResult
            } catch (e: Exception) {
                _status.value = GardenApiStatus.ERROR
                _sensorTypes.value = ArrayList()
            }
        }
    }

    private fun getSensors() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            val getPropertiesDeferred = GardenApi.retrofitService.getListSensorAsync()
            try {
                _status.value = GardenApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _status.value = GardenApiStatus.DONE
                _sensors.value = listResult
            } catch (e: Exception) {
                _status.value = GardenApiStatus.ERROR
                _sensors.value = ArrayList()
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }



}