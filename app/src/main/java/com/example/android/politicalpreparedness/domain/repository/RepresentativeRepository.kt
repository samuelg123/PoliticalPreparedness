package com.example.android.politicalpreparedness.domain.repository

import com.example.android.politicalpreparedness.common.base.entity.ResultWrapper
import com.example.android.politicalpreparedness.domain.entity.AddressEntity
import com.example.android.politicalpreparedness.domain.entity.RepresentativeEntity

interface RepresentativeRepository {
    suspend fun getListRepresentativesByDivisionId(ocdDivisionId: String): ResultWrapper<List<RepresentativeEntity>>

    suspend fun getListRepresentativesByAddress(address: AddressEntity): ResultWrapper<List<RepresentativeEntity>>
}