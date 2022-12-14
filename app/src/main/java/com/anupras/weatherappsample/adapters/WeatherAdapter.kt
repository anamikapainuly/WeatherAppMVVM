package com.anupras.weatherappsample.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anupras.weatherappsample.databinding.ItemWeatherBinding
import com.anupras.weatherappsample.model.Data
import com.anupras.weatherappsample.ui.fragment.SuburbListFragment
import com.anupras.weatherappsample.ui.fragment.SuburbListFragmentDirections
import com.anupras.weatherappsample.utils.Helper

/**
 * Created by anamika on 16,September,2022
 */
class WeatherAdapter(
    private val itemClickListener: OnItemClickListener
) : ListAdapter<Data, WeatherAdapter.WeatherViewHolder>(DiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
        holder.itemView.setOnClickListener {
            currentItem.id?.let { it1 -> itemClickListener.onItemClick(position, it1) }
        }
    }

    class WeatherViewHolder(private val binding: ItemWeatherBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Data){
            binding.apply {
                suburbText.text = data.name
                forecastText.text = data.weatherCondition
                if(data.weatherTemp!=null) {
                    tempText.text = "${data.weatherTemp} ${"\u00B0"}"
                }
                else {
                    tempText.text = "NA  "
                }
                var dateTime = Helper.getDateCurrentTimeZone(data.weatherLastUpdated?.toLong() ?: 0)
                lastUpdatedText.text = "Last Updated: $dateTime"

            }
        }
    }

    //Check for change of Data/items and refresh items on screen
    class DiffCallback : DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data) =
            oldItem.venueID == newItem.venueID

        override fun areContentsTheSame(oldItem: Data, newItem: Data) =
            oldItem == newItem

    }
    interface OnItemClickListener {
        fun onItemClick(position: Int, id: Int)
    }
}