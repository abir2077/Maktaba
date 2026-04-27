package com.ElOuedUniv.maktaba.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ElOuedUniv.maktaba.presentation.book.BookListView
import com.ElOuedUniv.maktaba.presentation.book.add.AddBookView
import com.ElOuedUniv.maktaba.presentation.book.detail.BookDetailView
import com.ElOuedUniv.maktaba.presentation.category.CategoryListView
import com.ElOuedUniv.maktaba.presentation.onboarding.OnboardingView

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    val startDestination = if (prefs.getBoolean("onboarding_done", false)) {
        Screen.BookList.route
    } else {
        Screen.Onboarding.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Screen.Onboarding.route) {
            OnboardingView(
                onNavigateToLibrary = {
                    navController.navigate(Screen.BookList.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.BookList.route) {
            BookListView(
                onCategoriesClick = {
                    navController.navigate(Screen.CategoryList.route)
                },
                onAddBookClick = {
                    navController.navigate(Screen.AddBook.route)
                },
                onBookClick = { isbn ->
                    navController.navigate(Screen.BookDetail.createRoute(isbn))
                },
                onDispose = { }
            )
        }

        composable(
            route = Screen.BookDetail.route + "/{isbn}"
        ) { backStackEntry ->
            val isbn = backStackEntry.arguments?.getString("isbn")

            BookDetailView(
                onBackClick = {
                    navController.popBackStack()
                    navController.navigate(Screen.BookList.route) {
                        popUpTo(Screen.BookList.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.CategoryList.route) {
            CategoryListView(
                onBackClick = { navController.popBackStack() },
                onCategoryClick = { categoryId ->
                    navController.navigate("books_by_category/$categoryId")
                }
            )
        }

        composable(
            route = "books_by_category/{categoryId}"
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")

            BookListView(
                selectedCategoryId = categoryId,
                onCategoriesClick = {
                    navController.navigate(Screen.CategoryList.route)
                },
                onAddBookClick = {
                    navController.navigate(Screen.AddBook.route)
                },
                onBookClick = { isbn ->
                    navController.navigate(Screen.BookDetail.createRoute(isbn))
                },
                onDispose = { }
            )
        }

        composable(Screen.AddBook.route) {
            AddBookView(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}