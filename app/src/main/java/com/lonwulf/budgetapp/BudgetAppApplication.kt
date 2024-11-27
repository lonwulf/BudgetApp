package com.lonwulf.budgetapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BudgetAppApplication :Application() {

    override fun onCreate() {
        super.onCreate()
    }
}