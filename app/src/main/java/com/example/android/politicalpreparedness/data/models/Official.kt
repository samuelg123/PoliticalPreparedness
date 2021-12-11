package com.example.android.politicalpreparedness.data.models

import com.example.android.politicalpreparedness.domain.entity.OfficialEntity

data class Official(
    val name: String,
    val address: List<Address>? = null,
    val party: String? = null,
    val phones: List<String>? = null,
    val urls: List<String>? = null,
    val photoUrl: String? = null,
    val channels: List<Channel>? = null
)

fun Official.toEntity() =
    OfficialEntity(
        name,
        address?.map { it.toEntity() },
        party,
        phones,
        urls,
        photoUrl,
        channels?.map { it.toEntity() },
    )