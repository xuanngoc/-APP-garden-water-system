package xuanngoc.myapplication.screen.listgarden

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import xuanngoc.myapplication.R
import xuanngoc.myapplication.adapter.GardenAdapter
import xuanngoc.myapplication.databinding.ActivityListGardenBinding
import xuanngoc.myapplication.model.*
import xuanngoc.myapplication.screen.detailgarden.DetailGardenActivity

class ListGardenActivity : AppCompatActivity() {

    private val viewModel: ListGardenViewModel by lazy {
        ViewModelProviders.of(this).get(ListGardenViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_list_garden)

        val binding = DataBindingUtil.setContentView<ActivityListGardenBinding>(this,  R.layout.activity_list_garden)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        val gardenAdapter = GardenAdapter()

        viewModel.gardens.observe(this, Observer { gardens ->
            if (gardens.isNullOrEmpty()) {
                binding.loadingView.visibility = View.VISIBLE
            } else {
                binding.loadingView.visibility = View.GONE
                gardenList = gardens
                gardenAdapter.setGardens(gardenList)
            }
        })

        viewModel.plants.observe(this, Observer { plants ->
            plantList = plants
            gardenAdapter.setPlants(plantList)
        })

        gardenAdapter.setItemClickListener(object: GardenAdapter.ItemClickListener {
            override fun onItemClickListener(garden: Garden) {
                // intent to detail garden activity
                Toast.makeText(this@ListGardenActivity, "Clicked", Toast.LENGTH_SHORT).show()
                val goToDetailGardenActivity = Intent(this@ListGardenActivity, DetailGardenActivity::class.java)
                goToDetailGardenActivity.putExtra(GARDEN_ID, garden.id)
                startActivity(goToDetailGardenActivity)
            }
        })

        binding.recyclerViewGarden.adapter = gardenAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerViewGarden.layoutManager = linearLayoutManager
    }


    companion object{
        lateinit var gardenList: List<Garden>
        lateinit var plantList: List<Plant>
        const val GARDEN_ID = "GARDEN_ID"
        const val PLANT_ID = "PLANT_ID"

    }

}
