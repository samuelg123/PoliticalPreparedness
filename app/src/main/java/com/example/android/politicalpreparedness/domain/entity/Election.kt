package com.example.android.politicalpreparedness.domain.entity

import android.os.Parcelable
import com.example.android.politicalpreparedness.common.extensions.formatted
import com.example.android.politicalpreparedness.common.extensions.toIso8601String
import com.example.android.politicalpreparedness.data.models.Election
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ElectionEntity(
    val id: Int,
    val name: String,
    val electionDay: Date,
    val division: DivisionEntity
): Parcelable {
    fun getElectionDateFormatted() = electionDay.formatted("EEE MMM d hh:mm:ss z yyyy")
}

fun ElectionEntity.toDTO() = Election(id, name, electionDay, division.toDTO())
