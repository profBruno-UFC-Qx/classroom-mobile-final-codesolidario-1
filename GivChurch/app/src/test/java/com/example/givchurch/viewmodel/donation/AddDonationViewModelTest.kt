package com.example.givchurch.viewmodel.donation

import com.example.givchurch.domain.model.Beneficiary
import com.example.givchurch.domain.model.Donation
import com.example.givchurch.domain.model.enums.DonationCategory
import com.example.givchurch.domain.model.enums.DonationStatus
import com.example.givchurch.domain.repository.BeneficiaryRepository
import com.example.givchurch.domain.repository.DonationRepository
import com.example.givchurch.domain.repository.UserRepository
import com.example.givchurch.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class AddDonationViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val donationRepository: DonationRepository = mockk()
    private val beneficiaryRepository: BeneficiaryRepository = mockk()
    private val userRepository: UserRepository = mockk()
    private lateinit var viewModel: AddDonationViewModel

    private val firebaseUserId = "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"
    private val mockBeneficiaries = listOf(
        Beneficiary(1, "Ana Silva", "8899", "Rua A", "", "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH"),
        Beneficiary(2, "Carlos Oliveira", "8898", "Rua B", "", "W4k9Xz7Pq2LmN1bV8cR3tY6mK5jH")
    )

    @Before
    fun setUp() {
        every { userRepository.getCurrentUserId() } returns firebaseUserId
        every { beneficiaryRepository.getAll(firebaseUserId) } returns flowOf(mockBeneficiaries)
    }

    @Test
    fun `init should load beneficiaries successfully`() = runTest {
        viewModel = AddDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        assertEquals(mockBeneficiaries, viewModel.uiState.value.beneficiaries)
    }

    @Test
    fun `onNameChanged should update state with new value`() = runTest {
        viewModel = AddDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.onNameChanged("Cesta Básica")

        assertEquals("Cesta Básica", viewModel.uiState.value.name)
    }

    @Test
    fun `onDescriptionChanged should update state with new value`() = runTest {
        viewModel = AddDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.onDescriptionChanged("Doação de alimentos não perecíveis")

        assertEquals("Doação de alimentos não perecíveis", viewModel.uiState.value.description)
    }

    @Test
    fun `onQuantityChanged should update state with new value`() = runTest {
        viewModel = AddDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.onQuantityChanged("5")

        assertEquals("5", viewModel.uiState.value.quantityString)
    }

    @Test
    fun `onCategorySelected should update category and collapse expansion state`() = runTest {
        viewModel = AddDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.onCategorySelected(DonationCategory.CLOTHING)

        assertEquals(DonationCategory.CLOTHING, viewModel.uiState.value.selectedCategory)
        assertFalse(viewModel.uiState.value.isCategoryExpanded)
    }

    @Test
    fun `onCategoryExpandedChanged should update expansion state`() = runTest {
        viewModel = AddDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.onCategoryExpandedChanged(true)

        assertTrue(viewModel.uiState.value.isCategoryExpanded)
    }

    @Test
    fun `onBeneficiarySelected should update beneficiary and collapse expansion state`() = runTest {
        viewModel = AddDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        val beneficiary = mockBeneficiaries.first()
        viewModel.onBeneficiarySelected(beneficiary)

        assertEquals(beneficiary, viewModel.uiState.value.selectedBeneficiary)
        assertFalse(viewModel.uiState.value.isBeneficiaryExpanded)
    }

    @Test
    fun `onBeneficiaryExpandedChanged should update expansion state`() = runTest {
        viewModel = AddDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.onBeneficiaryExpandedChanged(true)

        assertTrue(viewModel.uiState.value.isBeneficiaryExpanded)
    }

    @Test
    fun `onStatusSelected should update status and collapse expansion state`() = runTest {
        viewModel = AddDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.onStatusSelected(DonationStatus.DELIVERED)

        assertEquals(DonationStatus.DELIVERED, viewModel.uiState.value.selectedStatus)
        assertFalse(viewModel.uiState.value.isStatusExpanded)
    }

    @Test
    fun `onStatusExpandedChanged should update expansion state`() = runTest {
        viewModel = AddDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.onStatusExpandedChanged(true)

        assertTrue(viewModel.uiState.value.isStatusExpanded)
    }

    @Test
    fun `onDateSelected should update date time and collapse picker expansion state`() = runTest {
        viewModel = AddDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        val futureDate = LocalDateTime.now().plusDays(5)
        viewModel.onDateSelected(futureDate)

        assertEquals(futureDate, viewModel.uiState.value.selectedDateTime)
        assertFalse(viewModel.uiState.value.isDatePickerExpanded)
    }

    @Test
    fun `onDatePickerExpandedChanged should update expansion state`() = runTest {
        viewModel = AddDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.onDatePickerExpandedChanged(true)

        assertTrue(viewModel.uiState.value.isDatePickerExpanded)
    }

    @Test
    fun `loadDonationData should set edit mode and map fields with correct beneficiary lookup`() = runTest {
        viewModel = AddDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        val donation = Donation(
            id = 42,
            imageUrl = "path/to/img.jpg",
            name = "Leite",
            category = DonationCategory.FOOD,
            description = "Caixas de leite integral",
            quantity = 12,
            beneficiaryId = 2,
            createBy = firebaseUserId,
            status = DonationStatus.PENDING,
            dueDate = LocalDateTime.now().plusDays(2)
        )

        viewModel.loadDonationData(donation)

        val state = viewModel.uiState.value
        assertEquals("path/to/img.jpg", state.imageUrl)
        assertEquals("Leite", state.name)
        assertEquals("Caixas de leite integral", state.description)
        assertEquals("12", state.quantityString)
        assertEquals(DonationCategory.FOOD, state.selectedCategory)
        assertEquals(mockBeneficiaries[1], state.selectedBeneficiary)
        assertEquals(DonationStatus.PENDING, state.selectedStatus)
        assertEquals(donation.dueDate, state.selectedDateTime)
    }

    @Test
    fun `resetSaveStatus should clean data fields but retain existing beneficiaries list`() = runTest {
        viewModel = AddDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.onNameChanged("Casaco")
        viewModel.resetSaveStatus()

        val state = viewModel.uiState.value
        assertEquals("", state.name)
        assertEquals(mockBeneficiaries, state.beneficiaries)
        assertFalse(state.isSaveSuccess)
    }

    @Test
    fun `saveDonation should call repository create and update success flag when in creation mode`() = runTest {
        viewModel = AddDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        viewModel.onNameChanged("Arroz")
        viewModel.onQuantityChanged("3")
        viewModel.onCategorySelected(DonationCategory.FOOD)
        viewModel.onBeneficiarySelected(mockBeneficiaries.first())

        coEvery { donationRepository.create(any()) } returns true

        viewModel.saveDonation()
        runCurrent()

        assertTrue(viewModel.uiState.value.isSaveSuccess)
    }

    @Test
    fun `saveDonation should call repository update and update success flag when in edit mode`() = runTest {
        viewModel = AddDonationViewModel(donationRepository, beneficiaryRepository, userRepository)
        runCurrent()

        val donation = Donation(
            id = 10,
            imageUrl = null,
            name = "Feijão",
            category = DonationCategory.FOOD,
            description = "",
            quantity = 2,
            beneficiaryId = 1,
            createBy = firebaseUserId,
            status = DonationStatus.PENDING,
            dueDate = LocalDateTime.now().plusDays(1)
        )
        viewModel.loadDonationData(donation)

        coEvery { donationRepository.update(any()) } returns true

        viewModel.saveDonation()
        runCurrent()

        assertTrue(viewModel.uiState.value.isSaveSuccess)
    }
}
