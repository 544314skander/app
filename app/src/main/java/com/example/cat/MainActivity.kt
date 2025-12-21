package com.example.cat

import android.Manifest
import android.content.Intent
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import com.example.cat.BuildConfig
import com.example.cat.R
import com.example.cat.notifications.CatFirebaseMessagingService
import com.example.cat.ui.CatApp
import com.example.cat.ui.CatViewModel
import com.example.cat.ui.theme.CatAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MainActivity : ComponentActivity() {

    private val navTargetFlow = MutableStateFlow<NavTarget?>(null)
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            showWelcomeNotification()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        navTargetFlow.value = intent.toNavTarget()
        maybeRequestNotificationPermission()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
            android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            showWelcomeNotification()
        }
        setContent {
            CatAppTheme {
                val catViewModel: CatViewModel = viewModel()
                val navTarget by navTargetFlow.collectAsState()
                CatApp(viewModel = catViewModel, navTarget = navTarget)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        navTargetFlow.update { intent.toNavTarget() }
    }

    private fun maybeRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
        val granted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        if (!granted) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun showWelcomeNotification() {
        val channelId = CatFirebaseMessagingService.CHANNEL_ID
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Cat updates",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Welcome back")
            .setContentText("Glad to see you in Cat Lounge!")
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(this).notify(1001, notification)
    }
}

sealed class NavTarget {
    data object Home : NavTarget()
    data object Tools : NavTarget()
    data class CatDetail(val id: Int) : NavTarget()
}

private fun Intent?.toNavTarget(): NavTarget? {
    if (this == null) return NavTarget.Home
    val catId = extras?.getString("catId")?.toIntOrNull()
    val destination = extras?.getString("destination")
    return when {
        catId != null -> NavTarget.CatDetail(catId)
        destination == "tools" -> NavTarget.Tools
        else -> NavTarget.Home
    }
}
