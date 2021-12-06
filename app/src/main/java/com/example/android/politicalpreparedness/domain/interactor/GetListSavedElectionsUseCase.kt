package com.example.android.politicalpreparedness.domain.interactor

import com.example.android.politicalpreparedness.common.base.entity.ResultWrapper
import com.example.android.politicalpreparedness.domain.entity.ElectionEntity
import com.example.android.politicalpreparedness.domain.repository.ElectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListSavedElectionsUseCase @Inject constructor(val repository: ElectionRepository) {
    fun invoke(): Flow<ResultWrapper<List<ElectionEntity>>> = repository.getSavedElections()
}