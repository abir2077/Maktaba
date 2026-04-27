package com.ElOuedUniv.maktaba.presentation.onboarding

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun OnboardingView(
    onNavigateToLibrary: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to Maktaba", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Your personal digital library.")
        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            viewModel.onCompleteOnboarding()

            // ✅ حفظ أن onboarding تم
            val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            prefs.edit().putBoolean("onboarding_done", true).apply()

            onNavigateToLibrary()
        }) {
            Text("Get Started")
        }
    }
}