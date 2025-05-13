package com.example.takehomeassessment.domain.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CountryItem(

    @SerializedName("capital")
    val capital: String,

    @SerializedName("code")
    @PrimaryKey val code: String,

    @SerializedName("flag")
    val flag: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("region")
    val region: String
)