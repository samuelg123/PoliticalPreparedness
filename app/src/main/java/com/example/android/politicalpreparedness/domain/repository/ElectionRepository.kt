package com.example.android.politicalpreparedness.domain.repository

import com.example.android.politicalpreparedness.common.base.entity.ResultWrapper
import com.example.android.politicalpreparedness.domain.entity.ElectionEntity
import kotlinx.coroutines.flow.Flow

interface ElectionRepository {
    suspend fun getElections(): ResultWrapper<List<ElectionEntity>>

    fun getSavedElectionById(electionId: Int): Flow<ResultWrapper<ElectionEntity?>>

    fun getSavedElections(): Flow<ResultWrapper<List<ElectionEntity>>>

    suspend fun saveElection(election: ElectionEntity): ResultWrapper<Long>

    suspend fun saveElections(vararg election: ElectionEntity): ResultWrapper<LongArray>

    suspend fun update(election: ElectionEntity): ResultWrapper<Int>

    suspend fun delete(election: ElectionEntity): ResultWrapper<Int>

    suspend fun deleteElectionById(electionId: Int): ResultWrapper<Int>

    suspend fun deleteAll(): ResultWrapper<Int>
}