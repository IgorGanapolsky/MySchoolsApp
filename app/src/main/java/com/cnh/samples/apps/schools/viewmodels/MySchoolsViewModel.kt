package com.cnh.samples.apps.schools.viewmodels

import com.cnh.samples.apps.schools.data.SchoolItems

class MySchoolsViewModel(schoolItems: SchoolItems) {
    private val school = checkNotNull(schoolItems.school)
    private val mySchools = schoolItems.mySchools[0]

    val schoolName
        get() = school.schoolName
    val schoolBorough
        get() = school.borough
    val schoolOverviewParagraph
        get() = school.overviewParagraph
}
