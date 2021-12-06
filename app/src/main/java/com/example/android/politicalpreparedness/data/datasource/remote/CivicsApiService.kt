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

// TODO: Add adapters for Java Date and custom adapter ElectionAdapter (included in project) - OK
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
    //TODO: Add elections API Call - OK
    // USING THIS TO GET LIST OF UPCOMING ELECTIONS PAGE
    @GET("elections")
    suspend fun getElections(): ElectionResponse

    //TODO: Add voterinfo API Call - OK
    // USING THIS TO GET ELECTION INFORMATION (DETAIL PAGE)
    @GET("voterinfo")
    suspend fun getVoterInfo(
        address: String,// From ElectionResponse -> ElectionDTO -> Division ->  state + country
        electionId: Long? = null, // From ElectionResponse -> ElectionDTO -> Division -> id
        officialOnly: Boolean? = null
    ): VoterInfoResponse

    //TODO: Add representatives API Call - OK
    // USING THIS FOR REPRESENTATIVE SEARCH PAGE
    @GET("representatives")
    suspend fun getRepresentativesByAddress(
        address: String? = null,
        includeOffices: Boolean? = null,
        levels: String? = null,
        roles: String? = null
    ): RepresentativeResponse

    @GET("representatives/{ocdId}")
    suspend fun getRepresentativeInfoByDivision(
        @Path("ocdId") ocdId: String,
        levels: String? = null,
        recursive: Boolean? = null,
        roles: String? = null
    ): RepresentativeResponse
}

object CivicsApi {

    fun create(chuckerInterceptor: ChuckerInterceptor): CivicsApiService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .addCallAdapterFactory(CoroutineCallAdapterFactory()) // TODO: CHECK AGAINNNNNN----
            .client(CivicsHttpClient.getClient(chuckerInterceptor))
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(CivicsApiService::class.java)
    }
}