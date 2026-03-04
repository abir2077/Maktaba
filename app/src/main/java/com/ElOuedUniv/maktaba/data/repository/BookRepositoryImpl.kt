package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book

class BookRepositoryImpl : BookRepository {

    private val booksList = listOf(
        Book(isbn = "978-0-13-235088-4", title = "Clean Code", nbPages = 464),
        Book(isbn = "978-0-201-61622-4", title = "The Pragmatic Programmer", nbPages = 352),
        Book(isbn = "978-0201633610", title = "Design Patterns", nbPages = 395),
        Book(isbn = "978-0134757599", title = "Refactoring", nbPages = 448),
        Book(isbn = "978-0-596-52068-7", title = "Head First Design Patterns", nbPages = 694)
    )
    
    override fun getAllBooks(): List<Book> {
        return booksList
    }

    override fun getBookByIsbn(isbn: String): Book? {
        return booksList.find { it.isbn == isbn }
    }
}

