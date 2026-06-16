package com.example.givchurch.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.givchurch.data.local.dao.dto.DonationMonthGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface DashboardDao {

    @Query("SELECT TOTAL(quantity) FROM donations")
    fun getTotalDonations(): Flow<Int>

    @Query("SELECT TOTAL(quantity) FROM donations WHERE status = 'PENDING'")
    fun getPendingDonations(): Flow<Int>

    @Query("SELECT TOTAL(quantity) FROM donations WHERE status = 'DELIVERED'")
    fun getDeliveredDonations(): Flow<Int>

    @Query("SELECT COUNT(*) FROM beneficiaries")
    fun getTotalBeneficiaries(): Flow<Int>

    @Query("""
        SELECT strftime('%m', createdAt) as monthStr, quantity 
        FROM donations 
        WHERE strftime('%Y', createdAt) = :currentYear
    """)
    fun getDonationsForYear(currentYear: String): Flow<List<DonationMonthGroup>>
}