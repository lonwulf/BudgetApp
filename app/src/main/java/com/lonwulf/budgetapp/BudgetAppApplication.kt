package com.lonwulf.budgetapp

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.lonwulf.budgetapp.worker.ExpensesWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltAndroidApp
class BudgetAppApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setExecutor(Executors.newFixedThreadPool(8))
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(if (BuildConfig.DEBUG) android.util.Log.DEBUG else android.util.Log.ERROR)
            .build()


    override fun onCreate() {
        super.onCreate()
        setupExpensesSyncWorker(this)
    }

    private fun setupExpensesSyncWorker(context: Context) {
        val deleteInsertWorker = OneTimeWorkRequestBuilder<ExpensesWorker>().build()
        WorkManager.getInstance(context)
            .beginUniqueWork("syncWorker", ExistingWorkPolicy.REPLACE, deleteInsertWorker).enqueue()
    }

}