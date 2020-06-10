package xuanngoc.myapplication.screen.detailgarden

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import xuanngoc.myapplication.R
import xuanngoc.myapplication.databinding.ActivityDetailGardenBinding
import xuanngoc.myapplication.model.Device
import xuanngoc.myapplication.model.DeviceType
import xuanngoc.myapplication.model.Sensor
import xuanngoc.myapplication.model.SensorType
import xuanngoc.myapplication.screen.listgarden.ListGardenActivity
import xuanngoc.myapplication.util.DataList
import xuanngoc.myapplication.screen.listgarden.ListGardenActivity.Companion as Constant

class DetailGardenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailGardenBinding
    private lateinit var viewModel: DetailGardenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_garden)
        viewModel = ViewModelProviders.of(this).get(DetailGardenViewModel::class.java)

        val gardenId = intent.getIntExtra(Constant.GARDEN_ID, 0)
        val gardenName = DataList.getGardenByGardenId(ListGardenActivity.gardenList, gardenId)?.name

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.gardenNameView.text = gardenName

        binding.swipeContainer.setOnRefreshListener {
            finish()
            startActivity(intent)
            //Log.d("worked", "true")
        }

        viewModel.deviceTypes.observe(this, Observer { deviceTypes ->
            deviceTypeList = deviceTypes
        })

        viewModel.devices.observe(this, Observer { devices ->
            deviceList = devices
            insertDataIntoDeviceTable(gardenId)

        })

        viewModel.sensorTypes.observe(this, Observer { sensorTypes ->
            sensorTypeList = sensorTypes
        })

        viewModel.sensors.observe(this, Observer { sensors ->
            sensorList = sensors
            insertDataIntoSensorTable(gardenId)
        })

//        insertDataIntoDeviceTable(gardenId)
//        insertDataIntoSensorTable(gardenId)

    }

    private fun insertDataIntoDeviceTable(gardenId: Int) {
        val tableLayout = binding.listDeviceTable
        val devicesInGarden = DataList.getDeviceListByGardenId(deviceList, gardenId)
        var order = 0
        devicesInGarden.forEach { device ->
            val row = layoutInflater.inflate(R.layout.table_row_template, tableLayout, false)
            row.apply {
                findViewById<TextView>(R.id.orderView).text = order.toString()
                findViewById<TextView>(R.id.deviceNameView).text = device.name
                findViewById<TextView>(R.id.deviceTypeView).text = DataList.getDeviceTypeById(deviceTypeList, device.deviceTypeId).name
                findViewById<TextView>(R.id.deviceStatusView).text = device.status
                val switchCompat = findViewById<SwitchCompat>(R.id.deviceStateView)
                switchCompat.isChecked = device.state
                switchCompat.setOnClickListener {
                    viewModel.onToggleSwitchDevice(device.id)
                }
                findViewById<SwitchCompat>(R.id.deviceStateView).text = device.state.toString()
            }
            order++
            tableLayout.addView(row)
        }
    }

    private fun insertDataIntoSensorTable(gardenId: Int) {
        val tableLayout = binding.listSensorTable
        val sensorsInGarden = DataList.getSensorListByGardenId(sensorList, gardenId)
        var orderNumber = 0
        sensorsInGarden.forEach { sensor ->
            val row = layoutInflater.inflate(R.layout.table_row_template, tableLayout, false)
            row.apply {
                findViewById<TextView>(R.id.orderView).text = orderNumber.toString()
                findViewById<TextView>(R.id.deviceNameView).text = sensor.name
                findViewById<TextView>(R.id.deviceTypeView).text = DataList.getSensorTypeById(sensorTypeList, sensor.sensorTypeId).name
                findViewById<TextView>(R.id.deviceStatusView).text = sensor.status
                val switchCompat = findViewById<SwitchCompat>(R.id.deviceStateView)
                switchCompat.setOnCheckedChangeListener(null)
                switchCompat.isChecked = sensor.state
                switchCompat.isClickable = false
            }
            orderNumber++
            tableLayout.addView(row)
        }
    }

    companion object {
        lateinit var deviceTypeList: List<DeviceType>
        lateinit var deviceList: List<Device>
        lateinit var sensorTypeList: List<SensorType>
        lateinit var sensorList: List<Sensor>
    }
}
