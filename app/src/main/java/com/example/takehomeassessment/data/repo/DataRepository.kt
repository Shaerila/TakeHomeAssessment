package com.example.takehomeassessment.data.repo

import com.example.takehomeassessment.data.dao.CountryDao
import com.example.takehomeassessment.data.remote.ApiService
import com.example.takehomeassessment.domain.model.CountryItem
import kotlinx.coroutines.flow.Flow


class DataRepository constructor(
    private val dao: CountryDao,
    private val api: ApiService
) {
    val allCountries: Flow<List<CountryItem>> = dao.getAll()

    suspend fun fetchCountry() {
        val countryFromRetrofit = api.fetchData()
        dao.insertAll(countryFromRetrofit)
    }
}