package com.example.givchurch.data.repository

import com.example.givchurch.data.local.dao.DonationDao
import com.example.givchurch.data.mapper.toDomain
import com.example.givchurch.data.mapper.toEntity
import com.example.givchurch.domain.model.Donation as DonationDomain
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.repository.DonationRepository
import com.example.givchurch.domain.repository.enums.SortDirection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DonationRepositoryImpl(
    private val donationDao: DonationDao
) : DonationRepository {

    override fun getAll(direction: SortDirection?, limit: Int?, createBy: String): Flow<List<DonationDomain>> {
        val sqlLimit = limit ?: -1
        val sqlDirection = direction ?: SortDirection.ASC
        return donationDao.getAll(sqlDirection.name, sqlLimit, createBy).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getById(id: Int, createBy: String): Flow<DonationDomain?> {
        return donationDao.getById(id, createBy).map { it?.toDomain() }
    }

    override fun searchAndFilter(name: String, category: DonationCategory?, createBy: String): Flow<List<DonationDomain>> {
        return donationDao.searchAndFilter(name.trim(), category, createBy).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun create(donation: DonationDomain): Boolean {
        val entity = donation.toEntity()
        val donationExists = donationDao.checkExists(
            name = entity.name,
            beneficiaryId = entity.beneficiaryId,
            createBy = entity.createBy
        )

        if (donationExists) {
            return false
        }

        val rowId = donationDao.insert(entity)
        return rowId > 0
    }

    override suspend fun update(updatedDonation: DonationDomain): Boolean {
        val rowsAffected = donationDao.update(updatedDonation.toEntity())
        return rowsAffected > 0
    }

    override suspend fun delete(id: Int, createBy: String): Boolean {
        val rowsDeleted = donationDao.deleteById(id, createBy)
        return rowsDeleted > 0
    }
}
