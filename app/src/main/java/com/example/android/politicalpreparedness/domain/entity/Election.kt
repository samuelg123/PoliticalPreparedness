package com.example.android.politicalpreparedness.domain.entity

import android.os.Parcelable
import com.example.android.politicalpreparedness.data.models.Election
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ElectionEntity(
    val id: Int,
    val name: String,
    val electionDay: Date,
    val division: DivisionEntity
): Parcelable

fun ElectionEntity.toDTO() = Election(id, name, electionDay, division.toDTO())
