package com.example.android.politicalpreparedness.data.models

import com.example.android.politicalpreparedness.domain.entity.VoterInfoEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VoterInfoResponse(
    val election: Election,
    val pollingLocations: List<PollingLocation>? = null,
    val contests: List<Contest>? = null,
    val state: List<State>? = null,
    val kind: String
)

fun VoterInfoResponse.toEntity(): VoterInfoEntity = VoterInfoEntity(
    election.toEntity(),
    state?.firstOrNull()?.toEntity(),
)

@JsonClass(generateAdapter = true)
data class Contest(
    val office: String? = null,
    val roles: List<String>? = null,
    val district: District,
    val candidates: List<Candidate>? = null,
    val referendumTitle: String? = null,
    val referendumSubtitle: String? = null,
    val referendumURL: String? = null
)

@JsonClass(generateAdapter = true)
data class Candidate(
    val name: String,
    val party: String,
    val candidateURL: String? = null,
    val channels: List<Channel>? = null,
    val email: String? = null,
    val phone: String? = null
)

@JsonClass(generateAdapter = true)
data class District(
    val name: String,
    val scope: String,
    val id: String
)

@JsonClass(generateAdapter = true)
data class PollingLocation(
    val address: Address,
    val notes: String,
    val pollingHours: String
)