package com.ElOuedUniv.maktaba.data.di

import com.ElOuedUniv.maktaba.data.repository.BookRepository
import com.ElOuedUniv.maktaba.data.repository.CategoryRepository
import com.ElOuedUniv.maktaba.data.repository.SupabaseCategoryRepositoryImpl
import com.ElOuedUniv.maktaba.data.repository.SupabaseBookRepositoryImpl
import io.github.jan.supabase.SupabaseClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    // ❗ نحذفو SupabaseClient من هنا (راه موجود في SupabaseModule)

    @Provides
    @Singleton
    fun provideBookRepository(
        client: SupabaseClient
    ): BookRepository {
        return SupabaseBookRepositoryImpl(client)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        client: SupabaseClient
    ): CategoryRepository {
        return SupabaseCategoryRepositoryImpl(client)
    }
}