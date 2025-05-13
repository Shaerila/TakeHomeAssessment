package com.example.takehomeassessment

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.takehomeassessment.data.remote.RetrofitClient
import com.example.takehomeassessment.data.repo.DataRepository
import com.example.takehomeassessment.data.repo.DatabaseModule
import com.example.takehomeassessment.view.model.CountryAdapter
import com.example.takehomeassessment.view.viewmodel.CountryViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: CountryViewModel
    private lateinit var adapter: CountryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.country_layout)


        // Setup DB, DAO, Retrofit manually
        val db = DatabaseModule.provideDatabase(applicationContext)
        val dao = DatabaseModule.provideCountryDao(db)
        val retrofit = RetrofitClient.provideRetrofit()
        val api = RetrofitClient.provideApiService(retrofit)
        val repo = DataRepository(dao, api)


        // ViewModel created using a Factory
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CountryViewModel(repo) as T
            }
        })[CountryViewModel::class.java]


        // RecyclerView setup
        val recyclerView = findViewById<RecyclerView>(R.id.countryRecycleView)
        adapter = CountryAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        // For Landscape
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.layoutManager = GridLayoutManager(this, 2)
        } else {
            recyclerView.layoutManager = LinearLayoutManager(this)
        }


        // Observe countries
        lifecycleScope.launch {
            repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.countries.collect {
                    adapter.submitCountryList(it)
                }
            }
        }

        if (savedInstanceState == null) {
            viewModel.fetchCountries()
        }

        val searchView = findViewById<TextInputEditText>(R.id.countrySearchBar)
        searchView.addTextChangedListener { adapter.filter.filter(it.toString()) }


    }
}

