package com.cnh.samples.apps.schools.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the [MySchool] class.
 */
@Dao
interface MySchoolDao {
    @Query("SELECT * FROM my_schools")
    fun getMySchools(): Flow<List<SchoolItems>>

    @Insert
    suspend fun insertMySchool(mySchool: MySchool): Long

    @Delete
    suspend fun deleteMySchool(mySchool: MySchool)
}
