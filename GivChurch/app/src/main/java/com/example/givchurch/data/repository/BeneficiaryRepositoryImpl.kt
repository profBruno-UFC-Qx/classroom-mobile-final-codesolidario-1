package com.example.givchurch.data.repository

import com.example.givchurch.data.local.dao.BeneficiaryDao
import com.example.givchurch.data.local.model.Beneficiary
import kotlinx.coroutines.flow.Flow

class BeneficiaryRepositoryImpl(private val beneficiaryDao: BeneficiaryDao) {

    fun getAll(): Flow<List<Beneficiary>> {
        return beneficiaryDao.getAll()
    }

    suspend fun getById(id: Int): Beneficiary? {
        return beneficiaryDao.getById(id)
    }

    fun getByName(name: String): Flow<List<Beneficiary>> {
        if (name.isBlank()) return beneficiaryDao.getAll()
        return beneficiaryDao.getByName(name)
    }

    fun getByCreator(userId: Int): Flow<List<Beneficiary>> {
        return beneficiaryDao.getByCreator(userId)
    }

    suspend fun create(beneficiary: Beneficiary): Boolean {
        val organizationExists = beneficiaryDao.checkExists(beneficiary.name, beneficiary.createBy)

        if (organizationExists) {
            return false
        }

        val rowId = beneficiaryDao.insert(beneficiary)
        return rowId > 0
    }

    suspend fun update(updatedBeneficiary: Beneficiary): Boolean {
        val rowsAffected = beneficiaryDao.update(updatedBeneficiary)
        return rowsAffected > 0
    }

    suspend fun delete(id: Int): Boolean {
        val rowsDeleted = beneficiaryDao.deleteById(id)
        return rowsDeleted > 0
    }
}
