package com.example.givchurch.di

import com.example.givchurch.data.local.database.AppDatabase
import com.example.givchurch.data.remote.firebase.service.FirebaseAuthService
import com.example.givchurch.data.remote.firebase.service.FirebaseUserService
import com.example.givchurch.data.repository.AuthRepositoryImpl
import com.example.givchurch.data.repository.BeneficiaryRepositoryImpl
import com.example.givchurch.data.repository.DashboardRepositoryImpl
import com.example.givchurch.data.repository.DonationRepositoryImpl
import com.example.givchurch.data.repository.UserRepositoryImpl
import com.example.givchurch.domain.repository.AuthRepository
import com.example.givchurch.domain.repository.BeneficiaryRepository
import com.example.givchurch.domain.repository.DashboardRepository
import com.example.givchurch.domain.repository.DonationRepository
import com.example.givchurch.domain.repository.UserRepository
import com.example.givchurch.viewmodel.auth.LoginViewModel
import com.example.givchurch.viewmodel.auth.RegisterViewModel
import com.example.givchurch.viewmodel.beneficiary.AddBeneficiaryViewModel
import com.example.givchurch.viewmodel.beneficiary.MainBeneficiaryViewModel
import com.example.givchurch.viewmodel.donation.AddDonationViewModel
import com.example.givchurch.viewmodel.donation.MainDonationViewModel
import com.example.givchurch.viewmodel.history.MainHistoryViewModel
import com.example.givchurch.viewmodel.home.MainHomeViewModel
import com.example.givchurch.viewmodel.profile.MainProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single { AppDatabase.getDatabase(androidContext()) }

    single { get<AppDatabase>().beneficiaryDao() }
    single { get<AppDatabase>().donationDao() }
    single { get<AppDatabase>().dashboardDao() }
    single { FirebaseAuthService() }
    single { FirebaseUserService() }
}

val repositoryModule = module {
    single<BeneficiaryRepository> { BeneficiaryRepositoryImpl(get()) }
    single<DonationRepository> { DonationRepositoryImpl(get()) }
    single<DashboardRepository> { DashboardRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { AddBeneficiaryViewModel(get()) }
    viewModel { MainBeneficiaryViewModel(get()) }
    viewModel { AddDonationViewModel(get(), get()) }
    viewModel { MainDonationViewModel(get(), get()) }
    viewModel { MainHistoryViewModel(get(), get()) }
    viewModel { MainHomeViewModel(get(), get()) }
    viewModel { MainProfileViewModel() }
}

val appModules = listOf(databaseModule, repositoryModule, viewModelModule)
