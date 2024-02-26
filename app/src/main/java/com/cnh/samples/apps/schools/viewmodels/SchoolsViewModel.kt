package com.cnh.samples.apps.schools.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cnh.samples.apps.schools.data.School
import com.cnh.samples.apps.schools.data.SchoolRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * The ViewModel for All Schools list.
 */
@HiltViewModel
class SchoolsViewModel
@Inject
internal constructor(schoolRepository: SchoolRepository) : ViewModel() {
    val schools: LiveData<List<School>> = schoolRepository.getSchools().asLiveData()
}
