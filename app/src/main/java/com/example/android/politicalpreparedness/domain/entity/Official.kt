package com.example.android.politicalpreparedness.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OfficialEntity(
    val name: String,
    val address: List<AddressEntity>? = null,
    val party: String? = null,
    val phones: List<String>? = null,
    val urls: List<String>? = null,
    val photoUrl: String? = null,
    val channels: List<ChannelEntity>? = null
): Parcelable