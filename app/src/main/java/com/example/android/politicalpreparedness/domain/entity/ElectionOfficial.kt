package com.example.android.politicalpreparedness.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ElectionOfficialEntity(
    val name: String,
    val title: String,
    val phone: String,
    val fax: String,
    val emailAddress: String
): Parcelable