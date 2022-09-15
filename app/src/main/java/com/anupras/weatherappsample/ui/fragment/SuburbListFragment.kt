package com.anupras.weatherappsample.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.anupras.weatherappsample.R
import com.anupras.weatherappsample.adapters.WeatherAdapter
import com.anupras.weatherappsample.databinding.FragmentSuburbListBinding
import com.anupras.weatherappsample.utils.Resource
import com.anupras.weatherappsample.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuburbListFragment : Fragment(R.layout.fragment_suburb_list) {
    private val viewModel: WeatherViewModel by viewModels()
    private val weatherAdapter = WeatherAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSuburbListBinding.bind(view)

        binding.apply {
            recyclerViewData.apply {
                adapter = weatherAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.weatherList.observe(viewLifecycleOwner) { result ->
            weatherAdapter.submitList(result.data)
            Log.d("CHECK--", result.data.toString())
            binding.progressBar.isVisible =
                result is Resource.Loading && result.data.isNullOrEmpty()
        }

    }
}
