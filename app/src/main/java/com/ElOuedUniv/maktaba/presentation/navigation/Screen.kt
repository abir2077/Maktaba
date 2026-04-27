package com.ElOuedUniv.maktaba.presentation.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object BookList : Screen("book_list")

    object BookDetail {
        const val route = "book_detail"

        fun createRoute(isbn: String) = "$route/$isbn"
    }
    object CategoryList : Screen("category_list")
    object AddBook : Screen("add_book")
}
