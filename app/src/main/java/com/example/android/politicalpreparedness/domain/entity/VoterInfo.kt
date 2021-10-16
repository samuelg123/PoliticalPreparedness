package com.example.android.politicalpreparedness.domain.entity

import com.example.android.politicalpreparedness.data.models.Election
import com.example.android.politicalpreparedness.data.models.ElectionOfficial
import com.example.android.politicalpreparedness.data.models.State


class VoterInfo (
    val election: Election,
    val pollingLocations: String? = null, //TODO: Future Use
    val contests: String? = null, //TODO: Future Use
    val state: List<State>? = null,
    val electionElectionOfficials: List<ElectionOfficial>? = null
)