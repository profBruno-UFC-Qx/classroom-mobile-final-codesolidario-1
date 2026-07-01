package com.example.givchurch.domain.repository

import com.example.givchurch.domain.model.Donation as DonationDomain
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.repository.enums.SortDirection
import kotlinx.coroutines.flow.Flow

interface DonationRepository {
    fun getAll(direction: SortDirection? = null, limit: Int? = null, createBy: String): Flow<List<DonationDomain>>
    fun getById(id: Int, createBy: String): Flow<DonationDomain?>
    fun searchAndFilter(name: String, category: DonationCategory?, createBy: String): Flow<List<DonationDomain>>
    suspend fun create(donation: DonationDomain): Boolean
    suspend fun update(updatedDonation: DonationDomain): Boolean
    suspend fun delete(id: Int, createBy: String): Boolean
}
