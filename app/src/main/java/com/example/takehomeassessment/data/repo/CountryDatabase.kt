package com.example.takehomeassessment.data.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.takehomeassessment.data.dao.CountryDao
import com.example.takehomeassessment.domain.model.CountryItem


@Database(entities = [CountryItem::class], version = 2)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}