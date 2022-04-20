package com.example.countries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.databinding.ItemCountryBinding
import com.example.countries.model.Country
import com.example.countries.util.downloadFromUrl
import com.example.countries.util.placeHolderProgressBar
import com.example.countries.view.FeedFragmentDirections

class CountryAdapter(val countryList: ArrayList<Country>):
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), CountryClickListener {

    class CountryViewHolder(var view: ItemCountryBinding) : RecyclerView.ViewHolder(view.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.item_country, parent, false)

        val view = DataBindingUtil.inflate<ItemCountryBinding>(inflater, R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.country = countryList[position]
        holder.view.listener = this

//        var countryName = holder.view.findViewById<TextView>(R.id.countryName)
//        var countryRegion = holder.view.findViewById<TextView>(R.id.countryRegion)
//        var imageView = holder.view.findViewById<ImageView>(R.id.imageView)
//        countryName.text = countryList[position].countryName
//        countryRegion.text = countryList[position].countryRegion
//
//        holder.view.setOnClickListener {
//            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
//            Navigation.findNavController(it).navigate(action)
//        }
//        imageView.downloadFromUrl(countryList[position].imageUrl, placeHolderProgressBar(holder.view.context))
    }

    fun updateCountryList(newCountryList: List<Country>) {
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClick(v: View) {
        var countryUuidText = v.findViewById<TextView>(R.id.countryUuidText)
        val uuid = countryUuidText.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}