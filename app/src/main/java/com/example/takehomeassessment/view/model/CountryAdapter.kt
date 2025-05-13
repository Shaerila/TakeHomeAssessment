package com.example.takehomeassessment.view.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.takehomeassessment.R
import com.example.takehomeassessment.domain.model.CountryItem


class CountryAdapter : ListAdapter<CountryItem, CountryAdapter.CountryViewHolder>(DiffCallback()),
    Filterable {

    private var fullList: List<CountryItem> = emptyList()

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.countryName)
        val conCode: TextView = itemView.findViewById(R.id.countryCode)
        val region: TextView = itemView.findViewById(R.id.countryRegion)
        val capital: TextView = itemView.findViewById(R.id.countryCapital)
    }

    // Inflater that Inflates the card view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_card, parent, false)
        return CountryViewHolder(view)
    }

    // Binds the data of item (CountryItem) to the ViewHolder of its respective position
    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val item = getItem(position)
        holder.name.text = item.name
        holder.region.text = item.region
        holder.conCode.text = item.code
        holder.capital.text = "Capital: ${item.capital}"

    }

    // Stores the list of countries so that when the adapter is notified there new data
    fun submitCountryList(list: List<CountryItem>) {
        fullList = list
        submitList(list)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(query: CharSequence?): FilterResults {
                val filtered = if (query.isNullOrEmpty()) {
                    fullList
                } else {
                    fullList.filter {
                        it.name.contains(query, ignoreCase = true) ||
                                it.capital.contains(query, ignoreCase = true) ||
                                it.region.contains(query, ignoreCase = true) ||
                                it.code.contains(query, ignoreCase = true)
                    }
                }
                return FilterResults().apply { values = filtered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results?.values as? List<CountryItem>)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CountryItem>() {
        override fun areItemsTheSame(old: CountryItem, new: CountryItem) = old.code == new.code
        override fun areContentsTheSame(old: CountryItem, new: CountryItem) = old == new
    }
}