package com.example.givchurch.data.mock

import java.time.LocalDateTime
import com.example.givchurch.data.local.model.Donation as DonationEntity
import com.example.givchurch.domain.model.Donation as DonationDomain
import com.example.givchurch.data.local.model.enums.DonationCategory as CategoryEntity
import com.example.givchurch.domain.model.enums.DonationCategory as CategoryDomain
import com.example.givchurch.data.local.model.enums.DonationStatus as StatusEntity
import com.example.givchurch.domain.model.enums.DonationStatus as StatusDomain

object DonationMockData {

    val entityList = listOf(
        DonationEntity(
            id = 1,
            imageUrl = "content://media/external/images/media/32",
            name = "Cesta Básica",
            category = CategoryEntity.FOOD,
            description = "Contém arroz, feijão e óleo",
            quantity = 10,
            beneficiaryId = 101,
            createBy = "Admin",
            status = StatusEntity.PENDING,
            createdAt = LocalDateTime.of(2026, 6, 28, 10, 0),
            dueDate = LocalDateTime.of(2026, 7, 28, 10, 0)
        ),
        DonationEntity(
            id = 2,
            imageUrl = "content://media/external/images/media/33",
            name = "Agasalho de Lã",
            category = CategoryEntity.CLOTHING,
            description = "Tamanho G em bom estado",
            quantity = 5,
            beneficiaryId = 102,
            createBy = "User1",
            status = StatusEntity.DELIVERED,
            createdAt = LocalDateTime.of(2026, 6, 28, 11, 0),
            dueDate = LocalDateTime.of(2026, 8, 28, 11, 0)
        )
    )

    val domainList = listOf(
        DonationDomain(
            id = 1,
            imageUrl = "content://media/external/images/media/32",
            name = "Cesta Básica",
            category = CategoryDomain.FOOD,
            description = "Contém arroz, feijão e óleo",
            quantity = 10,
            beneficiaryId = 101,
            createBy = "Admin",
            status = StatusDomain.PENDING,
            createdAt = LocalDateTime.of(2026, 6, 28, 10, 0),
            dueDate = LocalDateTime.of(2026, 7, 28, 10, 0)
        ),
        DonationDomain(
            id = 2,
            imageUrl = "content://media/external/images/media/33",
            name = "Agasalho de Lã",
            category = CategoryDomain.CLOTHING,
            description = "Tamanho G em bom estado",
            quantity = 5,
            beneficiaryId = 102,
            createBy = "User1",
            status = StatusDomain.DELIVERED,
            createdAt = LocalDateTime.of(2026, 6, 28, 11, 0),
            dueDate = LocalDateTime.of(2026, 8, 28, 11, 0)
        )
    )
}
