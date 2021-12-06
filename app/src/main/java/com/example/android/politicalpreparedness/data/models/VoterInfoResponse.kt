package com.example.android.politicalpreparedness.data.models

import com.example.android.politicalpreparedness.domain.entity.VoterInfoEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class VoterInfoResponse(
    val election: Election,
    val pollingLocations: String? = null, //TODO: Future Use - ? OK
    val contests: String? = null, //TODO: Future Use - ? OK
    val state: List<State>? = null,
    val electionElectionOfficials: List<ElectionOfficial>? = null
)

fun VoterInfoResponse.toEntity() = VoterInfoEntity(
    election.toEntity(),
    pollingLocations,
    contests,
    state,
    electionElectionOfficials
)