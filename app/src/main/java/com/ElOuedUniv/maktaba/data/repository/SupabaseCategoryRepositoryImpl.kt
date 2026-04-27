package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Category
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class SupabaseCategoryRepositoryImpl(
    private val client: SupabaseClient
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> = flow {
        val response = client.from("categories")
            .select()
            .data

        val result = Json {
            ignoreUnknownKeys = true
        }.decodeFromString(
            kotlinx.serialization.builtins.ListSerializer(Category.serializer()),
            response
        )

        emit(result)
    }

    override fun getCategoryById(id: String): Category? {
        return null
    }
    override fun getCategories(): Flow<List<Category>> = flow {
        val supabaseClient = null
        val result = supabaseClient // استدعاء Supabase
        emit(emptyList()) // مؤقت حتى تربطي API
    }
}