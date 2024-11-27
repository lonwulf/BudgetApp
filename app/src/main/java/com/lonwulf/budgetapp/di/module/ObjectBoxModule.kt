package com.lonwulf.budgetapp.di.module

import android.content.Context
import com.lonwulf.budgetapp.objectbox.entity.MyObjectBox
import com.lonwulf.budgetapp.objectbox.service.BoxStoreService
import com.lonwulf.budgetapp.objectbox.service.BoxStoreServiceImpl
//import com.lonwulf.budgetapp.objectbox.entity.MyObjectBox

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ObjectBoxModule {

    @Provides
    @Singleton
    @Synchronized
    fun provideBoxStoreDB(@ApplicationContext ctx:Context):BoxStoreService{
        return BoxStoreServiceImpl(ctx){ MyObjectBox.builder()}
    }
}