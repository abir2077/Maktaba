package com.ElOuedUniv.maktaba.utils

import android.content.ContentResolver
import android.net.Uri
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupabaseStorageManager @Inject constructor(
    private val client: SupabaseClient,
    private val contentResolver: ContentResolver
) {

    suspend fun uploadBookCover(uri: Uri): String = withContext(Dispatchers.IO) {
        // 1. تحويل URI إلى File مؤقت
        val inputStream = contentResolver.openInputStream(uri)
            ?: throw Exception("لا يمكن فتح الصورة")

        val tempFile = File.createTempFile("book_cover_", ".jpg")
        FileOutputStream(tempFile).use { output ->
            inputStream.copyTo(output)
        }
        inputStream.close()

        // 2. اسم فريد للصورة
        val fileName = "${UUID.randomUUID()}.jpg"

        // 3. رفع الصورة إلى Supabase Storage
        try {
            client.storage.from("book_covers").upload(
                path = fileName,
                data = tempFile.readBytes()
            )

            // 4. الحصول على الرابط العام
            val publicUrl = client.storage.from("book_covers").publicUrl(fileName)

            // 5. حذف الملف المؤقت
            tempFile.delete()

            return@withContext publicUrl
        } catch (e: Exception) {
            tempFile.delete()
            throw Exception("فشل رفع الصورة: ${e.message}")
        }
    }
}