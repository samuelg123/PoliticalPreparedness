package com.example.android.politicalpreparedness.data.repository

import com.example.android.politicalpreparedness.common.base.entity.ApiResult
import com.example.android.politicalpreparedness.common.utils.ApiUtils
import com.example.android.politicalpreparedness.data.datasource.remote.CivicsApiService
import com.example.android.politicalpreparedness.common.base.entity.ResultWrapper
import com.example.android.politicalpreparedness.data.models.toEntity
import com.example.android.politicalpreparedness.domain.entity.AddressEntity
import com.example.android.politicalpreparedness.domain.entity.RepresentativeEntity
import com.example.android.politicalpreparedness.domain.repository.RepresentativeRepository
import javax.inject.Inject

class RepresentativeRepositoryImpl @Inject constructor(
    private val civicsApiService: CivicsApiService,
) : RepresentativeRepository {
    override suspend fun getListRepresentativesByDivisionId(ocdDivisionId: String): ResultWrapper<List<RepresentativeEntity>> {
        return when (val result =
            ApiUtils.wrapApiResponse { civicsApiService.getRepresentativeInfoByDivision(ocdDivisionId) }) {
            is ApiResult.Success -> ResultWrapper.Success(result.value.toEntity())
            is ApiResult.GenericError -> ResultWrapper.Error(result.error?.message, result.code)
            is ApiResult.NetworkError -> ResultWrapper.Error(result.error?.message)
        }
    }

    override suspend fun getListRepresentativesByAddress(address: AddressEntity): ResultWrapper<List<RepresentativeEntity>> {
        return when (val result =
            ApiUtils.wrapApiResponse { civicsApiService.getRepresentativesByAddress(address.toFormattedString()) }) {
            is ApiResult.Success -> ResultWrapper.Success(result.value.toEntity())
            is ApiResult.GenericError -> ResultWrapper.Error(result.error?.message, result.code)
            is ApiResult.NetworkError -> ResultWrapper.Error(result.error?.message)
        }
    }
}