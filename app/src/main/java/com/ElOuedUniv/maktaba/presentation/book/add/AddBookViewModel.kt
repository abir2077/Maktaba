package com.ElOuedUniv.maktaba.presentation.book.add

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.data.repository.CategoryRepository
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import com.ElOuedUniv.maktaba.utils.SupabaseStorageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val addBookUseCase: AddBookUseCase,
    private val categoryRepository: CategoryRepository,
    private val storageManager: SupabaseStorageManager  // ✅ جديد لرفع الصور
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun onAction(action: AddBookUiAction) {
        when (action) {

            is AddBookUiAction.OnTitleChange -> {
                _uiState.update { it.copy(title = action.title) }
                validateInputs()
            }

            is AddBookUiAction.OnIsbnChange -> {
                _uiState.update { it.copy(isbn = action.isbn) }
                validateInputs()
                // ✅ جلب الصورة من ISBN تلقائياً (كما هو موجود)
                fetchImageFromIsbn(action.isbn)
            }

            is AddBookUiAction.OnPagesChange -> {
                _uiState.update { it.copy(nbPages = action.pages) }
                validateInputs()
            }

            is AddBookUiAction.OnImageSelected -> {
                // ✅ تغيير: رفع الصورة إلى Supabase بدلاً من حفظ URI مباشرة
                uploadImageToSupabase(action.uri)
            }

            is AddBookUiAction.OnCategoryChange -> {
                _uiState.update { it.copy(categoryId = action.categoryId) }
            }

            is AddBookUiAction.OnToggleDropdown -> {
                _uiState.update {
                    it.copy(isDropdownExpanded = !it.isDropdownExpanded)
                }
            }

            AddBookUiAction.OnAddClick -> {
                if (_uiState.value.isFormValid) {
                    addBook()
                }
            }

            else -> {}
        }
    }

    // ✅ جلب الصورة من ISBN (محافظة على الوضع الحالي)
    private fun fetchImageFromIsbn(isbn: String) {
        // فقط إذا كان ISBN صحيح (13 رقم)
        if (isbn.length == 13 && isbn.all { it.isDigit() }) {
            val imageUrl = "https://covers.openlibrary.org/b/isbn/${isbn}-L.jpg"
            _uiState.update { it.copy(imageUrl = imageUrl) }
        }
    }

    // ✅ رفع الصورة إلى Supabase Storage
    private fun uploadImageToSupabase(uri: Uri) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val uploadedUrl = storageManager.uploadBookCover(uri)
                _uiState.update {
                    it.copy(
                        imageUrl = uploadedUrl,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = "فشل رفع الصورة: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getAllCategories().collect { list ->
                _uiState.update {
                    it.copy(categories = list)
                }
            }
        }
    }

    private fun validateInputs() {
        val title = _uiState.value.title
        val isbn = _uiState.value.isbn
        val nbPages = _uiState.value.nbPages

        val titleError = if (title.isBlank()) "Title cannot be empty" else null

        val isbnError = if (isbn.isNotBlank() && (isbn.length != 13 || isbn.any { !it.isDigit() }))
            "ISBN must be 13 digits" else null

        val pagesInt = if (nbPages.isNotBlank()) nbPages.toIntOrNull() else null
        val pagesError = if (nbPages.isNotBlank() && (pagesInt == null || pagesInt <= 0))
            "Pages must be a positive number" else null

        _uiState.update {
            it.copy(
                titleError = titleError,
                isbnError = isbnError,
                nbPagesError = pagesError,
                isFormValid = titleError == null && pagesError == null
            )
        }
    }

    private fun addBook() {
        val s = _uiState.value

        // ✅ إذا لم توجد صورة مرفوعة، استخدم صورة من ISBN
        val finalImageUrl = s.imageUrl ?: if (s.isbn.length == 13 && s.isbn.all { it.isDigit() }) {
            "https://covers.openlibrary.org/b/isbn/${s.isbn}-L.jpg"
        } else {
            null
        }

        val book = Book(
            isbn = s.isbn,
            title = s.title,
            nbPages = s.nbPages.toIntOrNull() ?: 0,
            imageUrl = finalImageUrl,
            categoryId = s.categoryId ?: ""
        )

        viewModelScope.launch {
            try {
                addBookUseCase(book)
                _uiState.update { it.copy(isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }
}