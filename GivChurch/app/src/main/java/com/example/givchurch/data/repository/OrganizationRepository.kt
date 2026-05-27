package com.example.givchurch.data.repository

import com.example.givchurch.data.mock.OrganizationMockData
import com.example.givchurch.data.model.Organization

class OrganizationRepository {

    fun getAll(): List<Organization> {
        return OrganizationMockData.organizations
    }

    fun getById(id: Int): Organization? {
        return OrganizationMockData.organizations.find { it.id == id }
    }

    fun getByName(name: String): List<Organization> {
        if (name.isBlank()) return getAll()

        return OrganizationMockData.organizations.filter {
            it.name.contains(name, ignoreCase = true)
        }
    }

    fun getByCreator(userId: Int): List<Organization> {
        return OrganizationMockData.organizations.filter { it.createBy == userId }
    }

    fun create(organization: Organization): Boolean {
        val organizationExists = OrganizationMockData.organizations.any {
            it.name.equals(organization.name, ignoreCase = true) && it.createBy == organization.createBy
        }

        if (organizationExists) {
            return false
        }

        val nextId = (OrganizationMockData.organizations.maxOfOrNull { it.id } ?: 0) + 1

        val newOrganizationWithId = organization.copy(id = nextId)

        OrganizationMockData.organizations.add(newOrganizationWithId)
        return true
    }

    fun update(updatedOrganization: Organization): Boolean {
        val index = OrganizationMockData.organizations.indexOfFirst {
            it.id == updatedOrganization.id
        }

        if (index != -1) {
            OrganizationMockData.organizations[index] = updatedOrganization
            return true
        }

        return false
    }

    fun delete(id: Int): Boolean {
        return OrganizationMockData.organizations.removeIf { it.id == id }
    }
}