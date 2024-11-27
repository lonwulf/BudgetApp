package com.lonwulf.budgetapp.di.module

import com.lonwulf.budgetapp.objectbox.service.BoxStoreService
import com.lonwulf.budgetapp.objectbox.service.ExpensesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideExpenseService(boxService: BoxStoreService): ExpensesService =
        ExpensesService(boxService)

}