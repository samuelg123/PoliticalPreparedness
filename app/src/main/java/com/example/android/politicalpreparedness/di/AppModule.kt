package com.example.android.politicalpreparedness.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.android.politicalpreparedness.data.datasource.local.database.ElectionDatabase
import com.example.android.politicalpreparedness.data.datasource.local.database.dao.ElectionDao
import com.example.android.politicalpreparedness.data.datasource.remote.CivicsApi
import com.example.android.politicalpreparedness.data.datasource.remote.CivicsApiService
import com.example.android.politicalpreparedness.data.datasource.remote.CivicsHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): ElectionDatabase =
        ElectionDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideNasaApiService(chuckerInterceptor: ChuckerInterceptor): CivicsApiService =
        CivicsApi.create(chuckerInterceptor)

    @Singleton
    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor =
        CivicsHttpClient.createChuckerInterceptor(context)

    // DAO
    @Singleton
    @Provides
    fun provideElectionDao(db: ElectionDatabase): ElectionDao = db.electionDao
}