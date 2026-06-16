package com.example.givchurch.domain.repository

import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.repository.enums.SortDirection
import kotlinx.coroutines.flow.Flow

interface DonationRepository {
    fun getAll(direction: SortDirection = SortDirection.DESC, limit: Int? = null): Flow<List<Donation>>
    fun getById(id: Int): Flow<Donation?>
    fun getByCreator(userId: Int): Flow<List<Donation>>
    fun searchAndFilter(name: String, category: DonationCategory?): Flow<List<Donation>>
    suspend fun create(donation: Donation): Boolean
    suspend fun update(updatedDonation: Donation): Boolean
    suspend fun delete(id: Int): Boolean
}
