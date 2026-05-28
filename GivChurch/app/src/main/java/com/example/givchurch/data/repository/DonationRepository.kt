package com.example.givchurch.data.repository

import com.example.givchurch.data.mock.DonationMockData
import com.example.givchurch.data.model.Donation
import com.example.givchurch.data.model.enums.DonationCategory

class DonationRepository {

    fun getAll(): List<Donation> {
        return DonationMockData.donations
    }

    fun getById(id: Int): Donation? {
        return DonationMockData.donations.find { it.id == id }
    }

    fun getByCreator(userId: Int): List<Donation> {
        return DonationMockData.donations.filter { it.createBy == userId }
    }

    fun searchAndFilter(name: String, category: DonationCategory?): List<Donation> {
        return DonationMockData.donations.filter { donation ->
            val matchesName = name.isBlank() || donation.name.contains(name, ignoreCase = true)
            val matchesCategory = category == null || donation.category == category
            matchesName && matchesCategory
        }
    }

    fun create(donation: Donation): Boolean {
        val donationExists = DonationMockData.donations.any {
            it.name.equals(donation.name, ignoreCase = true) &&
                    it.beneficiaryId == donation.beneficiaryId &&
                    it.createBy == donation.createBy
        }

        if (donationExists) {
            return false
        }

        val nextId = (DonationMockData.donations.maxOfOrNull { it.id } ?: 0) + 1
        val newDonationWithId = donation.copy(id = nextId)

        DonationMockData.donations.add(newDonationWithId)
        return true
    }

    fun update(updatedDonation: Donation): Boolean {
        val index = DonationMockData.donations.indexOfFirst {
            it.id == updatedDonation.id
        }

        if (index != -1) {
            DonationMockData.donations[index] = updatedDonation
            return true
        }

        return false
    }

    fun delete(id: Int): Boolean {
        return DonationMockData.donations.removeIf { it.id == id }
    }
}
