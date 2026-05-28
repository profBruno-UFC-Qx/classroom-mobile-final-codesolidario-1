package com.example.givchurch.viewmodel.beneficiary

import androidx.lifecycle.ViewModel
import com.example.givchurch.data.model.Beneficiary
import com.example.givchurch.data.repository.BeneficiaryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainBeneficiaryViewModel(
    private val repository: BeneficiaryRepository = BeneficiaryRepository()
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _beneficiaries = MutableStateFlow<List<Beneficiary>>(repository.getAll())
    val beneficiaries: StateFlow<List<Beneficiary>> = _beneficiaries.asStateFlow()

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
        _beneficiaries.value = repository.getByName(newQuery)
    }
}
