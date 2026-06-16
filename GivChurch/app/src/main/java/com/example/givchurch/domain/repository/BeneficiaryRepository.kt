package com.example.givchurch.domain.repository

import com.example.givchurch.domain.model.Beneficiary
import kotlinx.coroutines.flow.Flow

interface BeneficiaryRepository {
    fun getAll(): Flow<List<Beneficiary>>
    suspend fun getById(id: Int): Beneficiary?
    fun getByName(name: String): Flow<List<Beneficiary>>
    fun getByCreator(userId: Int): Flow<List<Beneficiary>>
    suspend fun create(beneficiary: Beneficiary): Boolean
    suspend fun update(updatedBeneficiary: Beneficiary): Boolean
    suspend fun delete(id: Int): Boolean
}
