package com.example.givchurch.data.repository

import com.example.givchurch.data.mock.BeneficiaryMockData
import com.example.givchurch.data.model.Beneficiary

class BeneficiaryRepository {

    fun getAll(): List<Beneficiary> {
        return BeneficiaryMockData.beneficiaries
    }

    fun getById(id: Int): Beneficiary? {
        return BeneficiaryMockData.beneficiaries.find { it.id == id }
    }

    fun getByName(name: String): List<Beneficiary> {
        if (name.isBlank()) return getAll()

        return BeneficiaryMockData.beneficiaries.filter {
            it.name.contains(name, ignoreCase = true)
        }
    }

    fun getByCreator(userId: Int): List<Beneficiary> {
        return BeneficiaryMockData.beneficiaries.filter { it.createBy == userId }
    }

    fun create(beneficiary: Beneficiary): Boolean {
        val organizationExists = BeneficiaryMockData.beneficiaries.any {
            it.name.equals(beneficiary.name, ignoreCase = true) && it.createBy == beneficiary.createBy
        }

        if (organizationExists) {
            return false
        }

        val nextId = (BeneficiaryMockData.beneficiaries.maxOfOrNull { it.id } ?: 0) + 1

        val newOrganizationWithId = beneficiary.copy(id = nextId)

        BeneficiaryMockData.beneficiaries.add(newOrganizationWithId)
        return true
    }

    fun update(updatedBeneficiary: Beneficiary): Boolean {
        val index = BeneficiaryMockData.beneficiaries.indexOfFirst {
            it.id == updatedBeneficiary.id
        }

        if (index != -1) {
            BeneficiaryMockData.beneficiaries[index] = updatedBeneficiary
            return true
        }

        return false
    }

    fun delete(id: Int): Boolean {
        return BeneficiaryMockData.beneficiaries.removeIf { it.id == id }
    }
}