package com.ElOuedUniv.maktaba.domain.usecase

import com.ElOuedUniv.maktaba.data.repository.BookRepository
import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(): Flow<List<Book>> {
        return repository.getAllBooks()
    }
}
