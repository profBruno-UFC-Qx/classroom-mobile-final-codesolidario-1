package com.example.givchurch.domain.repository

import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.repository.enums.SortDirection
import kotlinx.coroutines.flow.Flow

interface DonationRepository {
    fun getAll(direction: SortDirection = SortDirection.DESC, limit : Int? = null): List<Donation>
    fun getById(id: Int): Donation?
    fun getByCreator(userId: Int): List<Donation>
    fun searchAndFilter(name: String, category: DonationCategory?): List<Donation>
    fun create(donation: Donation): Boolean
    fun update(updatedDonation: Donation): Boolean
    fun delete(id: Int): Boolean
}
