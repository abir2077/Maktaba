package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Category
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor() : CategoryRepository {

    private val _categoriesList = listOf(

        Category(
            id = 1,
            name = "Programming",
            description = "Books about software development and coding",
            iconRes = android.R.drawable.ic_menu_edit   // ✏️ code / editing
        ),

        Category(
            id = 2,
            name = "Algorithms",
            description = "Books about algorithms and data structures",
            iconRes = android.R.drawable.ic_menu_sort_alphabetically  // 🔢 sorting logic
        ),

        Category(
            id = 3,
            name = "Databases",
            description = "Books about database design and management",
            iconRes = android.R.drawable.ic_menu_agenda
        ),

        Category(
            id = 4,
            name = "Mobile Development",
            description = "Books about Android and iOS development",
            iconRes = android.R.drawable.ic_menu_call  // 📱 mobile/dev idea
        ),

        Category(
            id = 5,
            name = "Artificial Intelligence",
            description = "Books about AI and Machine Learning",
            iconRes = android.R.drawable.ic_menu_compass  // 🧠 thinking/AI direction
        )
    )

    private val categoriesFlow = MutableSharedFlow<List<Category>>(replay = 1).apply {
        tryEmit(_categoriesList)
    }

    override fun getAllCategories(): Flow<List<Category>> = flow {
        delay(2000)
        emitAll(categoriesFlow)
    }

    override fun getCategoryById(id: String): Category? {
        return _categoriesList.find { it.name == id }
    }
}