package com.cnh.samples.apps.schools.data

import androidx.room.Embedded
import androidx.room.Relation

/**
 * This class captures the relationship between a [School] and a user's [MySchool], which is
 * used by Room to fetch the related entities.
 */
data class SchoolItems(
    @Embedded
    val school: School,
    @Relation(parentColumn = "dbn", entityColumn = "dbn")
    val mySchools: List<MySchool> = emptyList(),
)
