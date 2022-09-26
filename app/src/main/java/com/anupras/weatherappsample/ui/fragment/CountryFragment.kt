package com.anupras.weatherappsample.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anupras.weatherappsample.R
import com.anupras.weatherappsample.adapters.CountryAdapter
import com.anupras.weatherappsample.databinding.FragmentCountryBinding
import com.anupras.weatherappsample.model.Data
import com.anupras.weatherappsample.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryFragment :Fragment(R.layout.fragment_country), CountryAdapter.OnItemClickListenerViewCountry {
    private var _binding: FragmentCountryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by viewModels()
    private var listLocal = mutableListOf<String?>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCountryBinding.bind(view)

        viewModel.weatherByCountry.observe(viewLifecycleOwner) {
            it.forEach { it2 ->
                listLocal.add(it2.country?._name)
            }
            listLocal = listLocal.distinct() as MutableList<String?>
            binding.recycler.layoutManager = LinearLayoutManager(activity)
            binding.recycler.adapter = CountryAdapter(listLocal, this)
        }

        binding.btnClose.setOnClickListener {
            val action = CountryFragmentDirections.actionCountryFragment2ToSuburbListFragment("")
            findNavController().navigate(action)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(country: String) {
        val action = CountryFragmentDirections.actionCountryFragment2ToSuburbListFragment(country)
        findNavController().navigate(action)
    }

}