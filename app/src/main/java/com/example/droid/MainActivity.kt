package com.example.droid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.droid.ui.DroidApp
import com.example.droid.ui.DroidViewModel
import com.example.droid.ui.theme.DroidAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DroidAppTheme {
                val droidViewModel: DroidViewModel = viewModel()
                DroidApp(viewModel = droidViewModel)
            }
        }
    }
}
