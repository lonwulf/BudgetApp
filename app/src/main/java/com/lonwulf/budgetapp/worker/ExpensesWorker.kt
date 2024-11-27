package com.lonwulf.budgetapp.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.lonwulf.budgetapp.domain.usecase.ExpensesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

@HiltWorker
class ExpensesWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted workerParameters: WorkerParameters,
    private val expensesUseCase: ExpensesUseCase
) :
    CoroutineWorker(ctx, workerParameters) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            val deleteInsertJob = listOf(async {
                expensesUseCase.deleteCache()
            }, async {
                expensesUseCase.insertAllItems().collect { /*ignore result*/ }
            })
            deleteInsertJob.awaitAll()
            Result.success()
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure()
        }
    }
}