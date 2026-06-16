package com.example.givchurch.di

import com.example.givchurch.data.local.database.AppDatabase
import com.example.givchurch.data.repository.BeneficiaryRepositoryImpl
import com.example.givchurch.data.repository.DashboardRepositoryImpl
import com.example.givchurch.data.repository.DonationRepositoryImpl
import com.example.givchurch.domain.repository.BeneficiaryRepository
import com.example.givchurch.domain.repository.DashboardRepository
import com.example.givchurch.domain.repository.DonationRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { AppDatabase.getDatabase(androidContext()) }

    single { get<AppDatabase>().beneficiaryDao() }
    single { get<AppDatabase>().donationDao() }
    single { get<AppDatabase>().dashboardDao() }
}

val repositoryModule = module {
    single<BeneficiaryRepository> { BeneficiaryRepositoryImpl(get()) }
    single<DonationRepository> { DonationRepositoryImpl(get()) }
    single<DashboardRepository> { DashboardRepositoryImpl(get()) }
}

val appModules = listOf(databaseModule, repositoryModule)
