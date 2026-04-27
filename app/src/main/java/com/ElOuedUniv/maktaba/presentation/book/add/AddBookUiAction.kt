package com.ElOuedUniv.maktaba.presentation.book.add

import com.ElOuedUniv.maktaba.data.model.Category

sealed class AddBookUiAction {
    data class OnTitleChange(val title: String) : AddBookUiAction()
    data class OnIsbnChange(val isbn: String) : AddBookUiAction()
    data class OnPagesChange(val pages: String) : AddBookUiAction()

    data class OnImageSelected(val uri: android.net.Uri) : AddBookUiAction()

    data class OnCategoryChange(val categoryId: String) : AddBookUiAction()
    object OnAddClick : AddBookUiAction()

    data class OnCategorySelected(val categoryId: String) : AddBookUiAction()


    object OnToggleCategoryDropdown : AddBookUiAction()

    data class OnCategoriesLoaded(val categories: List<Category>) : AddBookUiAction()
    object OnToggleDropdown : AddBookUiAction()
}
