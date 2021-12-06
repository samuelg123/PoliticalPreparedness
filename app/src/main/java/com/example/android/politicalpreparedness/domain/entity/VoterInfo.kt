package com.example.android.politicalpreparedness.domain.entity

import com.example.android.politicalpreparedness.data.models.ElectionOfficial
import com.example.android.politicalpreparedness.data.models.State


class VoterInfoEntity (
    val election: ElectionEntity,
    val pollingLocations: String? = null, //TODO: Future Use - ? OK
    val contests: String? = null, //TODO: Future Use - ? OK
    val state: List<State>? = null,
    val electionElectionOfficials: List<ElectionOfficial>? = null
)