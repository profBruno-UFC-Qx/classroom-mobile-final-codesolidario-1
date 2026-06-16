package com.example.givchurch.data.local.converters

import androidx.room.TypeConverter
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.model.enums.DonationStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it, formatter) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    fun fromCategory(value: String): DonationCategory {
        return DonationCategory.valueOf(value)
    }

    @TypeConverter
    fun categoryToString(category: DonationCategory): String {
        return category.name
    }

    @TypeConverter
    fun fromStatus(value: String): DonationStatus {
        return DonationStatus.valueOf(value)
    }

    @TypeConverter
    fun statusToString(status: DonationStatus): String {
        return status.name
    }
}
