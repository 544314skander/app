package com.example.cat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cat.ui.CatApp
import com.example.cat.ui.CatViewModel
import com.example.cat.ui.theme.CatAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatAppTheme {
                val catViewModel: CatViewModel = viewModel()
                CatApp(viewModel = catViewModel)
            }
        }
    }
}
