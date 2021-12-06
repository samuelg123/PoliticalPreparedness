package com.example.android.politicalpreparedness.data.models

import com.example.android.politicalpreparedness.domain.entity.OfficeEntity
import com.example.android.politicalpreparedness.domain.entity.RepresentativeEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Office(
    val name: String,
    @Json(name = "divisionId") val division: Division,
    @Json(name = "officialIndices") val officials: List<Int>
)

fun Office.toEntity() = OfficeEntity(name, division.toEntity(), officials)

fun Office.getRepresentatives(officials: List<Official>): List<RepresentativeEntity> {
    return this.officials.map { index ->
        RepresentativeEntity(officials[index].toEntity(), this.toEntity())
    }
}