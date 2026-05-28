package com.example.givchurch.data.mock

import com.example.givchurch.data.model.Donation
import com.example.givchurch.data.model.enums.DonationCategory
import com.example.givchurch.data.model.enums.DonationStatus

object DonationMockData {

    val donations = mutableListOf(
        Donation(
            id = 1,
            imageUrl = null,
            name = "Cesta Básica Completa",
            category = DonationCategory.FOOD,
            description = "Contém arroz, feijão, óleo, açúcar e café.",
            quantity = 5,
            organizationId = 1,
            creatorId = 1,
            status = DonationStatus.PENDING
        ),
        Donation(
            id = 2,
            imageUrl = null,
            name = "Fardo de Macarrão",
            category = DonationCategory.FOOD,
            description = "Pacotes de espaguete de 500g.",
            quantity = 2,
            organizationId = 1,
            creatorId = 1,
            status = DonationStatus.DELIVERED
        ),

        Donation(
            id = 3,
            imageUrl = null,
            name = "Fraldas Geriátricas M",
            category = DonationCategory.HYGIENE,
            description = "Pacotes fechados para os idosos assistidos.",
            quantity = 10,
            organizationId = 2,
            creatorId = 1,
            status = DonationStatus.PENDING
        ),
        Donation(
            id = 4,
            imageUrl = null,
            name = "Leite em Pó Integral",
            category = DonationCategory.FOOD,
            description = "Latas de 400g para o complemento alimentar.",
            quantity = 12,
            organizationId = 2,
            creatorId = 1,
            status = DonationStatus.PENDING
        ),

        Donation(
            id = 5,
            imageUrl = null,
            name = "Agasalhos de Inverno",
            category = DonationCategory.CLOTHING,
            description = "Casacos e moletons em bom estado.",
            quantity = 15,
            organizationId = 3,
            creatorId = 1,
            status = DonationStatus.DELIVERED
        ),
        Donation(
            id = 6,
            imageUrl = null,
            name = "Cobertores de Casal",
            category = DonationCategory.CLOTHING,
            description = "Cobertores higienizados para distribuição.",
            quantity = 8,
            organizationId = 3,
            creatorId = 1,
            status = DonationStatus.PENDING
        ),

        Donation(
            id = 7,
            imageUrl = null,
            name = "Livros Infantis",
            category = DonationCategory.FURNITURE_AND_UTENSILS,
            description = "Livros didáticos e de historinhas para a biblioteca.",
            quantity = 20,
            organizationId = 4,
            creatorId = 2,
            status = DonationStatus.PENDING
        ),
        Donation(
            id = 8,
            imageUrl = null,
            name = "Cadernos Universitários",
            category = DonationCategory.FURNITURE_AND_UTENSILS,
            description = "Cadernos de 10 matérias para os estudantes.",
            quantity = 30,
            organizationId = 4,
            creatorId = 2,
            status = DonationStatus.DELIVERED
        ),

        Donation(
            id = 9,
            imageUrl = null,
            name = "Sabonetes em Barra",
            category = DonationCategory.HYGIENE,
            description = "Caixas com unidades para higiene pessoal.",
            quantity = 50,
            organizationId = 5,
            creatorId = 2,
            status = DonationStatus.PENDING
        ),
        Donation(
            id = 10,
            imageUrl = null,
            name = "Desinfetante Hospitalar",
            category = DonationCategory.HYGIENE,
            description = "Galões de 5 litros para limpeza do ambiente.",
            quantity = 4,
            organizationId = 5,
            creatorId = 2,
            status = DonationStatus.DELIVERED
        ),

        Donation(
            id = 11,
            imageUrl = null,
            name = "Sacos de Batata",
            category = DonationCategory.FOOD,
            description = "Sacos de 10kg para o preparo da sopa.",
            quantity = 3,
            organizationId = 6,
            creatorId = 2,
            status = DonationStatus.PENDING
        ),
        Donation(
            id = 12,
            imageUrl = null,
            name = "Copos Descartáveis 300ml",
            category = DonationCategory.FURNITURE_AND_UTENSILS,
            description = "Tiras de copos para servir a sopa.",
            quantity = 10,
            organizationId = 6,
            creatorId = 2,
            status = DonationStatus.PENDING
        )
    )
}
