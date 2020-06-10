package xuanngoc.myapplication.screen.detailgarden

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CompoundButton
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import xuanngoc.myapplication.R
import xuanngoc.myapplication.databinding.ActivityDetailGardenBinding
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

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        insertDataIntoDeviceTable(gardenId)
        insertDataIntoSensorTable(gardenId)
    }

    private fun insertDataIntoDeviceTable(gardenId: Int) {
        val tableLayout = binding.listDeviceTable
        val devicesInGarden = DataList.getDeviceListByGardenId(ListGardenActivity.deviceList, gardenId)
        var order = 0
        devicesInGarden.forEach { device ->
            val row = layoutInflater.inflate(R.layout.table_row_template, tableLayout, false)
            row.apply {
                findViewById<TextView>(R.id.orderView).text = order.toString()
                findViewById<TextView>(R.id.deviceNameView).text = device.name
                findViewById<TextView>(R.id.deviceTypeView).text = DataList.getDeviceTypeById(ListGardenActivity.deviceTypeList, device.deviceTypeId).name
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
        val sensorsInGarden = DataList.getSensorListByGardenId(ListGardenActivity.sensorList, gardenId)
        var orderNumber = 0
        sensorsInGarden.forEach { sensor ->
            val row = layoutInflater.inflate(R.layout.table_row_template, tableLayout, false)
            row.apply {
                findViewById<TextView>(R.id.orderView).text = orderNumber.toString()
                findViewById<TextView>(R.id.deviceNameView).text = sensor.name
                findViewById<TextView>(R.id.deviceTypeView).text = DataList.getSensorTypeById(ListGardenActivity.sensorTypeList, sensor.sensorTypeId).name
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
}
