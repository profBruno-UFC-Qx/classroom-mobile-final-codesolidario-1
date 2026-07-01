package com.example.givchurch.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.givchurch.data.local.converters.Converters
import com.example.givchurch.data.local.dao.BeneficiaryDao
import com.example.givchurch.data.local.dao.DashboardDao
import com.example.givchurch.data.local.dao.DonationDao
import com.example.givchurch.data.local.model.Beneficiary
import com.example.givchurch.data.local.model.Donation

@Database(
    entities = [Beneficiary::class, Donation::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun beneficiaryDao(): BeneficiaryDao
    abstract fun donationDao(): DonationDao
    abstract fun dashboardDao(): DashboardDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "givchurch_database"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration(true)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
