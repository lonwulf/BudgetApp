package com.lonwulf.budgetapp.objectbox.util

import com.lonwulf.budgetapp.domain.model.Transactions
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.objectbox.converter.PropertyConverter
import java.lang.reflect.ParameterizedType
import java.util.Date

class TransactionsConverter : PropertyConverter<List<Transactions>?, String?> {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val type: ParameterizedType =
        Types.newParameterizedType(List::class.java, Transactions::class.java)
    private val adapter: JsonAdapter<List<Transactions>> = moshi.adapter(type)
    override fun convertToEntityProperty(databaseValue: String?): List<Transactions>? {
        return adapter.fromJson(databaseValue)
    }

    override fun convertToDatabaseValue(entityProperty: List<Transactions>?): String? {
        return adapter.toJson(entityProperty)
    }

}

class ExpenseDateConverter : PropertyConverter<Date?, String?> {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val type: ParameterizedType =
        Types.newParameterizedType(Date::class.java)
    private val adapter: JsonAdapter<Date> = moshi.adapter(type)
    override fun convertToEntityProperty(databaseValue: String?): Date? {
        return adapter.fromJson(databaseValue)
    }

    override fun convertToDatabaseValue(entityProperty: Date?): String? {
        return adapter.toJson(entityProperty)
    }

}