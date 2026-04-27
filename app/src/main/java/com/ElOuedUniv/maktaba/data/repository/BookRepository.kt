package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun addBook(book: Book)

    fun getBooksByCategory(categoryId: String): Flow<List<Book>>

    fun getAllBooks(): Flow<List<Book>>

    suspend fun getBookByIsbn(isbn: String): Book?

    suspend fun deleteBook(isbn: String)

}