package com.example.givchurch.data.mapper

import com.example.givchurch.data.local.model.Donation as DonationEntity
import com.example.givchurch.domain.model.Donation as DonationDomain
import com.example.givchurch.data.local.model.enums.DonationCategory as CategoryEntity
import com.example.givchurch.domain.model.enums.DonationCategory as CategoryDomain
import com.example.givchurch.data.local.model.enums.DonationStatus as StatusEntity
import com.example.givchurch.domain.model.enums.DonationStatus as StatusDomain

fun DonationEntity.toDomain(): DonationDomain {
    return DonationDomain(
        id = this.id,
        imageUrl = this.imageUrl,
        name = this.name,
        category = CategoryDomain.valueOf(this.category.name),
        description = this.description,
        quantity = this.quantity,
        beneficiaryId = this.beneficiaryId,
        createBy = this.createBy,
        status = StatusDomain.valueOf(this.status.name),
        createdAt = this.createdAt,
        dueDate = this.dueDate
    )
}

fun DonationDomain.toEntity(): DonationEntity {
    return DonationEntity(
        id = this.id,
        imageUrl = this.imageUrl,
        name = this.name,
        category = CategoryEntity.valueOf(this.category.name),
        description = this.description,
        quantity = this.quantity,
        beneficiaryId = this.beneficiaryId,
        createBy = this.createBy,
        status = StatusEntity.valueOf(this.status.name),
        createdAt = this.createdAt,
        dueDate = this.dueDate
    )
}
