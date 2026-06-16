package com.example.givchurch.data.mapper

import com.example.givchurch.data.local.model.Donation as DonationEntity
import com.example.givchurch.domain.model.Donation as DonationDomain

fun DonationEntity.toDomain(): DonationDomain {
    return DonationDomain(
        id = this.id,
        imageUrl = this.imageUrl,
        name = this.name,
        category = this.category,
        description = this.description,
        quantity = this.quantity,
        beneficiaryId = this.beneficiaryId,
        createBy = this.createBy,
        status = this.status,
        createdAt = this.createdAt,
        dueDate = this.dueDate
    )
}

fun DonationDomain.toEntity(): DonationEntity {
    return DonationEntity(
        id = this.id,
        imageUrl = this.imageUrl,
        name = this.name,
        category = this.category,
        description = this.description,
        quantity = this.quantity,
        beneficiaryId = this.beneficiaryId,
        createBy = this.createBy,
        status = this.status,
        createdAt = this.createdAt,
        dueDate = this.dueDate
    )
}
