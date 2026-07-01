package com.example.givchurch.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.givchurch.data.local.dao.dto.DonationMonthGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface DashboardDao {

    @Query("SELECT TOTAL(quantity) FROM donations WHERE createBy = :createBy")
    fun getTotalDonations(createBy: String): Flow<Int>

    @Query("SELECT TOTAL(quantity) FROM donations WHERE status = 'PENDING' AND createBy = :createBy")
    fun getPendingDonations(createBy: String): Flow<Int>

    @Query("SELECT TOTAL(quantity) FROM donations WHERE status = 'DELIVERED' AND createBy = :createBy")
    fun getDeliveredDonations(createBy: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM beneficiaries WHERE createBy = :createBy")
    fun getTotalBeneficiaries(createBy: String): Flow<Int>

    @Query("""
        SELECT strftime('%m', createdAt) as monthStr, quantity 
        FROM donations 
        WHERE strftime('%Y', createdAt) = :currentYear AND createBy = :createBy
    """)
    fun getDonationsForYear(currentYear: String, createBy: String): Flow<List<DonationMonthGroup>>
}
