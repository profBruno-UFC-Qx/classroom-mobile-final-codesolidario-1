package com.example.givchurch.data.repository

import com.example.givchurch.data.local.dao.BeneficiaryDao
import com.example.givchurch.data.mapper.toDomain
import com.example.givchurch.data.mapper.toEntity
import com.example.givchurch.domain.repository.BeneficiaryRepository
import com.example.givchurch.domain.model.Beneficiary as BeneficiaryDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BeneficiaryRepositoryImpl(
    private val beneficiaryDao: BeneficiaryDao
) : BeneficiaryRepository {

    override fun getAll(createBy: String): Flow<List<BeneficiaryDomain>> {
        return beneficiaryDao.getAll(createBy).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getById(id: Int, createBy: String): BeneficiaryDomain? {
        return beneficiaryDao.getById(id, createBy)?.toDomain()
    }

    override fun getByName(name: String, createBy: String): Flow<List<BeneficiaryDomain>> {
        val flowResult = if (name.isBlank()) {
            beneficiaryDao.getAll(createBy)
        } else {
            beneficiaryDao.getByName(name, createBy)
        }
        return flowResult.map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun create(beneficiary: BeneficiaryDomain): Boolean {
        val organizationExists = beneficiaryDao.checkExists(beneficiary.name, beneficiary.createBy)

        if (organizationExists) {
            return false
        }

        val rowId = beneficiaryDao.insert(beneficiary.toEntity())
        return rowId > 0
    }

    override suspend fun update(updatedBeneficiary: BeneficiaryDomain): Boolean {
        val rowsAffected = beneficiaryDao.update(updatedBeneficiary.toEntity())
        return rowsAffected > 0
    }

    override suspend fun delete(id: Int, createBy: String): Boolean {
        val rowsDeleted = beneficiaryDao.deleteById(id, createBy)
        return rowsDeleted > 0
    }
}
