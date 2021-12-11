package com.example.android.politicalpreparedness.domain.interactor

import com.example.android.politicalpreparedness.common.base.entity.ResultWrapper
import com.example.android.politicalpreparedness.domain.entity.AddressEntity
import com.example.android.politicalpreparedness.domain.entity.ElectionEntity
import com.example.android.politicalpreparedness.domain.repository.ElectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveElectionUseCase @Inject constructor(private val repository: ElectionRepository) {
    suspend fun invoke(param: Param): ResultWrapper<Int> = repository.delete(param.election)

    data class Param(val election: ElectionEntity)
}