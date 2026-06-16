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

    override fun getAll(): Flow<List<BeneficiaryDomain>> {
        return beneficiaryDao.getAll().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getById(id: Int): BeneficiaryDomain? {
        return beneficiaryDao.getById(id)?.toDomain()
    }

    override fun getByName(name: String): Flow<List<BeneficiaryDomain>> {
        val flowResult = if (name.isBlank()) beneficiaryDao.getAll() else beneficiaryDao.getByName(name)
        return flowResult.map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getByCreator(userId: Int): Flow<List<BeneficiaryDomain>> {
        return beneficiaryDao.getByCreator(userId).map { list ->
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

    override suspend fun delete(id: Int): Boolean {
        val rowsDeleted = beneficiaryDao.deleteById(id)
        return rowsDeleted > 0
    }
}
