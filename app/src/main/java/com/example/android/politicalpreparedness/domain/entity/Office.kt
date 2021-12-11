package com.example.android.politicalpreparedness.domain.entity

import android.os.Parcelable
import com.example.android.politicalpreparedness.domain.entity.RepresentativeEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
data class OfficeEntity (
    val name: String,
    val division:DivisionEntity,
    val officials: List<Int>
): Parcelable {
    fun getRepresentatives(officials: List<OfficialEntity>): List<RepresentativeEntity> {
        return this.officials.map { index ->
            RepresentativeEntity(officials[index], this)
        }
    }
}
