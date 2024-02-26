package com.cnh.samples.apps.schools.viewmodels

import androidx.lifecycle.ViewModel
import com.cnh.samples.apps.schools.data.SchoolItems
import com.cnh.samples.apps.schools.data.MySchoolsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MySchoolsTabViewModel
@Inject
internal constructor(mySchoolsRepository: MySchoolsRepository) : ViewModel() {
    val mySchoolItems: Flow<List<SchoolItems>> = mySchoolsRepository.getMySchools()
}
