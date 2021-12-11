package com.example.android.politicalpreparedness.domain.interactor

import com.example.android.politicalpreparedness.common.base.entity.ResultWrapper
import com.example.android.politicalpreparedness.domain.entity.AddressEntity
import com.example.android.politicalpreparedness.domain.entity.RepresentativeEntity
import com.example.android.politicalpreparedness.domain.repository.RepresentativeRepository
import javax.inject.Inject

class GetListRepresentativesByAddressUseCase @Inject constructor(private val repository: RepresentativeRepository) {
    suspend fun invoke(param: Param): ResultWrapper<List<RepresentativeEntity>> =
        repository.getListRepresentativesByAddress(param.address)

    data class Param(val address: AddressEntity)
}