package com.example.android.politicalpreparedness.domain.entity

import android.os.Parcelable
import com.example.android.politicalpreparedness.data.models.Office
import com.example.android.politicalpreparedness.data.models.Official
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class RepresentativeEntity(
    val official: OfficialEntity? = null,
    val office: OfficeEntity? = null,
    val entityId: String = UUID.randomUUID().toString()
) : Parcelable