package com.ElOuedUniv.maktaba.domain.di

import com.ElOuedUniv.maktaba.data.repository.BookRepository
import com.ElOuedUniv.maktaba.data.repository.CategoryRepository
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import com.ElOuedUniv.maktaba.domain.usecase.GetBooksUseCase
import com.ElOuedUniv.maktaba.domain.usecase.GetCategoriesUseCase
import android.content.ContentResolver
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(
        categoryRepository: CategoryRepository
    ): GetCategoriesUseCase {
        return GetCategoriesUseCase(categoryRepository)
    }

    @Provides
    @Singleton
    fun provideGetBooksUseCase(
        bookRepository: BookRepository
    ): GetBooksUseCase {
        return GetBooksUseCase(bookRepository)
    }

    @Provides
    @Singleton
    fun provideAddBookUseCase(
        bookRepository: BookRepository
    ): AddBookUseCase {
        return AddBookUseCase(bookRepository)
    }

    @Provides
    @Singleton
    fun provideGetBookByIsbnUseCase(
        bookRepository: BookRepository
    ): com.ElOuedUniv.maktaba.domain.usecase.GetBookByIsbnUseCase {
        return com.ElOuedUniv.maktaba.domain.usecase.GetBookByIsbnUseCase(bookRepository)
    }

    // ✅ FIX IMPORTANT: ContentResolver provider (correct + safe)
    @Provides
    @Singleton
    fun provideContentResolver(
        @ApplicationContext context: Context
    ): ContentResolver {
        return context.contentResolver
    }
}