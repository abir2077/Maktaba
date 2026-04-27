package com.ElOuedUniv.maktaba.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://mgmxynwlczrwuvvhojpf.supabase.co",
            supabaseKey = "sb_publishable_BL1hMyfhOqnMGATcs97SfQ_21gSH2Sb"
        ) {
            install(Postgrest)
            install(Storage)
        }
    }
}