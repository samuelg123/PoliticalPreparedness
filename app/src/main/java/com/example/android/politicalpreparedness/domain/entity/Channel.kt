package com.example.android.politicalpreparedness.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChannelEntity (
    val type: String,
    val id: String
): Parcelable