package com.example.android.politicalpreparedness.domain.interactor

import com.example.android.politicalpreparedness.domain.entity.ElectionEntity
import com.example.android.politicalpreparedness.domain.repository.ElectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.android.politicalpreparedness.common.base.entity.ResultWrapper
import com.example.android.politicalpreparedness.domain.entity.VoterInfoEntity
import com.example.android.politicalpreparedness.domain.repository.VoterInfoRepository

class GetVoterInfoUseCase @Inject constructor(private val repository: VoterInfoRepository) {
    suspend fun invoke(param: Param): ResultWrapper<VoterInfoEntity> =
        repository.getVoterInfo(
            address = param.electionEntity.division.toFormattedStringAddress(),
            electionId = param.electionEntity.id.toLong()
        )

    data class Param(val electionEntity: ElectionEntity)
}