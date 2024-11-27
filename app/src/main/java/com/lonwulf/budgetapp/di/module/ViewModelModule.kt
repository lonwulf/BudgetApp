package com.lonwulf.budgetapp.di.module

import com.lonwulf.budgetapp.domain.repository.Repository
import com.lonwulf.budgetapp.domain.usecase.ExpensesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    fun provideExpensesUseCase(repository: Repository) = ExpensesUseCase(repository)
}