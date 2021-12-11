package com.example.android.politicalpreparedness.data.models

import com.example.android.politicalpreparedness.domain.entity.DivisionEntity

data class Division(
    val id: String,
    val country: String,
    val state: String
)

fun Division.toEntity() = DivisionEntity(id, country, state)
