package com.ElOuedUniv.maktaba.presentation.book.add

import android.net.Uri
import com.ElOuedUniv.maktaba.data.model.Category

data class AddBookUiState(
    val title: String = "",
    val isbn: String = "",
    val nbPages: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isFormValid: Boolean = false,
    val titleError: String? = null,
    val isbnError: String? = null,
    val nbPagesError: String? = null,
    val errorMessage: String? = null,
    val imageUrl: String? = null,           // ✅ الرابط النهائي (من Supabase أو ISBN)
    val categoryId: String? = null,
    val categories: List<Category> = emptyList(),
    val selectedCategoryId: String? = null,
    val isCategoryExpanded: Boolean = false,
    val imageUri: Uri? = null,
    val isDropdownExpanded: Boolean = false
)
