package com.example.countries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.countries.R
import com.example.countries.adapter.CountryAdapter
import com.example.countries.viewmodel.FeedViewModel

class FeedFragment : Fragment() {
    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())
    private lateinit var countryListRecyclerView: RecyclerView
    private lateinit var countryError: TextView
    private lateinit var countryLoading: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        countryListRecyclerView = view.findViewById(R.id.countryListRecyclerView)
        countryError = view.findViewById(R.id.countryError)
        countryLoading = view.findViewById(R.id.countryLoading)

        countryListRecyclerView.layoutManager = LinearLayoutManager(context)
        countryListRecyclerView.adapter = countryAdapter

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            countryListRecyclerView.visibility = View.GONE
            countryError.visibility = View.GONE
            countryLoading.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()

//        feedButton.setOnClickListener{
//            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
//            Navigation.findNavController(it).navigate(action)
//        }

//        val myString = "James"
//        myString.myExtension("Heitfield")
    }

    private fun observeLiveData() {
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                countryListRecyclerView.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {
                    countryError.visibility = View.VISIBLE
                } else {
                    countryError.visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    countryLoading.visibility = View.VISIBLE
                    countryListRecyclerView.visibility = View.GONE
                    countryError.visibility = View.GONE
                } else {
                    countryLoading.visibility = View.GONE
                }
            }
        })
    }
}