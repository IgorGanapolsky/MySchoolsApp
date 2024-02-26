package com.cnh.samples.apps.schools.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cnh.samples.apps.schools.data.SchoolRepository
import com.cnh.samples.apps.schools.data.MySchoolsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The ViewModel for School Details screen.
 */
@HiltViewModel
class SchoolDetailsViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    schoolRepository: SchoolRepository,
    private val mySchoolsRepository: MySchoolsRepository,
) : ViewModel() {
    private val schoolId: String = savedStateHandle.get<String>(SCHOOL_ID_SAVED_STATE_KEY)!!

    val school = schoolRepository.getSchool(schoolId).asLiveData()

    private val _showSnackbar = MutableLiveData(false)
    val showSnackbar: LiveData<Boolean>
        get() = _showSnackbar

    fun addSchoolToMySchools() {
        viewModelScope.launch {
            mySchoolsRepository.createMySchool(school.value)
            _showSnackbar.value = true
        }
    }

    fun dismissSnackbar() {
        _showSnackbar.value = false
    }

    companion object {
        private const val SCHOOL_ID_SAVED_STATE_KEY = "schoolId"
    }
}
