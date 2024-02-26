package com.cnh.samples.apps.schools.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.cnh.samples.apps.schools.utilities.DATABASE_NAME
import com.cnh.samples.apps.schools.utilities.HIGH_SCHOOLS_DATA_FILENAME
import com.cnh.samples.apps.schools.workers.SeedDatabaseWorker
import com.cnh.samples.apps.schools.workers.SeedDatabaseWorker.Companion.KEY_FILENAME

/**
 * The Room database for this app
 */
@Database(entities = [MySchool::class, School::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mySchoolDao(): MySchoolDao

    abstract fun schoolDao(): SchoolDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database with default data.
        // See this article for more details: https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                                .setInputData(workDataOf(KEY_FILENAME to HIGH_SCHOOLS_DATA_FILENAME))
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    },
                )
                .build()
        }
    }
}
