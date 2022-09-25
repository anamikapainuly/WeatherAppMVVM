package com.anupras.weatherappsample.ui.fragment

import android.os.Bundle
import android.util.Log
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
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private val args: SuburbListFragmentArgs by navArgs()
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by viewModels()
    private val weatherAdapter = WeatherAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSuburbListBinding.bind(view)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        initRecyclerView()
        populateWeatherList(args.id.toString())
        initTabLayoutFilter()
        initUI()
    }

    private fun initUI() {

            weatherAdapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                binding.recyclerViewData.scrollToPosition(0)
            }
            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                binding.recyclerViewData.scrollToPosition(0)
            }
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                binding.recyclerViewData.scrollToPosition(0)
            }
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                binding.recyclerViewData.scrollToPosition(0)
            }
            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                binding.recyclerViewData.scrollToPosition(0)
            }
            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                binding.recyclerViewData.scrollToPosition(0)
            }
        })

        binding.buttonFilter.setOnClickListener {
            val action =
                SuburbListFragmentDirections.actionSuburbListFragmentToCountryFragment2()
            findNavController().navigate(action)
        }

        binding.swiperefresh.setOnRefreshListener {
            populateWeatherList(args.id.toString())
        }
    }

    private fun populateWeatherList(selected_country: String) {

        viewModel.weatherList.observe(viewLifecycleOwner) { result ->
            weatherAdapter.submitList(result.data)
            weatherAdapter.notifyItemRangeChanged(0, weatherAdapter.itemCount)

            binding.progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
            binding.textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
            binding.textViewError.text = result.error?.localizedMessage

            binding.swiperefresh.isRefreshing = false

        }

        //Saving last updated date
        PrefsHelper.setLastSyncDate("Last Updated:  " + Helper.returnCurrentDate().toString())
        binding.lastUpdatedText.text = PrefsHelper.getLastSyncDate()


    }

    private fun populateWeatherByTemp() {
        viewModel.weatherListTemp.observe(viewLifecycleOwner) {
            weatherAdapter.submitList(it)
            weatherAdapter.notifyItemRangeChanged(0, weatherAdapter.itemCount)
        }
    }

    private fun populateWeatherByLastUpdate() {
        viewModel.weatherListLastUpdated.observe(viewLifecycleOwner) {
            weatherAdapter.submitList(it)
            weatherAdapter.notifyItemRangeChanged(0, weatherAdapter.itemCount)
        }

    }


    private fun initTabLayoutFilter() {
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        populateWeatherList(args.id.toString())
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
                binding.swiperefresh.isRefreshing = true
                populateWeatherList(args.id.toString())
                if(binding.tabLayout.selectedTabPosition==1)
                {
                    populateWeatherByTemp()
                }else if(binding.tabLayout.selectedTabPosition==2)
                {
                    populateWeatherByLastUpdate()
                }
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
