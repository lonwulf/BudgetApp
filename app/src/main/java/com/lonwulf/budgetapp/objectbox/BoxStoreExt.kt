package com.lonwulf.budgetapp.objectbox

import android.util.Log
import com.lonwulf.budgetapp.objectbox.service.BoxStoreService

interface BoxStoreObject {
    var objectBoxId: Long
}

fun <T> catchException(issueWarning: Boolean = true, throwingLambda: () -> T): T? {
    return try {
        throwingLambda()
    } catch (ex: Exception) {
        if (issueWarning) Log.e("Caught exception.", ex.message ?: "An error has occurred")
        null
    }
}

inline infix fun <reified T: BoxStoreObject> BoxStoreService.store(t:T):Long{
    catchException(issueWarning = false) { getBoxStore().boxFor(t.javaClass).attach(t) }
    return getBoxStore().boxFor(T::class.java).put(t)
}

inline infix fun <reified T: BoxStoreObject> BoxStoreService.store(t:List<T>){
    Log.e("TAG BS", t.toString())
    catchException(issueWarning = false) { getBoxStore().boxFor(t.javaClass).attach(t) }
    return getBoxStore().boxFor(T::class.java).put(t)
}