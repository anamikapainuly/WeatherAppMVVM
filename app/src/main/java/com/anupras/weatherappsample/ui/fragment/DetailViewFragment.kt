package com.anupras.weatherappsample.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.anupras.weatherappsample.R
import com.anupras.weatherappsample.databinding.FragmentDetailViewBinding
import com.anupras.weatherappsample.ui.WeatherActivity
import com.anupras.weatherappsample.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailViewFragment : Fragment(R.layout.fragment_detail_view) {
    private var _binding: FragmentDetailViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WeatherViewModel by viewModels()
    private val args: DetailViewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailViewBinding.bind(view)

        viewModel.getWeatherDetails(args.id.toInt()).observe(viewLifecycleOwner) {
            if(it!=null) {
                if (it.weatherTemp != null) {
                    binding.tempText.text = it.weatherTemp.toString() + "\u00B0"
                }
                if (it.name != null) {
                    binding.suburbText.text = it.name.toString() + "\u00B0"
                }
                if (it.weatherFeelsLike != null) {
                    binding.feelsLikeText.text = "Feels Like :" + it.weatherFeelsLike.toString() + "\u00B0"
                }
                if (it.weatherCondition != null) {
                    binding.detailText.text = it.weatherCondition.toString() + "\u00B0"
                }

                if (it.country?._name != null) {
                    binding.countryText.text = it.country?._name.toString()
                }
                if(it.weatherConditionIcon!=null){

                }


            }

        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}