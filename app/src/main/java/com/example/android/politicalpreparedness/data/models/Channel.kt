package com.example.android.politicalpreparedness.data.models

import com.example.android.politicalpreparedness.domain.entity.ChannelEntity

data class Channel(
    val type: String,
    val id: String
)

fun Channel.toEntity() = ChannelEntity(type, id)