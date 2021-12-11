package com.example.android.politicalpreparedness.data.models

import com.example.android.politicalpreparedness.domain.entity.AddressEntity

data class Address(
    val line1: String,
    val line2: String? = null,
    val city: String,
    val state: String,
    val zip: String
)

fun Address.toEntity() = AddressEntity(line1, line2, city, state, zip)