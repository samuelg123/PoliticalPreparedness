package com.example.android.politicalpreparedness.domain.entity

import android.os.Parcelable
import com.example.android.politicalpreparedness.data.models.Division
import kotlinx.parcelize.Parcelize

@Parcelize
data class DivisionEntity(
    val id: String,
    val country: String,
    val state: String
) : Parcelable {
    fun toFormattedStringAddress(): String {
        if (state.isBlank()) return country
        return "$state, $country"
    }
}

fun DivisionEntity.toDTO() = Division(id, country, state)