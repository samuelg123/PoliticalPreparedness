package com.example.android.politicalpreparedness.data.datasource.remote

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.android.politicalpreparedness.data.models.ElectionResponse
import com.example.android.politicalpreparedness.data.models.RepresentativeResponse
import com.example.android.politicalpreparedness.data.models.VoterInfoResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

private const val BASE_URL = "https://www.googleapis.com/civicinfo/v2/"

private val moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
//    .add(DateAdapter()) // alternative custom date adapter
    .add(ElectionAdapter())
    .build()

/**
 *  Documentation for the Google Civics API Service can be found at https://developers.google.com/civic-information/docs/v2
 */

interface CivicsApiService {
    @GET("elections")
    suspend fun getElections(): ElectionResponse

    @GET("voterinfo")
    suspend fun getVoterInfo(
        @Query("address") address: String,// From ElectionResponse -> ElectionDTO -> Division ->  state + country
        @Query("electionId") electionId: Long? = null, // From ElectionResponse -> ElectionDTO -> Division -> id
        @Query("officialOnly") officialOnly: Boolean? = null
    ): VoterInfoResponse

    @GET("representatives")
    suspend fun getRepresentativesByAddress(
        @Query("address") address: String? = null,
        @Query("includeOffices") includeOffices: Boolean? = null,
        @Query("levels") levels: String? = null,
        @Query("roles") roles: String? = null
    ): RepresentativeResponse

    @GET("representatives/{ocdId}")
    suspend fun getRepresentativeInfoByDivision(
        @Path("ocdId") ocdId: String,
        @Query("levels") levels: String? = null,
        @Query("recursive") recursive: Boolean? = null,
        @Query("roles") roles: String? = null
    ): RepresentativeResponse
}

object CivicsApi {
    fun create(chuckerInterceptor: ChuckerInterceptor): CivicsApiService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(CivicsHttpClient.getClient(chuckerInterceptor))
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(CivicsApiService::class.java)
    }
}