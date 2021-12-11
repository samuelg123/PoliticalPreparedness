package com.example.android.politicalpreparedness.data.repository

import com.example.android.politicalpreparedness.common.base.entity.ApiResult
import com.example.android.politicalpreparedness.common.utils.ApiUtils
import com.example.android.politicalpreparedness.data.datasource.remote.CivicsApiService
import com.example.android.politicalpreparedness.domain.entity.VoterInfoEntity
import com.example.android.politicalpreparedness.common.base.entity.ResultWrapper
import com.example.android.politicalpreparedness.data.models.toEntity
import com.example.android.politicalpreparedness.domain.repository.VoterInfoRepository
import javax.inject.Inject

class VoterInfoRepositoryImpl @Inject constructor(
    private val civicsApiService: CivicsApiService,
) : VoterInfoRepository {
    override suspend fun getVoterInfo(address: String, electionId: Long): ResultWrapper<VoterInfoEntity> {
        return when (val result =
            ApiUtils.wrapApiResponse { civicsApiService.getVoterInfo(address, electionId) }) {
            is ApiResult.Success -> ResultWrapper.Success(result.value.toEntity())
            is ApiResult.GenericError -> ResultWrapper.Error(result.error?.message, result.code)
            is ApiResult.NetworkError -> ResultWrapper.Error(result.error?.message)
        }
    }
}