package com.anupras.weatherappsample.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anupras.weatherappsample.R
import com.anupras.weatherappsample.model.Data


/**
 * Created by anamika on 26,Sept,2022
 */
class CountryAdapter(
    private var dataSetX: MutableList<String?>,
    private val itemClickListener: OnItemClickListenerViewCountry
) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val countryName: TextView = view.findViewById(R.id.country_name)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.spinner_holder_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.countryName.text = dataSetX[position].toString()
        if(position %2 == 1)
        {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#F3F4F7"));
        }

        viewHolder.itemView.setOnClickListener {
            itemClickListener.onItemClick(dataSetX[position].toString())
        }

    }
    override fun getItemCount() = dataSetX.size

    interface OnItemClickListenerViewCountry {
        fun onItemClick(country: String)
    }
}

