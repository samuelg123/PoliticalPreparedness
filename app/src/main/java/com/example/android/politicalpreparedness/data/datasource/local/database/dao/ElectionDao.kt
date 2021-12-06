package com.example.android.politicalpreparedness.data.datasource.local.database.dao

import androidx.room.*
import com.example.android.politicalpreparedness.data.models.Election
import kotlinx.coroutines.flow.Flow

@Dao
interface ElectionDao {

    //TODO: Add insert query - OK
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveElection(election: Election): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveElections(vararg election: Election): LongArray // ids saved

    //TODO: Add select all election query - OK
    @Query("SELECT * FROM election_table")
    fun allElections(): Flow<List<Election>>

    //TODO: Add select single election query - OK
    @Query("SELECT * FROM election_table WHERE id = :id")
    fun getElectionById(id: Int): Flow<Election?>

    @Update
    suspend fun update(election: Election): Int?

    //TODO: Add delete query - OK
    @Delete
    suspend fun delete(election: Election): Int?

    @Query("DELETE FROM election_table WHERE id = :id")
    suspend fun deleteElectionById(id: Int): Int?

    //TODO: Add clear query - OK
    @Query("DELETE FROM election_table")
    suspend fun deleteAll(): Int //Total row deleted

}