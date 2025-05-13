package com.example.takehomeassessment.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takehomeassessment.data.repo.DataRepository
import com.example.takehomeassessment.domain.model.CountryItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch



class CountryViewModel constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    val countries: StateFlow<List<CountryItem>> =
        dataRepository.allCountries.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun fetchCountries() {
        viewModelScope.launch {
            dataRepository.fetchCountry()
        }
    }
}
