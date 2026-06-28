package com.example.givchurch.data.mock

import com.example.givchurch.data.local.model.Beneficiary as BeneficiaryEntity
import com.example.givchurch.domain.model.Beneficiary as BeneficiaryDomain

object BeneficiaryMockData {

    val entityList = listOf(
        BeneficiaryEntity(
            id = 1,
            name = "João da Silva",
            phoneNumber = "88999999999",
            address = "Rua Central, 45",
            observations = "Necessita de cestas básicas",
            createBy = "Admin"
        ),
        BeneficiaryEntity(
            id = 2,
            name = "Maria Souza",
            phoneNumber = "88988888888",
            address = "Av. Principal, 100",
            observations = "Mãe solo",
            createBy = "User1"
        )
    )

    val domainList = listOf(
        BeneficiaryDomain(
            id = 1,
            name = "João da Silva",
            phoneNumber = "88999999999",
            address = "Rua Central, 45",
            observations = "Necessita de cestas básicas",
            createBy = "Admin"
        ),
        BeneficiaryDomain(
            id = 2,
            name = "Maria Souza",
            phoneNumber = "88988888888",
            address = "Av. Principal, 100",
            observations = "Mãe solo",
            createBy = "User1"
        )
    )
}
