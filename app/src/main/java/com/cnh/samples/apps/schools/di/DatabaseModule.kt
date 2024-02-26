package com.cnh.samples.apps.schools.di

import android.content.Context
import com.cnh.samples.apps.schools.data.AppDatabase
import com.cnh.samples.apps.schools.data.MySchoolDao
import com.cnh.samples.apps.schools.data.SchoolDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideSchoolDao(appDatabase: AppDatabase): SchoolDao {
        return appDatabase.schoolDao()
    }

    @Provides
    fun provideMySchoolDao(appDatabase: AppDatabase): MySchoolDao {
        return appDatabase.mySchoolDao()
    }
}
