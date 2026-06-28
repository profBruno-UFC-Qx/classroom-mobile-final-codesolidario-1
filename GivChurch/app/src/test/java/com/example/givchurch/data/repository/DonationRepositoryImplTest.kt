package com.example.givchurch.data.repository

import com.example.givchurch.data.local.dao.DonationDao
import com.example.givchurch.data.mock.DonationMockData
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.repository.DonationRepository
import com.example.givchurch.domain.repository.enums.SortDirection
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DonationRepositoryImplTest {

    private val donationDao: DonationDao = mockk()
    private lateinit var donationRepository: DonationRepository
    private val firebaseUserId = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"

    @Before
    fun setUp() {
        donationRepository = DonationRepositoryImpl(donationDao)
    }

    @Test
    @Suppress("UNUSED_VARIABLE")
    fun `getAll should emit mapped domain list from dao with default filters`() = runTest {
        val entities = DonationMockData.entityList
        every { donationDao.getAll(SortDirection.ASC.name, -1, firebaseUserId) } returns flowOf(entities)

        val result = donationRepository.getAll(createBy = firebaseUserId).first()

        assertEquals(entities.size, result.size)
        assertEquals(entities.first().name, result.first().name)
        verify(exactly = 1) {
            val unused = donationDao.getAll(SortDirection.ASC.name, -1, firebaseUserId)
        }
    }

    @Test
    @Suppress("UNUSED_VARIABLE")
    fun `getById should emit mapped domain model when donation exists`() = runTest {
        val id = 1
        val entity = DonationMockData.entityList.first()
        every { donationDao.getById(id, firebaseUserId) } returns flowOf(entity)

        val result = donationRepository.getById(id, firebaseUserId).first()

        assertEquals(entity.id, result?.id)
        assertEquals(entity.name, result?.name)
        verify(exactly = 1) {
            val unused = donationDao.getById(id, firebaseUserId)
        }
    }

    @Test
    @Suppress("UNUSED_VARIABLE")
    fun `getById should emit null when donation does not exist`() = runTest {
        val id = 99
        every { donationDao.getById(id, firebaseUserId) } returns flowOf(null)

        val result = donationRepository.getById(id, firebaseUserId).first()

        assertNull(result)
        verify(exactly = 1) {
            val unused = donationDao.getById(id, firebaseUserId)
        }
    }

    @Test
    @Suppress("UNUSED_VARIABLE")
    fun `searchAndFilter should invoke dao passing cleaned parameters`() = runTest {
        val query = "Cesta"
        val category = DonationCategory.FOOD
        val entities = listOf(DonationMockData.entityList.first())
        every { donationDao.searchAndFilter(query, category, firebaseUserId) } returns flowOf(entities)

        val result = donationRepository.searchAndFilter(query, category, firebaseUserId).first()

        assertEquals(1, result.size)
        verify(exactly = 1) {
            val unused = donationDao.searchAndFilter(query, category, firebaseUserId)
        }
    }

    @Test
    fun `create should return false when donation record already exists`() = runTest {
        val domain = DonationMockData.domainList.first()
        coEvery { donationDao.checkExists(domain.name, domain.beneficiaryId, domain.createBy) } returns true

        val result = donationRepository.create(domain)

        assertFalse(result)
        coVerify(exactly = 0) { donationDao.insert(any()) }
    }

    @Test
    fun `create should return true when database insertion succeeds`() = runTest {
        val domain = DonationMockData.domainList.first()
        coEvery { donationDao.checkExists(domain.name, domain.beneficiaryId, domain.createBy) } returns false
        coEvery { donationDao.insert(any()) } returns 1L

        val result = donationRepository.create(domain)

        assertTrue(result)
    }

    @Test
    fun `update should return true when data object is modified in database`() = runTest {
        val domain = DonationMockData.domainList.first()
        coEvery { donationDao.update(any()) } returns 1

        val result = donationRepository.update(domain)

        assertTrue(result)
    }

    @Test
    fun `delete should return true when targeting row code is dropped`() = runTest {
        val id = 1
        coEvery { donationDao.deleteById(id, firebaseUserId) } returns 1

        val result = donationRepository.delete(id, firebaseUserId)

        assertTrue(result)
    }
}
