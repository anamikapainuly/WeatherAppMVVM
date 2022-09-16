package com.anupras.weatherappsample.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.anupras.weatherappsample.R
import com.anupras.weatherappsample.adapters.WeatherAdapter
import com.anupras.weatherappsample.databinding.FragmentSuburbListBinding
import com.anupras.weatherappsample.utils.Resource
import com.anupras.weatherappsample.viewmodel.WeatherViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuburbListFragment : Fragment(R.layout.fragment_suburb_list) {
    private var _binding: FragmentSuburbListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by viewModels()
    private val weatherAdapter = WeatherAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSuburbListBinding.bind(view)

        initRecyclerView()
        initTabLayoutFilter()
        populateWeatherList()

    }

    private fun populateWeatherList() {
        viewModel.weatherList.observe(viewLifecycleOwner) { result ->
            weatherAdapter.submitList(result.data)
            binding.progressBar.isVisible =
                result is Resource.Loading && result.data.isNullOrEmpty()
        }

    }

    private fun populateWeatherByTemp() {
        viewModel.weatherListTemp.observe(viewLifecycleOwner) { result ->
            weatherAdapter.submitList(result)
        }
    }
    private fun populateWeatherByLastUpdate() {
        viewModel.weatherListLastUpdated.observe(viewLifecycleOwner) { result ->
            weatherAdapter.submitList(result)
        }
    }

    private fun initTabLayoutFilter() {
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        populateWeatherList()
                    }
                    1 -> {
                        populateWeatherByTemp()
                    }
                    2 -> {
                        populateWeatherByLastUpdate()
                    }
                    else -> { // Note the block

                    }
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

    }


    private fun initRecyclerView() {
        binding.apply {
            recyclerViewData.apply {
                adapter = weatherAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
