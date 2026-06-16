package com.example.givchurch.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.givchurch.data.local.model.Beneficiary
import kotlinx.coroutines.flow.Flow

@Dao
interface BeneficiaryDao {

    @Query("SELECT * FROM beneficiaries ORDER BY name ASC")
    fun getAll(): Flow<List<Beneficiary>>

    @Query("SELECT * FROM beneficiaries WHERE id = :id")
    suspend fun getById(id: Int): Beneficiary?

    @Query("SELECT * FROM beneficiaries WHERE name LIKE '%' || :name || '%' ORDER BY name ASC")
    fun getByName(name: String): Flow<List<Beneficiary>>

    @Query("SELECT * FROM beneficiaries WHERE createBy = :userId")
    fun getByCreator(userId: Int): Flow<List<Beneficiary>>

    @Query("SELECT EXISTS(SELECT 1 FROM beneficiaries WHERE LOWER(name) = LOWER(:name) AND createBy = :createBy LIMIT 1)")
    suspend fun checkExists(name: String, createBy: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(beneficiary: Beneficiary): Long

    @Update
    suspend fun update(beneficiary: Beneficiary): Int

    @Query("DELETE FROM beneficiaries WHERE id = :id")
    suspend fun deleteById(id: Int): Int
}
