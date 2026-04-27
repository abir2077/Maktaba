package com.ElOuedUniv.maktaba.domain.usecase

import com.ElOuedUniv.maktaba.data.repository.BookRepository
import javax.inject.Inject

class DeleteBookUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(isbn: String) {
        repository.deleteBook(isbn)
    }
}