package com.cnh.samples.apps.schools.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the [School] class.
 */
@Dao
interface SchoolDao {
    @Query("SELECT * FROM schools ORDER BY school_name")
    fun getSchools(): Flow<List<School>>

    @Query("SELECT * FROM schools WHERE dbn = :schoolDbn")
    fun getSchoolById(schoolDbn: String): Flow<School>

    @Upsert
    suspend fun upsertAll(schools: List<School>)
}
