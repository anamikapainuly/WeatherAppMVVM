package com.anupras.weatherappsample.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anupras.weatherappsample.R
import com.anupras.weatherappsample.adapters.WeatherAdapter
import com.anupras.weatherappsample.databinding.FragmentSuburbListBinding
import com.anupras.weatherappsample.utils.Helper
import com.anupras.weatherappsample.utils.PrefsHelper
import com.anupras.weatherappsample.utils.Resource
import com.anupras.weatherappsample.viewmodel.WeatherViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SuburbListFragment : Fragment(R.layout.fragment_suburb_list), MenuProvider,
    WeatherAdapter.OnItemClickListener {
    private var _binding: FragmentSuburbListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by viewModels()
    private val weatherAdapter = WeatherAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSuburbListBinding.bind(view)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        initRecyclerView()
        initTabLayoutFilter()
        populateWeatherList()

    }

    private fun populateWeatherList() {
        viewModel.weatherList.observe(viewLifecycleOwner) { result ->
            binding.progressBar.isVisible =
                result is Resource.Loading && result.data.isNullOrEmpty()
            weatherAdapter.submitList(result.data)
            weatherAdapter.notifyItemRangeChanged(0, weatherAdapter.itemCount)
        }
        //Saving last updated date
        PrefsHelper.setLastSyncDate("Last Updated:  " + Helper.returnCurrentDate().toString())
        binding.lastUpdatedText.text = PrefsHelper.getLastSyncDate()

    }

    private fun populateWeatherByTemp() {
        viewModel.weatherListTemp.observe(viewLifecycleOwner) { result ->
            weatherAdapter.submitList(result)
            weatherAdapter.notifyItemRangeChanged(0, weatherAdapter.itemCount)
        }
    }

    private fun populateWeatherByLastUpdate() {
        viewModel.weatherListLastUpdated.observe(viewLifecycleOwner) { result ->
            weatherAdapter.submitList(result)
            weatherAdapter.notifyItemRangeChanged(0, weatherAdapter.itemCount)
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
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.header_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_refresh -> {
                populateWeatherList()
                return true
            }
        }
        return false
    }

    override fun onItemClick(position: Int, id: Int) {
        val action =
            SuburbListFragmentDirections.actionSuburbListFragmentToDetailViewFragment(id.toString())
        findNavController().navigate(action)
    }

}
