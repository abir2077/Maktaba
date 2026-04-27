package com.ElOuedUniv.maktaba.presentation.book.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.domain.usecase.GetBookByIsbnUseCase
import com.ElOuedUniv.maktaba.domain.usecase.DeleteBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBookByIsbnUseCase: GetBookByIsbnUseCase,
    private val deleteBookUseCase: DeleteBookUseCase
) : ViewModel() {

    private val isbn: String = savedStateHandle.get<String>("isbn") ?: ""

    private val _uiState = MutableStateFlow(BookDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadBook()
    }

    private fun loadBook() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                if (isbn.isBlank()) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "ISBN not received"
                        )
                    }
                    return@launch
                }

                val book = getBookByIsbnUseCase(isbn)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        book = book,
                        errorMessage = if (book == null) "Book not found" else null
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    // ✅ إضافة الحذف
    fun deleteBook() {
        viewModelScope.launch {
            try {
                deleteBookUseCase(isbn)
                _uiState.update { it.copy(isDeleted = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    fun onAction(action: BookDetailUiAction) {
        // future actions
    }
}