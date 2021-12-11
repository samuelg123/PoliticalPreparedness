package com.example.android.politicalpreparedness.data.datasource.local.database.dao

import androidx.room.*
import com.example.android.politicalpreparedness.data.models.Election
import kotlinx.coroutines.flow.Flow

@Dao
interface ElectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveElection(election: Election): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveElections(vararg election: Election): LongArray // ids saved

    @Query("SELECT * FROM election_table")
    fun allElections(): Flow<List<Election>>

    @Query("SELECT * FROM election_table WHERE id = :id")
    fun getElectionById(id: Int): Flow<Election?>

    @Update
    suspend fun update(election: Election): Int?

    @Delete
    suspend fun delete(election: Election): Int?

    @Query("DELETE FROM election_table WHERE id = :id")
    suspend fun deleteElectionById(id: Int): Int?

    @Query("DELETE FROM election_table")
    suspend fun deleteAll(): Int //Total row deleted

}