package com.example.givchurch.data.mapper

import com.example.givchurch.data.mock.DonationMockData
import org.junit.Assert.assertEquals
import org.junit.Test

class DonationMapperTest {

    @Test
    fun `should map donation entity to domain model with correct fields`() {
        val entity = DonationMockData.entityList.first()

        val domain = entity.toDomain()

        assertEquals(entity.id, domain.id)
        assertEquals(entity.imageUrl, domain.imageUrl)
        assertEquals(entity.name, domain.name)
        assertEquals(entity.category.name, domain.category.name)
        assertEquals(entity.description, domain.description)
        assertEquals(entity.quantity, domain.quantity)
        assertEquals(entity.beneficiaryId, domain.beneficiaryId)
        assertEquals(entity.createBy, domain.createBy)
        assertEquals(entity.status.name, domain.status.name)
        assertEquals(entity.createdAt, domain.createdAt)
        assertEquals(entity.dueDate, domain.dueDate)
    }

    @Test
    fun `should map donation domain model to database entity with correct fields`() {
        val domain = DonationMockData.domainList.last()

        val entity = domain.toEntity()

        assertEquals(domain.id, entity.id)
        assertEquals(domain.imageUrl, entity.imageUrl)
        assertEquals(domain.name, entity.name)
        assertEquals(domain.category.name, entity.category.name)
        assertEquals(domain.description, entity.description)
        assertEquals(domain.quantity, entity.quantity)
        assertEquals(domain.beneficiaryId, entity.beneficiaryId)
        assertEquals(domain.createBy, entity.createBy)
        assertEquals(domain.status.name, entity.status.name)
        assertEquals(domain.createdAt, entity.createdAt)
        assertEquals(domain.dueDate, entity.dueDate)
    }

    @Test
    fun `should map entire donation entity list to domain list successfully`() {
        val entities = DonationMockData.entityList

        val domains = entities.map { it.toDomain() }

        assertEquals(entities.size, domains.size)
        assertEquals(entities[0].id, domains[0].id)
        assertEquals(entities[0].name, domains[0].name)
        assertEquals(entities[1].id, domains[1].id)
        assertEquals(entities[1].name, domains[1].name)
    }
}
