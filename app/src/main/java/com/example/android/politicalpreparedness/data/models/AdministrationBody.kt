package com.example.android.politicalpreparedness.data.models

import com.squareup.moshi.JsonClass

//TODO: Election Information Page entity
@JsonClass(generateAdapter = true)
data class AdministrationBody (
        val name: String? = null,
        val electionInfoUrl: String? = null,
        val votingLocationFinderUrl: String? = null,
        val ballotInfoUrl: String? = null,
        val correspondenceAddress: Address? = null
)