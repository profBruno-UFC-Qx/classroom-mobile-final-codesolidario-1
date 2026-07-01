package com.example.givchurch.data.mapper

import com.example.givchurch.data.local.model.Beneficiary as BeneficiaryEntity
import com.example.givchurch.domain.model.Beneficiary as BeneficiaryDomain

fun BeneficiaryEntity.toDomain(): BeneficiaryDomain {
    return BeneficiaryDomain(
        id = this.id,
        name = this.name,
        phoneNumber = this.phoneNumber,
        address = this.address,
        observations = this.observations,
        createBy = this.createBy
    )
}

fun BeneficiaryDomain.toEntity(): BeneficiaryEntity {
    return BeneficiaryEntity(
        id = this.id,
        name = this.name,
        phoneNumber = this.phoneNumber,
        address = this.address,
        observations = this.observations,
        createBy = this.createBy
    )
}
