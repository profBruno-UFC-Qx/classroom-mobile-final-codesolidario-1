package com.example.givchurch.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.enums.DonationCategory

@Dao
interface DonationDao {

    @Query("""
        SELECT * FROM donations 
        ORDER BY 
            CASE WHEN :direction = 'ASC' THEN dueDate END ASC,
            CASE WHEN :direction = 'DESC' THEN dueDate END DESC
        LIMIT :limit
    """)
    fun getAll(direction: String, limit: Int): List<Donation>

    @Query("SELECT * FROM donations WHERE id = :id")
    fun getById(id: Int): Donation?

    @Query("SELECT * FROM donations WHERE createBy = :userId")
    fun getByCreator(userId: Int): List<Donation>

    @Query("""
        SELECT * FROM donations 
        WHERE (:name = '' OR name LIKE '%' || :name || '%') 
        AND (:category IS NULL OR category = :category)
    """)
    fun searchAndFilter(name: String, category: DonationCategory?): List<Donation>

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM donations 
            WHERE LOWER(name) = LOWER(:name) 
            AND beneficiaryId = :beneficiaryId 
            AND createBy = :createBy 
            LIMIT 1
        )
    """)
    fun checkExists(name: String, beneficiaryId: Int, createBy: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(donation: Donation): Long

    @Update
    fun update(donation: Donation): Int

    @Query("DELETE FROM donations WHERE id = :id")
    fun deleteById(id: Int): Int
}
