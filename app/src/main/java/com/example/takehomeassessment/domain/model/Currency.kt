package com.example.takehomeassessment.domain.model


import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("code")
    val code: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("symbol")
    val symbol: String
)