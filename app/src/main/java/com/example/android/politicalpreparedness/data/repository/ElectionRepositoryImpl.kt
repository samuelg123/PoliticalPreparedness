package com.example.android.politicalpreparedness.data.repository

import com.example.android.politicalpreparedness.common.base.entity.ApiResult
import com.example.android.politicalpreparedness.common.base.entity.ResultWrapper
import com.example.android.politicalpreparedness.common.extensions.handleErrors
import com.example.android.politicalpreparedness.common.utils.ApiUtils
import com.example.android.politicalpreparedness.data.datasource.local.database.dao.ElectionDao
import com.example.android.politicalpreparedness.data.datasource.remote.CivicsApiService
import com.example.android.politicalpreparedness.data.models.toEntity
import com.example.android.politicalpreparedness.domain.entity.ElectionEntity
import com.example.android.politicalpreparedness.domain.entity.toDTO
import com.example.android.politicalpreparedness.domain.repository.ElectionRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ElectionRepositoryImpl @Inject constructor(
    private val electionDao: ElectionDao,
    private val civicsApiService: CivicsApiService,
) : ElectionRepository {
    override suspend fun getElections(): ResultWrapper<List<ElectionEntity>> =
        when (val result = ApiUtils.wrapApiResponse(civicsApiService::getElections)) {
            is ApiResult.Success -> ResultWrapper.Success(result.value.elections.map { it.toEntity() })
            is ApiResult.GenericError -> ResultWrapper.Error(result.error?.message, result.code)
            is ApiResult.NetworkError -> ResultWrapper.Error(result.error?.message)
        }

    override fun getSavedElectionById(electionId: Int): Flow<ResultWrapper<ElectionEntity?>> =
        electionDao
            .getElectionById(electionId)
            .handleErrors()
            .map {
                when (it) {
                    is ResultWrapper.Success -> ResultWrapper.Success(it.data?.toEntity())
                    is ResultWrapper.Error -> it
                }
            }

    override fun getSavedElections(): Flow<ResultWrapper<List<ElectionEntity>>> =
        electionDao.allElections()
            .handleErrors()
            .map {
                when (it) {
                    is ResultWrapper.Success -> ResultWrapper.Success(it.data.map { dto -> dto.toEntity() })
                    is ResultWrapper.Error -> it
                }
            }

    override suspend fun saveElection(election: ElectionEntity): ResultWrapper<Long> {
        val electionId = electionDao.saveElection(election.toDTO())
        return if (electionId != null) ResultWrapper.Success(electionId)
        else ResultWrapper.Error(message = "Failed to save election")
    }

    override suspend fun saveElections(vararg election: ElectionEntity): ResultWrapper<LongArray> {
        val listElectionIds = electionDao.saveElections(*election.map { it.toDTO() }.toTypedArray())
        return if (listElectionIds.size == election.size) ResultWrapper.Success(listElectionIds)
        else ResultWrapper.Error(message = "Failed to save elections")
    }

    override suspend fun update(election: ElectionEntity): ResultWrapper<Int> {
        val electionId = electionDao.update(election.toDTO())
        return if (electionId != null) ResultWrapper.Success(electionId)
        else ResultWrapper.Error(message = "Failed to update election")
    }

    override suspend fun delete(election: ElectionEntity): ResultWrapper<Int> {
        val electionId = electionDao.delete(election.toDTO())
        return if (electionId != null) ResultWrapper.Success(electionId)
        else ResultWrapper.Error(message = "Failed to delete election")
    }

    override suspend fun deleteElectionById(electionId: Int): ResultWrapper<Int> {
        val deletedElectionId = electionDao.deleteElectionById(electionId)
        return if (deletedElectionId != null) ResultWrapper.Success(deletedElectionId)
        else ResultWrapper.Error(message = "Failed to delete election")
    }

    override suspend fun deleteAll(): ResultWrapper<Int> {
        val deletedCount = electionDao.deleteAll()
        return ResultWrapper.Success(deletedCount)
    }

}