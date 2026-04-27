package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SupabaseBookRepositoryImpl(
    private val client: SupabaseClient
) : BookRepository {

    // ✅ إضافة كتاب إلى Supabase
    override suspend fun addBook(book: Book) {
        client
            .from("books")
            .insert(book)
    }

    // ✅ جلب الكتب من Supabase
    override  fun getAllBooks(): Flow<List<Book>> = flow {
        try {
            val result = client
                .from("books")
                .select()
                .decodeList<Book>()

            emit(result)
        } catch (e: Exception) {
            // في حالة خطأ نرجع قائمة فارغة (باش تعرفي كاين مشكل)
            emit(emptyList())
        }
    }

    // (اختياري) جلب كتاب واحد
    override suspend fun getBookByIsbn(isbn: String): Book? {
        return try {
            client.from("books")
                .select()
                .decodeList<Book>()
                .find { it.isbn == isbn }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun deleteBook(isbn: String) {
        client.from("books")
            .delete {
                filter {
                    eq("isbn", isbn)
                }
            }
    }

    override fun getBooksByCategory(categoryId: String): Flow<List<Book>> = flow {
        val result = client
            .from("books")
            .select {
                filter {
                    eq("category_id", categoryId)
                }
            }
            .decodeList<Book>()

        emit(result)
    }
}