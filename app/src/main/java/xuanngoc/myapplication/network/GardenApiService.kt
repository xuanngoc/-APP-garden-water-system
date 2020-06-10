package xuanngoc.myapplication.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory.*
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import xuanngoc.myapplication.model.*


private const val BASE_URL = "https://water-garden.herokuapp.com//api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object pointing to the desired URL
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


interface GardenApiService {

    @GET("garden/list")
    fun getListGardenAsync(): Deferred<List<Garden>>

    @GET("device-type/list")
    fun getListDeviceTypeAsync(): Deferred<List<DeviceType>>

    @GET("device/list")
    fun getListDeviceAsync(): Deferred<List<Device>>

    @GET("plant/list")
    fun getListPlantAsync(): Deferred<List<Plant>>

    @GET("sensor-type/list")
    fun getListSensorTypeAsync(): Deferred<List<SensorType>>

    @GET("sensor/list")
    fun getListSensorAsync(): Deferred<List<Sensor>>

    @POST("device/update/state/{deviceId}")
    fun updateStateDeviceAsync(@Path("deviceId") id: Int ): Deferred<Unit>

    @GET("sensor-value/humility/{gardenId}")
    fun getListAvgSensorHumilityValueAsync(@Path("gardenId") id: Int): Deferred<List<AvgSensorValue>>

    @GET("sensor-value/temperature/{gardenId}")
    fun getListAvgSensorTemperatureValueAsync(@Path("gardenId") id: Int): Deferred<List<AvgSensorValue>>

}

object GardenApi {
    val retrofitService : GardenApiService by lazy { retrofit.create(GardenApiService::class.java) }
}
