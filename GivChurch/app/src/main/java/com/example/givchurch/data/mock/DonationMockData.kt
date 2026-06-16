package com.example.givchurch.data.mock

import com.example.givchurch.data.local.model.Donation
import com.example.givchurch.data.local.model.enums.DonationCategory
import com.example.givchurch.data.local.model.enums.DonationStatus
import java.time.LocalDateTime

object DonationMockData {

    val donations = mutableListOf(
        // JANEIRO
        Donation(
            id = 1,
            imageUrl = null,
            name = "Cesta Básica Completa",
            category = DonationCategory.FOOD,
            description = "Contém arroz, feijão, óleo, açúcar e café.",
            quantity = 45,
            beneficiaryId = 1,
            createBy = 1,
            status = DonationStatus.PENDING,
            createdAt = LocalDateTime.now().minusMonths(4),
            dueDate = LocalDateTime.now().plusDays(1)
        ),

        // FEVEREIRO
        Donation(
            id = 2,
            imageUrl = null,
            name = "Fardo de Macarrão",
            category = DonationCategory.FOOD,
            description = "Pacotes de espaguete de 500g.",
            quantity = 52,
            beneficiaryId = 1,
            createBy = 1,
            status = DonationStatus.DELIVERED,
            createdAt = LocalDateTime.now().minusMonths(3),
            dueDate = LocalDateTime.now().minusDays(1)
        ),

        // MARÇO
        Donation(
            id = 3,
            imageUrl = null,
            name = "Fraldas Geriátricas M",
            category = DonationCategory.HYGIENE,
            description = "Pacotes fechados para os idosos assistidos.",
            quantity = 38,
            beneficiaryId = 2,
            createBy = 1,
            status = DonationStatus.PENDING,
            createdAt = LocalDateTime.now().minusMonths(2),
            dueDate = LocalDateTime.now().plusDays(5)
        ),

        // ABRIL
        Donation(
            id = 4,
            imageUrl = null,
            name = "Leite em Pó Integral",
            category = DonationCategory.FOOD,
            description = "Latas de 400g para o complemento alimentar.",
            quantity = 60,
            beneficiaryId = 2,
            createBy = 1,
            status = DonationStatus.PENDING,
            createdAt = LocalDateTime.now().minusMonths(1),
            dueDate = LocalDateTime.now().plusHours(12)
        ),

        // MAIO (Mês Atual)
        Donation(
            id = 5,
            imageUrl = null,
            name = "Agasalhos de Inverno",
            category = DonationCategory.CLOTHING,
            description = "Casacos e moletons em bom estado.",
            quantity = 15,
            beneficiaryId = 3,
            createBy = 1,
            status = DonationStatus.DELIVERED,
            createdAt = LocalDateTime.now().minusDays(5),
            dueDate = LocalDateTime.now().minusWeeks(1)
        ),
        Donation(
            id = 6,
            imageUrl = null,
            name = "Cobertores de Casal",
            category = DonationCategory.CLOTHING,
            description = "Cobertores higienizados para distribuição.",
            quantity = 8,
            beneficiaryId = 3,
            createBy = 1,
            status = DonationStatus.PENDING,
            createdAt = LocalDateTime.now().minusDays(3),
            dueDate = LocalDateTime.now().plusDays(2)
        ),
        Donation(
            id = 7,
            imageUrl = null,
            name = "Livros Infantis",
            category = DonationCategory.FURNITURE_AND_UTENSILS,
            description = "Livros didáticos e de historinhas para a biblioteca.",
            quantity = 20,
            beneficiaryId = 4,
            createBy = 2,
            status = DonationStatus.PENDING,
            createdAt = LocalDateTime.now().minusDays(4),
            dueDate = LocalDateTime.now().plusDays(10)
        ),
        Donation(
            id = 8,
            imageUrl = null,
            name = "Cadernos Universitários",
            category = DonationCategory.FURNITURE_AND_UTENSILS,
            description = "Cadernos de 10 matérias para os estudantes.",
            quantity = 30,
            beneficiaryId = 4,
            createBy = 2,
            status = DonationStatus.DELIVERED,
            createdAt = LocalDateTime.now().minusDays(10),
            dueDate = LocalDateTime.now().minusDays(3)
        ),
        Donation(
            id = 9,
            imageUrl = null,
            name = "Sabonetes em Barra",
            category = DonationCategory.HYGIENE,
            description = "Caixas com unidades para higiene pessoal.",
            quantity = 50,
            beneficiaryId = 5,
            createBy = 2,
            status = DonationStatus.PENDING,
            createdAt = LocalDateTime.now(),
            dueDate = LocalDateTime.now().plusDays(7)
        ),
        Donation(
            id = 10,
            imageUrl = null,
            name = "Desinfetante Hospitalar",
            category = DonationCategory.HYGIENE,
            description = "Galões de 5 litros para limpeza do ambiente.",
            quantity = 4,
            beneficiaryId = 5,
            createBy = 2,
            status = DonationStatus.DELIVERED,
            createdAt = LocalDateTime.now().minusDays(2),
            dueDate = LocalDateTime.now().minusDays(1)
        ),
        Donation(
            id = 11,
            imageUrl = null,
            name = "Sacos de Batata",
            category = DonationCategory.FOOD,
            description = "Sacos de 10kg para o preparo da sopa.",
            quantity = 3,
            beneficiaryId = 6,
            createBy = 2,
            status = DonationStatus.PENDING,
            createdAt = LocalDateTime.now().minusDays(1),
            dueDate = LocalDateTime.now().plusHours(6)
        ),
        Donation(
            id = 12,
            imageUrl = null,
            name = "Copos Descartáveis 300ml",
            category = DonationCategory.FURNITURE_AND_UTENSILS,
            description = "Tiras de copos para servir a sopa.",
            quantity = 10,
            beneficiaryId = 6,
            createBy = 2,
            status = DonationStatus.PENDING,
            createdAt = LocalDateTime.now().minusDays(2),
            dueDate = LocalDateTime.now().plusDays(4)
        )
    )
}
