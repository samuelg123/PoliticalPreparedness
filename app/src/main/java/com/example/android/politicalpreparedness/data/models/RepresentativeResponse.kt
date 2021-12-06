package com.example.android.politicalpreparedness.data.models

import com.example.android.politicalpreparedness.domain.entity.RepresentativeEntity

data class RepresentativeResponse(
    val offices: List<Office>,
    val officials: List<Official> //TODO: WILL BE SHOWN ON PAGE REPRESENTATIVES
)

fun RepresentativeResponse.toEntity(): List<RepresentativeEntity> {
    val allRepresentatives = mutableListOf<RepresentativeEntity>()
    offices.forEach {
        allRepresentatives.addAll(it.getRepresentatives(officials))
    }
    return allRepresentatives
}