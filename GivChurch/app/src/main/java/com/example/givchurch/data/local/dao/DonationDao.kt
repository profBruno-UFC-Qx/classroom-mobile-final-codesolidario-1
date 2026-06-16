package com.example.givchurch.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.givchurch.data.local.model.Donation
import com.example.givchurch.domain.model.enums.DonationCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface DonationDao {

    @Query("""
        SELECT * FROM donations 
        ORDER BY 
            CASE WHEN :direction = 'ASC' THEN dueDate END ASC,
            CASE WHEN :direction = 'DESC' THEN dueDate END DESC
        LIMIT :limit
    """)
    fun getAll(direction: String, limit: Int): Flow<List<Donation>>

    @Query("SELECT * FROM donations WHERE id = :id")
    fun getById(id: Int): Flow<Donation?>

    @Query("SELECT * FROM donations WHERE createBy = :userId")
    fun getByCreator(userId: Int): Flow<List<Donation>>

    @Query("""
        SELECT * FROM donations 
        WHERE (:name = '' OR name LIKE '%' || :name || '%') 
        AND (:category IS NULL OR category = :category)
    """)
    fun searchAndFilter(name: String, category: DonationCategory?): Flow<List<Donation>>

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM donations 
            WHERE LOWER(name) = LOWER(:name) 
            AND beneficiaryId = :beneficiaryId 
            AND createBy = :createBy 
            LIMIT 1
        )
    """)
    suspend fun checkExists(name: String, beneficiaryId: Int, createBy: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(donation: Donation): Long

    @Update
    suspend fun update(donation: Donation): Int

    @Query("DELETE FROM donations WHERE id = :id")
    suspend fun deleteById(id: Int): Int
}
