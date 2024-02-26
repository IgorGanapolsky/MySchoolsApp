package com.cnh.samples.apps.schools.di

import com.cnh.samples.apps.schools.api.SchoolService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideSchoolService(): SchoolService {
        return SchoolService.create()
    }
}
