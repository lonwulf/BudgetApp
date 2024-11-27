package com.lonwulf.budgetapp.util

sealed class GenericResultState<out T> {
    object Loading : GenericResultState<Nothing>()
    object Empty : GenericResultState<Nothing>()
    data class Success<T>(val result: T? = null) : GenericResultState<T>()
}