package com.example.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.countries.R
import com.example.countries.databinding.FragmentCountryBinding
import com.example.countries.util.downloadFromUrl
import com.example.countries.util.placeHolderProgressBar
import com.example.countries.viewmodel.CountryViewModel

class CountryFragment : Fragment() {

    private lateinit var viewModel: CountryViewModel
    private var countryUuid = 0

//    private lateinit var countryImage: ImageView
//    private lateinit var countryName: TextView
//    private lateinit var countryCapital: TextView
//    private lateinit var countryRegion: TextView
//    private lateinit var countryCurrency: TextView
//    private lateinit var countryLanguage: TextView

    private lateinit var dataBinding: FragmentCountryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_country, container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        countryName = view.findViewById(R.id.countryName)
//        countryCapital = view.findViewById(R.id.countryCapital)
//        countryRegion = view.findViewById(R.id.countryRegion)
//        countryCurrency = view.findViewById(R.id.countryCurrency)
//        countryLanguage = view.findViewById(R.id.countryLanguage)
//        countryImage = view.findViewById(R.id.countryImage)

        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }

        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->
            country?.let {
                dataBinding.selectedCountry = country // yada it diyebilirsin

//                countryName.text = country.countryName
//                countryCapital.text = country.countryCapital
//                countryRegion.text = country.countryRegion
//                countryCurrency.text = country.countryCurrency
//                countryLanguage.text = country.countryLanguage
//                context?.let {
//                    countryImage.downloadFromUrl(country.imageUrl, placeHolderProgressBar(it))
//                }
            }
        })
    }
}