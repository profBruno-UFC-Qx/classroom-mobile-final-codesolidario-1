package com.example.givchurch.data.repository

import com.example.givchurch.data.local.dao.BeneficiaryDao
import com.example.givchurch.data.mock.BeneficiaryMockData
import com.example.givchurch.domain.repository.BeneficiaryRepository
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

class BeneficiaryRepositoryImplTest {

    private val beneficiaryDao: BeneficiaryDao = mockk()
    private lateinit var beneficiaryRepository: BeneficiaryRepository
    private val firebaseUserId = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"

    @Before
    fun setUp() {
        beneficiaryRepository = BeneficiaryRepositoryImpl(beneficiaryDao)
    }

    @Test
    fun `getAll should emit mapped domain list from dao`() = runTest {
        val entities = BeneficiaryMockData.entityList
        every { beneficiaryDao.getAll(firebaseUserId) } returns flowOf(entities)

        val result = beneficiaryRepository.getAll(firebaseUserId).first()

        assertEquals(entities.size, result.size)
        assertEquals(entities.first().name, result.first().name)
    }

    @Test
    fun `getById should return mapped domain when entity exists`() = runTest {
        val id = 1
        val entity = BeneficiaryMockData.entityList.first()
        coEvery { beneficiaryDao.getById(id, firebaseUserId) } returns entity

        val result = beneficiaryRepository.getById(id, firebaseUserId)

        assertEquals(entity.id, result?.id)
        assertEquals(entity.name, result?.name)
    }

    @Test
    fun `getById should return null when entity does not exist`() = runTest {
        val id = 99
        coEvery { beneficiaryDao.getById(id, firebaseUserId) } returns null

        val result = beneficiaryRepository.getById(id, firebaseUserId)

        assertNull(result)
    }

    @Test
    @Suppress("UNUSED_VARIABLE")
    fun `getByName should call getAll when name query is blank`() = runTest {
        val entities = BeneficiaryMockData.entityList
        every { beneficiaryDao.getAll(firebaseUserId) } returns flowOf(entities)

        val result = beneficiaryRepository.getByName("", firebaseUserId).first()

        assertEquals(entities.size, result.size)
        verify(exactly = 1) {
            val unused = beneficiaryDao.getAll(firebaseUserId)
        }
        verify(exactly = 0) {
            val unused = beneficiaryDao.getByName(any(), any())
        }
    }

    @Test
    @Suppress("UNUSED_VARIABLE")
    fun `getByName should call getByName when name query is provided`() = runTest {
        val query = "João"
        val entities = listOf(BeneficiaryMockData.entityList.first())
        every { beneficiaryDao.getByName(query, firebaseUserId) } returns flowOf(entities)

        val result = beneficiaryRepository.getByName(query, firebaseUserId).first()

        assertEquals(1, result.size)
        verify(exactly = 1) {
            val unused = beneficiaryDao.getByName(query, firebaseUserId)
        }
    }

    @Test
    fun `create should return false when beneficiary already exists`() = runTest {
        val domain = BeneficiaryMockData.domainList.first()
        coEvery { beneficiaryDao.checkExists(domain.name, domain.createBy) } returns true

        val result = beneficiaryRepository.create(domain)

        assertFalse(result)
        coVerify(exactly = 0) { beneficiaryDao.insert(any()) }
    }

    @Test
    fun `create should return true when insert returns valid row id`() = runTest {
        val domain = BeneficiaryMockData.domainList.first()
        coEvery { beneficiaryDao.checkExists(domain.name, domain.createBy) } returns false
        coEvery { beneficiaryDao.insert(any()) } returns 1L

        val result = beneficiaryRepository.create(domain)

        assertTrue(result)
    }

    @Test
    fun `update should return true when row is successfully affected`() = runTest {
        val domain = BeneficiaryMockData.domainList.first()
        coEvery { beneficiaryDao.update(any()) } returns 1

        val result = beneficiaryRepository.update(domain)

        assertTrue(result)
    }

    @Test
    fun `delete should return true when row is successfully deleted`() = runTest {
        val id = 1
        coEvery { beneficiaryDao.deleteById(id, firebaseUserId) } returns 1

        val result = beneficiaryRepository.delete(id, firebaseUserId)

        assertTrue(result)
    }
}
