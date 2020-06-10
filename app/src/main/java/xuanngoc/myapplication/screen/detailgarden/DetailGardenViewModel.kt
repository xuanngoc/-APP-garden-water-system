package xuanngoc.myapplication.screen.detailgarden

import android.view.View
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import xuanngoc.myapplication.model.*
import xuanngoc.myapplication.network.GardenApi
import xuanngoc.myapplication.screen.listgarden.GardenApiStatus

class DetailGardenViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<GardenApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<GardenApiStatus>
        get() = _status

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

    private val _avgHumility = MutableLiveData<String>()
    val avgHumility: LiveData<String>
        get() = _avgHumility

    private val _avgTemperature = MutableLiveData<String>()
    val avgTemperature: LiveData<String>
        get() = _avgTemperature

    private val _sensorHumilityValues = MutableLiveData<List<AvgSensorValue>>()
    val sensorHumilityValues: LiveData<List<AvgSensorValue>>
        get() = _sensorHumilityValues

    private val _sensorTemperatureValues = MutableLiveData<List<AvgSensorValue>>()
    val sensorTemperatureValues: LiveData<List<AvgSensorValue>>
        get() = _sensorTemperatureValues

    private val _isExpandDeviceList = MutableLiveData<Boolean>()
    val isExpandDeviceList: LiveData<Boolean>
        get() = _isExpandDeviceList

    private val _visibilityDeviceTable = MutableLiveData<Int>()
    val visibilityDeviceTable: LiveData<Int>
        get() = _visibilityDeviceTable

    private val _isExpandSensorList = MutableLiveData<Boolean>()
    val isExpandSensorList: LiveData<Boolean>
    get() = _isExpandSensorList

    private val _visibilitySensorTable = MutableLiveData<Int>()
    val visibilitySensorTable: LiveData<Int>
        get() = _visibilitySensorTable

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        _avgHumility.value = "80%"
        _avgTemperature.value = "30℃"

        _isExpandDeviceList.value = false
        _isExpandSensorList.value = false

        _visibilityDeviceTable.value = View.GONE
        _visibilitySensorTable.value = View.GONE

        getDevices()
        getDeviceTypes()
        getSensorTypes()
        getSensors()

    }

    fun clickExpandDeviceList() {
        _isExpandDeviceList.value = !_isExpandDeviceList.value!!
        if (_isExpandDeviceList.value == true) {
            _visibilityDeviceTable.value = View.VISIBLE
        } else {
            _visibilityDeviceTable.value = View.GONE
        }
    }

    fun clickExpandSensorList() {
        _isExpandSensorList.value = !_isExpandSensorList.value!!
        println("clicked")
        if (_isExpandSensorList.value == true) {
            _visibilitySensorTable.value = View.VISIBLE
        } else {
            _visibilitySensorTable.value = View.GONE
        }
    }

    fun onToggleSwitchDevice(id: Int) {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            val updateStateDeviceDeferred = GardenApi.retrofitService.updateStateDeviceAsync(id)
            try {
                // this will run on a thread managed by Retrofit
                updateStateDeviceDeferred.await()
                println("success")
            } catch (e: Exception) {
                println("fail")
            }
        }
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


    fun getListAvgSensorValueHumility(id: Int) {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            val getPropertiesDeferred = GardenApi.retrofitService.getListAvgSensorHumilityValueAsync(id)
            try {
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _sensorHumilityValues.value = listResult
            } catch (e: Exception) {
                _sensorHumilityValues.value = ArrayList()
            }
        }
    }

    fun getListAvgSensorValueTemperature(id: Int) {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            val getPropertiesDeferred = GardenApi.retrofitService.getListAvgSensorTemperatureValueAsync(id)
            try {
                // this will run on a thread managed by Retrofit
                val listResult = getPropertiesDeferred.await()
                _sensorTemperatureValues.value = listResult
            } catch (e: Exception) {
                _sensorTemperatureValues.value = ArrayList()
            }
        }
    }

}