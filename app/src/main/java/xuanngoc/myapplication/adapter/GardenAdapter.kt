package xuanngoc.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import xuanngoc.myapplication.R
import xuanngoc.myapplication.model.Garden
import xuanngoc.myapplication.model.Plant
import xuanngoc.myapplication.util.DataList.Companion.getPlantByPlantId

class GardenAdapter : RecyclerView.Adapter<GardenAdapter.GardenViewHolder>(){

    private var gardens: List<Garden> = mutableListOf()
    private var plants: List<Plant> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GardenViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_garden, parent, false)
        return GardenViewHolder(view)
    }

    override fun onBindViewHolder(holder: GardenViewHolder, position: Int) {
        holder.bind(gardens[position], getPlantByPlantId(plants, gardens[position].plantId))
    }

    override fun getItemCount() = gardens.size


    fun setGardens(gardenList: List<Garden>) {
        gardens = gardenList
        notifyDataSetChanged()
    }

    fun setPlants(plantList: List<Plant>) {
        plants = plantList
        notifyDataSetChanged()
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        GardenAdapter.itemClickListener = itemClickListener
    }


    class GardenViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var nameTextView: TextView
        private lateinit var plantTextView: TextView

        fun bind(garden: Garden, plant: Plant) {
            nameTextView = itemView.findViewById(R.id.gardenNameView)
            plantTextView = itemView.findViewById(R.id.plantNameView)
            nameTextView.text = garden.name
            plantTextView.text = plant.name

            itemView.setOnClickListener {
                itemClickListener?.onItemClickListener(garden)
            }
        }
    }

    companion object {
        var itemClickListener: ItemClickListener? = null
    }


    interface ItemClickListener {
        fun onItemClickListener(garden: Garden)
    }


}