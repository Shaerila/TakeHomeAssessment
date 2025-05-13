package com.example.takehomeassessment.data.repo

import android.content.Context
import androidx.room.Room
import com.example.takehomeassessment.data.dao.CountryDao


// This was a dagger/hilt full file
object DatabaseModule {


    fun provideDatabase(context: Context): CountryDatabase {
        return Room.databaseBuilder(
            context,
            CountryDatabase::class.java,
            "country_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    fun provideCountryDao(database: CountryDatabase): CountryDao {
        return database.countryDao()
    }
}