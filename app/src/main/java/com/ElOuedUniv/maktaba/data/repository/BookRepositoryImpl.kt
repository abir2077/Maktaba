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
        Book(id = 1, title = "Clean Code", isbn = "978-0-13-235088-4", pages = 464, imageUrl = "https://m.media-amazon.com/images/I/41xShlnTZTL.jpg"),
        Book(id = 2, title = "The Pragmatic Programmer", isbn = "978-0-201-61622-4", pages = 352, imageUrl = "https://m.media-amazon.com/images/I/61ztlXgCmpL._SL1500_.jpg"),
        Book(id = 3, title = "Design Patterns", isbn = "978-0201633610", pages = 395, imageUrl = "https://m.media-amazon.com/images/I/81IGFC6oFmL._SL1500_.jpg"),
        Book(id = 4, title = "Refactoring", isbn = "978-0134757599", pages = 448, imageUrl = "https://m.media-amazon.com/images/I/71e6ndHEwqL._SL1500_.jpg"),
        Book(id = 5, title = "Head First Design Patterns", isbn = "978-0-596-52068-7", pages =694 , imageUrl ="https://m.media-amazon.com/images/I/91quawUTiVL._SL1500_.jpg" )
    )

    private val booksFlow = MutableSharedFlow<List<Book>>(replay = 1).apply {
        tryEmit(_booksList.toList())
    }
    
    override fun getAllBooks(): Flow<List<Book>> = flow {
        delay(2000) // Simulate delay
        emitAll(booksFlow)
    }

    override fun getBookByIsbn(isbn: String): Book? {
        return _booksList.find { it.isbn == isbn }
    }

    override fun addBook(book: Book) {
        _booksList.add(book)
        booksFlow.tryEmit(_booksList.toList())
    }
}
