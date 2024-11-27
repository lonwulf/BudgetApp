package com.lonwulf.budgetapp.objectbox.service

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.BoxStoreBuilder
import java.util.UUID

interface BoxStoreService {
    fun <T> getBoxStoreModel(classVariant: Class<T>): Box<T>
    fun getBoxStore(): BoxStore
    fun clearAllData()
}

class BoxStoreServiceImpl(
    @ApplicationContext private val ctx: Context,
    private val builderFactory: () -> BoxStoreBuilder
) : BoxStoreService {
    private var boxStore: BoxStore
    private var boxStoreId: String
    private val TAG = BoxStoreService::class.java.simpleName

    init {
        boxStoreId = createBoxStoreId()
        boxStore = openBoxStore()

    }

    private fun createBoxStoreId(): String = UUID.randomUUID().toString()

    override fun <T> getBoxStoreModel(classVariant: Class<T>): Box<T> =
        getBoxStore().boxFor(classVariant)

    override fun getBoxStore(): BoxStore = if (boxStore.isClosed) openBoxStore() else boxStore

    override fun clearAllData() {
        boxStore.close()
        boxStore.deleteAllFiles()
        boxStore = openBoxStore()
    }

    @Synchronized
    fun openBoxStore(isRetry: Boolean = false): BoxStore {
        try {
            val newBoxStore =
                builderFactory().androidContext(ctx).name(boxStoreId).maxReaders(500).build()
            return newBoxStore
        } catch (ex: Exception) {
            Log.e(TAG, "opening boxStore ($isRetry)", ex)
            if (isRetry) {
                throw ex
            } else {
                try {
                    val newBoxStore =
                        builderFactory().androidContext(ctx).name(boxStoreId).maxReaders(500)
                            .build()
                    Log.e(TAG, "Switched to the previous box store.")
                    return newBoxStore
                } catch (e: Exception) {
                    boxStoreId = createBoxStoreId()
                    return openBoxStore(true)
                }
            }
        }
    }

}