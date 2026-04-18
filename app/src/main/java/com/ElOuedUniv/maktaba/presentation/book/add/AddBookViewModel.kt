package com.ElOuedUniv.maktaba.presentation.book.add

import androidx.lifecycle.ViewModel
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState = _uiState.asStateFlow()

    // ✅ VALIDATION FUNCTION
    private fun validate(state: AddBookUiState): Boolean {

        val titleValid = state.title.trim().isNotEmpty()

        // 🔥 FIXED: remove "-" before checking
        val isbnClean = state.isbn.replace("-", "").trim()

        val isbnValid =
            isbnClean.length == 13 && isbnClean.all { it.isDigit() }

        val pagesNumber = state.pages.trim().toIntOrNull()
        val pagesValid = pagesNumber != null && pagesNumber > 0

        return titleValid && isbnValid && pagesValid
    }

    fun onAction(action: AddBookUiAction) {
        when (action) {

            is AddBookUiAction.OnTitleChange -> {
                _uiState.update {
                    val newState = it.copy(title = action.title)
                    newState.copy(isFormValid = validate(newState))
                }
            }

            is AddBookUiAction.OnIsbnChange -> {
                _uiState.update {
                    val newState = it.copy(isbn = action.isbn)
                    newState.copy(isFormValid = validate(newState))
                }
            }

            is AddBookUiAction.OnPagesChange -> {
                _uiState.update {
                    val newState = it.copy(pages = action.pages)
                    newState.copy(isFormValid = validate(newState))
                }
            }

            is AddBookUiAction.OnImageUrlChange -> {
                _uiState.update {
                    it.copy(imageUrl = action.imageUrl)
                }
            }

            AddBookUiAction.OnAddClick -> {
                addBook()
            }
        }
    }

    private fun addBook() {
        val currentState = _uiState.value

        val pagesNumber = currentState.pages.toIntOrNull() ?: return

        val book = Book(
            id = 1,
            title = currentState.title,
            isbn = currentState.isbn,
            pages = pagesNumber,
            imageUrl = currentState.imageUrl
        )

        addBookUseCase(book)

        _uiState.update { it.copy(isSuccess = true) }
    }
}