package com.example.android.politicalpreparedness.data.models

import androidx.room.*
import com.example.android.politicalpreparedness.domain.entity.ElectionEntity
import com.squareup.moshi.*
import java.util.*

@Entity(tableName = "election_table")
data class Election(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "electionDay") val electionDay: Date,
    @Embedded(prefix = "division_") @Json(name = "ocdDivisionId") val division: Division
)

fun Election.toEntity() = ElectionEntity(id, name, electionDay, division.toEntity())
