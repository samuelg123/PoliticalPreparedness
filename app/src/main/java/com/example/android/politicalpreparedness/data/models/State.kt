package com.example.android.politicalpreparedness.data.models

import com.example.android.politicalpreparedness.domain.entity.StateEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class State (
    val name: String,
    val electionAdministrationBody: AdministrationBody
)

fun State.toEntity() = StateEntity(name, electionAdministrationBody.toEntity())