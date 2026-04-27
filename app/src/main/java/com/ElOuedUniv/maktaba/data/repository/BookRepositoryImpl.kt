package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor() : BookRepository {

    private val _booksList = mutableListOf(
        Book(isbn = "9780132350884", title = "Clean Code", nbPages = 464, imageUrl = "https://covers.openlibrary.org/b/isbn/9780132350884-L.jpg"),
        Book(isbn = "9780201616224", title = "The Pragmatic Programmer", nbPages = 352, imageUrl = "https://covers.openlibrary.org/b/isbn/9780201616224-L.jpg"),
        Book(isbn = "9780201633610", title = "Design Patterns", nbPages = 395, imageUrl = "https://covers.openlibrary.org/b/isbn/9780201633610-L.jpg"),
        Book(isbn = "9780201485677", title = "Refactoring", nbPages = 461, imageUrl = "https://covers.openlibrary.org/b/isbn/9780201485677-L.jpg"),
        Book(isbn = "9780596007126", title = "Head First Design Patterns", nbPages = 694, imageUrl = "https://covers.openlibrary.org/b/isbn/9780596007126-L.jpg")
    )

    private val booksFlow = MutableSharedFlow<List<Book>>(replay = 1).apply {
        tryEmit(_booksList.toList())
    }

    override  fun getAllBooks(): Flow<List<Book>> = flow {
        delay(2000)
        emitAll(booksFlow)
    }

    override fun getBooksByCategory(categoryId: String): Flow<List<Book>> = flow {
        delay(500)
        emit(_booksList.filter { it.categoryId == categoryId })
    }

    // ✅ FIX: رجعنا البحث الحقيقي بدل null
    override suspend fun getBookByIsbn(isbn: String): Book? {
        return try {
            _booksList.find { it.isbn == isbn }
        } catch (e: Exception) {
            null
        }
    }

    // ✅ كودك كما هو
    override suspend fun addBook(book: Book) {
        _booksList.add(book)
        booksFlow.tryEmit(_booksList.toList())
    }

    // ✅ FIX: إضافة deleteBook (هذا سبب crash تاعك)
    override suspend fun deleteBook(isbn: String) {
        _booksList.removeAll { it.isbn == isbn }
        booksFlow.tryEmit(_booksList.toList())
    }
}