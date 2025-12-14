package com.example.cat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import org.osmdroid.config.Configuration
import com.example.cat.BuildConfig
import com.example.cat.ui.CatApp
import com.example.cat.ui.CatViewModel
import com.example.cat.ui.theme.CatAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        setContent {
            CatAppTheme {
                val catViewModel: CatViewModel = viewModel()
                CatApp(viewModel = catViewModel)
            }
        }
    }
}
