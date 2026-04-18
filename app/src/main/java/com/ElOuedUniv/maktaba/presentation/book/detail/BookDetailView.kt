package com.ElOuedUniv.maktaba.presentation.book.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailView(
    isbn: String,
    onBackClick: () -> Unit,
    viewModel: BookDetailViewModel = hiltViewModel()
) {

    //  نطلب من ViewModel يجلب الكتاب
    LaunchedEffect(Unit) {
        viewModel.loadBook()
    }

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.book?.title ?: "Book Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            if (uiState.isLoading) {
                CircularProgressIndicator()

            } else if (uiState.book != null) {

                val book = uiState.book!!

                // 📸 الصورة
                if (!book.imageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = book.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 📖 المعلومات
                Text("Title: ${book.title}")
                Text("ISBN: ${book.isbn}")
                Text("Pages: ${book.pages}")
                Text("Status: ${book.status}")

            } else if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage!!,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                Text("Book not found")
            }
        }
    }
}