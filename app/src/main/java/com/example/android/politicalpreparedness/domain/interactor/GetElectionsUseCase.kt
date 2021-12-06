package com.example.android.politicalpreparedness.domain.interactor

import com.example.android.politicalpreparedness.domain.entity.ElectionEntity
import com.example.android.politicalpreparedness.domain.repository.ElectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.android.politicalpreparedness.common.base.entity.ResultWrapper

class GetElectionsUseCase @Inject constructor(val repository: ElectionRepository) {
    suspend fun invoke(): ResultWrapper<List<ElectionEntity>> = repository.getElections()
}