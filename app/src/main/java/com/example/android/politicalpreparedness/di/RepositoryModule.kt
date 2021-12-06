package com.example.android.politicalpreparedness.di

import com.example.android.politicalpreparedness.data.datasource.local.database.dao.ElectionDao
import com.example.android.politicalpreparedness.data.datasource.remote.CivicsApiService
import com.example.android.politicalpreparedness.data.repository.ElectionRepositoryImpl
import com.example.android.politicalpreparedness.data.repository.RepresentativeRepositoryImpl
import com.example.android.politicalpreparedness.data.repository.VoterInfoRepositoryImpl
import com.example.android.politicalpreparedness.domain.repository.ElectionRepository
import com.example.android.politicalpreparedness.domain.repository.RepresentativeRepository
import com.example.android.politicalpreparedness.domain.repository.VoterInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideElectionRepository(
        electionDao: ElectionDao,
        civicsApiService: CivicsApiService,
    ): ElectionRepository = ElectionRepositoryImpl(electionDao, civicsApiService)

    @Provides
    @Singleton
    fun provideRepresentativeRepository(
        civicsApiService: CivicsApiService,
    ): RepresentativeRepository = RepresentativeRepositoryImpl(civicsApiService)

    @Provides
    @Singleton
    fun provideVoterInfoRepository(
        civicsApiService: CivicsApiService,
    ): VoterInfoRepository = VoterInfoRepositoryImpl(civicsApiService)
}