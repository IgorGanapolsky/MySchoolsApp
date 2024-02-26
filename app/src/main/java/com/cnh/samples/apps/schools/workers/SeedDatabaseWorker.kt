package com.cnh.samples.apps.schools.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cnh.samples.apps.schools.data.AppDatabase
import com.cnh.samples.apps.schools.data.School
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result =
        withContext(Dispatchers.IO) {
            try {
                val filename = inputData.getString(KEY_FILENAME)
                if (filename != null) {
                    applicationContext.assets.open(filename).use { inputStream ->
                        JsonReader(inputStream.reader()).use { jsonReader ->
                            val schoolType = object : TypeToken<List<School>>() {}.type
                            val school: List<School> = Gson().fromJson(jsonReader, schoolType)

                            val database = AppDatabase.getInstance(applicationContext)
                            database.schoolDao().upsertAll(school)

                            Result.success()
                        }
                    }
                } else {
                    Log.e(TAG, "Error seeding database - no valid filename")
                    Result.failure()
                }
            } catch (ex: Exception) {
                Log.e(TAG, "Error seeding database", ex)
                Result.failure()
            }
        }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
        const val KEY_FILENAME = "HIGH_SCHOOLS_DATA_FILENAME"
    }
}
