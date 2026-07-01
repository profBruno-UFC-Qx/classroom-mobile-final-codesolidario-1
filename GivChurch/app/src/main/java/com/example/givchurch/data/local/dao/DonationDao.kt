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
        WHERE createBy = :createBy
        ORDER BY 
            CASE WHEN :direction = 'ASC' THEN dueDate END ASC,
            CASE WHEN :direction = 'DESC' THEN dueDate END DESC
        LIMIT :limit
    """)
    fun getAll(direction: String, limit: Int, createBy: String): Flow<List<Donation>>

    @Query("SELECT * FROM donations WHERE id = :id AND createBy = :createBy LIMIT 1")
    fun getById(id: Int, createBy: String): Flow<Donation?>

    @Query("""
        SELECT * FROM donations 
        WHERE createBy = :createBy
        AND (:name = '' OR name LIKE '%' || :name || '%') 
        AND (:category IS NULL OR category = :category)
    """)
    fun searchAndFilter(name: String, category: DonationCategory?, createBy: String): Flow<List<Donation>>

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM donations 
            WHERE LOWER(name) = LOWER(:name) 
            AND beneficiaryId = :beneficiaryId 
            AND createBy = :createBy 
            LIMIT 1
        )
    """)
    suspend fun checkExists(name: String, beneficiaryId: Int, createBy: String): Boolean

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(donation: Donation): Long

    @Update
    suspend fun update(donation: Donation): Int

    @Query("DELETE FROM donations WHERE id = :id AND createBy = :createBy")
    suspend fun deleteById(id: Int, createBy: String): Int
}
