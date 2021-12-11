package com.example.android.politicalpreparedness.data.models

import com.example.android.politicalpreparedness.domain.entity.AdministrationBodyEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AdministrationBody(
    val name: String? = null,
    val electionInfoUrl: String? = null,
    val votingLocationFinderUrl: String? = null,
    val ballotInfoUrl: String? = null,
    val correspondenceAddress: Address? = null
)

fun AdministrationBody.toEntity() = AdministrationBodyEntity(
    name,
    electionInfoUrl,
    votingLocationFinderUrl,
    ballotInfoUrl,
    correspondenceAddress?.toEntity()
)