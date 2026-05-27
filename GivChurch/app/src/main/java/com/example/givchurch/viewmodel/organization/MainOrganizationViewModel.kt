package com.example.givchurch.viewmodel.organization

import androidx.lifecycle.ViewModel
import com.example.givchurch.data.model.Organization
import com.example.givchurch.data.repository.OrganizationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainOrganizationViewModel(
    private val repository: OrganizationRepository = OrganizationRepository()
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _organizations = MutableStateFlow<List<Organization>>(repository.getAll())
    val organizations: StateFlow<List<Organization>> = _organizations.asStateFlow()

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
        _organizations.value = repository.getByName(newQuery)
    }
}
