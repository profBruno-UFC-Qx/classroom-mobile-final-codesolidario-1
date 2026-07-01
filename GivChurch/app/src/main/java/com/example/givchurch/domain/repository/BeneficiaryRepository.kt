package com.example.givchurch.domain.repository

import com.example.givchurch.domain.model.Beneficiary as BeneficiaryDomain
import kotlinx.coroutines.flow.Flow

interface BeneficiaryRepository {
    fun getAll(createBy: String): Flow<List<BeneficiaryDomain>>
    suspend fun getById(id: Int, createBy: String): BeneficiaryDomain?
    fun getByName(name: String, createBy: String): Flow<List<BeneficiaryDomain>>
    suspend fun create(beneficiary: BeneficiaryDomain): Boolean
    suspend fun update(updatedBeneficiary: BeneficiaryDomain): Boolean
    suspend fun delete(id: Int, createBy: String): Boolean
}
