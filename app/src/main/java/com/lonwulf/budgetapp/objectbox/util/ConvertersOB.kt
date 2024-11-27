package com.lonwulf.budgetapp.objectbox.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.lonwulf.budgetapp.domain.model.Transactions
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.objectbox.converter.PropertyConverter
import java.lang.reflect.ParameterizedType

class TransactionsConverter : PropertyConverter<List<Transactions>?, String?> {
    private val moshi =
        Moshi.Builder().add(KotlinJsonAdapterFactory()).add(ColorJsonAdapter()).build()
    private val type: ParameterizedType =
        Types.newParameterizedType(List::class.java, Transactions::class.java)
    private val adapter: JsonAdapter<List<Transactions>> by lazy {
        moshi.adapter(type)
    }

    override fun convertToEntityProperty(databaseValue: String?): List<Transactions>? {
        return adapter.fromJson(databaseValue)
    }

    override fun convertToDatabaseValue(entityProperty: List<Transactions>?): String? {
        return adapter.toJson(entityProperty)
    }
}

class ColorJsonAdapter : JsonAdapter<Color>() {
    @FromJson
    override fun fromJson(reader: JsonReader): Color? {
        val colorInt = reader.nextInt()
        return Color(colorInt)
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Color?) {
        writer.value(value?.toArgb())
    }
}