package com.fekent.poetryapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthoredDao {
    @Insert
    suspend fun insertPoem(authored: Authored)

    @Delete
    suspend fun deletePoem(authored: Authored)

    @Update
    suspend fun updatePoem(authored: Authored)

    @Query ("SELECT * FROM authored")
    fun allAuthored(): Flow<List<Authored>>

    @Query("SELECT * FROM authored WHERE authored.id = :authoredId LIMIT 1")
    suspend fun getPoem(authoredId: Int): Authored
}

@Dao
interface SavedDao {
    @Insert
    suspend fun insertPoem(saved: Saved)

    @Delete
    suspend fun deletePoem(saved: Saved)

    @Update
    suspend fun updatePoem(saved: Saved)

    @Query ("SELECT * FROM saved")
    fun allSaved(): Flow<List<Saved>>

    @Query("SELECT * FROM saved WHERE saved.id = :savedId LIMIT 1")
    suspend fun getPoem(savedId: Int): Saved
}

