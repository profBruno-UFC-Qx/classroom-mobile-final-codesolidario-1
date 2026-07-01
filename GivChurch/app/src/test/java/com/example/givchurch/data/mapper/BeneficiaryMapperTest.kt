package com.example.givchurch.data.mapper

import com.example.givchurch.data.mock.BeneficiaryMockData
import org.junit.Assert.assertEquals
import org.junit.Test

class BeneficiaryMapperTest {

    @Test
    fun `should map beneficiary entity to domain model with correct fields`() {
        val entity = BeneficiaryMockData.entityList.first()

        val domain = entity.toDomain()

        assertEquals(entity.id, domain.id)
        assertEquals(entity.name, domain.name)
        assertEquals(entity.phoneNumber, domain.phoneNumber)
        assertEquals(entity.address, domain.address)
        assertEquals(entity.observations, domain.observations)
        assertEquals(entity.createBy, domain.createBy)
    }

    @Test
    fun `should map beneficiary domain model to database entity with correct fields`() {
        val domain = BeneficiaryMockData.domainList.last()

        val entity = domain.toEntity()

        assertEquals(domain.id, entity.id)
        assertEquals(domain.name, entity.name)
        assertEquals(domain.phoneNumber, entity.phoneNumber)
        assertEquals(domain.address, entity.address)
        assertEquals(domain.observations, entity.observations)
        assertEquals(domain.createBy, entity.createBy)
    }

    @Test
    fun `should map entire entity list to domain list successfully`() {
        val entities = BeneficiaryMockData.entityList

        val domains = entities.map { it.toDomain() }

        assertEquals(entities.size, domains.size)
        assertEquals(entities[0].id, domains[0].id)
        assertEquals(entities[0].name, domains[0].name)
        assertEquals(entities[1].id, domains[1].id)
        assertEquals(entities[1].name, domains[1].name)
    }
}
