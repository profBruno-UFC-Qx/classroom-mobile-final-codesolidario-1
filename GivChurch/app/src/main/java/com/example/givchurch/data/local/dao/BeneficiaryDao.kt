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

    @Query("SELECT * FROM beneficiaries WHERE createBy = :createBy ORDER BY name ASC")
    fun getAll(createBy: String): Flow<List<Beneficiary>>

    @Query("SELECT * FROM beneficiaries WHERE id = :id AND createBy = :createBy LIMIT 1")
    suspend fun getById(id: Int, createBy: String): Beneficiary?

    @Query("SELECT * FROM beneficiaries WHERE name LIKE '%' || :name || '%' AND createBy = :createBy ORDER BY name ASC")
    fun getByName(name: String, createBy: String): Flow<List<Beneficiary>>

    @Query("SELECT EXISTS(SELECT 1 FROM beneficiaries WHERE LOWER(name) = LOWER(:name) AND createBy = :createBy LIMIT 1)")
    suspend fun checkExists(name: String, createBy: String): Boolean

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(beneficiary: Beneficiary): Long

    @Update
    suspend fun update(beneficiary: Beneficiary): Int

    @Query("DELETE FROM beneficiaries WHERE id = :id AND createBy = :createBy")
    suspend fun deleteById(id: Int, createBy: String): Int
}
