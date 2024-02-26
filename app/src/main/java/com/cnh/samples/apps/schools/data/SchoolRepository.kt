package com.cnh.samples.apps.schools.data

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 *
 * Collecting from the Flows in [SchoolDao] is main-safe.  Room supports Coroutines and moves the
 * query execution off of the main thread.
 */
@Singleton
class SchoolRepository
@Inject
constructor(private val schoolDao: SchoolDao) {
    fun getSchools() = schoolDao.getSchools()

    fun getSchool(schoolId: String) = schoolDao.getSchoolById(schoolId)
}
