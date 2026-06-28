package com.example.givchurch.data.mapper

import com.example.givchurch.data.mock.UserMockData
import org.junit.Assert.assertEquals
import org.junit.Test

class UserMapperTest {

    @Test
    fun `should map user domain model to local user firebase entity with correct fields`() {
        val domain = UserMockData.domainUserList.first()

        val localUser = domain.toLocalUser()

        assertEquals(domain.id, localUser.id)
        assertEquals(domain.firstname, localUser.firstname)
        assertEquals(domain.lastname, localUser.lastname)
        assertEquals(domain.email, localUser.email)
        assertEquals(domain.imageUrl, localUser.imageUrl)
    }

    @Test
    fun `should map local user firebase entity to domain model using default password value`() {
        val localUser = UserMockData.localUserList.last()

        val domain = localUser.toRemoteUser()

        assertEquals(localUser.id, domain.id)
        assertEquals(localUser.firstname, domain.firstname)
        assertEquals(localUser.lastname, domain.lastname)
        assertEquals(localUser.email, domain.email)
        assertEquals(localUser.imageUrl, domain.imageUrl)
        assertEquals("", domain.password)
    }

    @Test
    fun `should map local user firebase entity to domain model using custom password value`() {
        val localUser = UserMockData.localUserList.first()
        val customPassword = "myCustomPassword99"

        val domain = localUser.toRemoteUser(password = customPassword)

        assertEquals(localUser.id, domain.id)
        assertEquals(localUser.firstname, domain.firstname)
        assertEquals(localUser.lastname, domain.lastname)
        assertEquals(localUser.email, domain.email)
        assertEquals(localUser.imageUrl, domain.imageUrl)
        assertEquals(customPassword, domain.password)
    }
}
