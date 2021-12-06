package com.example.android.politicalpreparedness.domain.repository

import com.example.android.politicalpreparedness.common.base.entity.ResultWrapper
import com.example.android.politicalpreparedness.domain.entity.VoterInfoEntity

interface VoterInfoRepository {
    suspend fun getVoterInfo(address: String, electionId: Long): ResultWrapper<VoterInfoEntity>
}