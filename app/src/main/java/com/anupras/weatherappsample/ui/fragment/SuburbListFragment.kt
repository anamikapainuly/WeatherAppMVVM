package com.anupras.weatherappsample.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.anupras.weatherappsample.R
import com.anupras.weatherappsample.adapters.WeatherAdapter
import com.anupras.weatherappsample.viewmodel.WeatherViewModel


class SuburbListFragment : Fragment(R.layout.fragment_suburb_list) {
    private val viewModel: WeatherViewModel by viewModels()
    private val weatherAdapter = WeatherAdapter()
}