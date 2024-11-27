package com.lonwulf.budgetapp.util

data class GenericUseCaseResult<out T>(
    val result: T,
    val isSuccessful: Boolean, val msg: String? = ""
)
