package com.example.android.politicalpreparedness.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class VoterInfoEntity(
    val election: ElectionEntity,
    val state: StateEntity? = null,
) : Parcelable

@Parcelize
class StateEntity(
    val name: String,
    val electionAdministrationBody: AdministrationBodyEntity
) : Parcelable

@Parcelize
data class AdministrationBodyEntity(
    val name: String? = null,
    val electionInfoUrl: String? = null,
    val votingLocationFinderUrl: String? = null,
    val ballotInfoUrl: String? = null,
    val correspondenceAddress: AddressEntity? = null
) : Parcelable