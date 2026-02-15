package com.example.api_test.localdb

import androidx.room.TypeConverter
import com.example.api_test.ui.SmartType

class SmartTypeConverter {

    @TypeConverter
    fun fromSmartType(type: SmartType): String = type.name

    @TypeConverter
    fun toSmartType(value: String): SmartType = SmartType.valueOf(value)
}