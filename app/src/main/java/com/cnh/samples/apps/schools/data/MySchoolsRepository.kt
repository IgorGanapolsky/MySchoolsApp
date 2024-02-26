package com.cnh.samples.apps.schools.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MySchoolsRepository
@Inject
constructor(
    private val mySchoolDao: MySchoolDao,
) {
    suspend fun createMySchool(school: School?) {
        val mySchool = MySchool(
            school?.schoolDbn ?: "",
            school?.schoolName ?: "",
            school?.borough ?: "",
            school?.overviewParagraph ?: "",
            school?.academicOpportunities1 ?: "",
        )
        mySchoolDao.insertMySchool(mySchool)
    }

    suspend fun removeMySchool(mySchool: MySchool) {
        mySchoolDao.deleteMySchool(mySchool)
    }

    fun getMySchools() = mySchoolDao.getMySchools()
}
