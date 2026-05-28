package com.example.givchurch.data.mock

import com.example.givchurch.data.model.Beneficiary

object BeneficiaryMockData {

    val beneficiaries = mutableListOf(
        Beneficiary(
            id = 1,
            name = "Igreja Central de Amparo",
            phoneNumber = "(85) 99999-1234",
            address = "Av. da Solidariedade, 123 - Centro",
            observations = "Recebemos alimentos não perecíveis de segunda a sexta.",
            createBy = 1
        ),
        Beneficiary(
            id = 2,
            name = "Ação Social Esperança",
            phoneNumber = "(85) 3412-0000",
            address = "Rua Clarindo de Queiroz, 789 - São João",
            observations = "Foco em fraldas geriátricas e leite em pó.",
            createBy = 1
        ),
        Beneficiary(
            id = 3,
            name = "Missão Resgate",
            phoneNumber = "(85) 98765-4321",
            address = "Marginal Baturité, Km 2 - Zona Rural",
            observations = "Ponto de coleta de roupas e cobertores.",
            createBy = 1
        ),

        Beneficiary(
            id = 4,
            name = "Instituto Videira",
            phoneNumber = "(85) 98888-5678",
            address = "Rua da Esperança, 456 - Campo Novo",
            observations = "Arrecadação de livros e materiais escolares.",
            createBy = 2
        ),
        Beneficiary(
            id = 5,
            name = "Lar dos Avós",
            phoneNumber = "(85) 3451-9988",
            address = "Av. Dom Lucas, 1010 - Planalto",
            observations = "Necessitamos de produtos de higiene e limpeza.",
            createBy = 2
        ),
        Beneficiary(
            id = 6,
            name = "Sopa Solidária",
            phoneNumber = "(85) 99111-2233",
            address = "Praça da Matriz, s/n - Centro",
            observations = "Doações de legumes e descartáveis para a sopa semanal.",
            createBy = 2
        )
    )
}