package com.example.givchurch.domain.repository

import com.example.givchurch.domain.model.Beneficiary

interface BeneficiaryRepository {
    fun getAll(): List<Beneficiary>
    fun getById(id: Int): Beneficiary?
    fun getByName(name: String): List<Beneficiary>
    fun getByCreator(userId: Int): List<Beneficiary>
    fun create(beneficiary: Beneficiary): Boolean
    fun update(updatedBeneficiary: Beneficiary): Boolean
    fun delete(id: Int): Boolean
}
