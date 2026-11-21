package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AppTab
import com.example.ui.StylloViewModel
import com.example.ui.components.StylloBottomBar
import com.example.ui.components.StylloTopBar
import com.example.ui.screens.ClosetScreen
import com.example.ui.screens.CreateOutfitScreen
import com.example.ui.screens.StylistDnaScreen
import com.example.ui.screens.SwipeDeckScreen
import com.example.ui.screens.TodayScreen
import com.example.ui.theme.StylloBackground
import com.example.ui.theme.StylloTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StylloTheme {
                StylloApp()
            }
        }
    }
}

@Composable
fun StylloApp(
    viewModel: StylloViewModel = viewModel()
) {
    val currentTab by viewModel.currentTab.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = StylloBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            StylloTopBar(
                vipBadge = userProfile?.vipTier ?: "VIP Studio",
                onProfileClick = { viewModel.selectTab(AppTab.DNA) }
            )
        },
        bottomBar = {
            StylloBottomBar(
                currentTab = currentTab,
                onTabSelected = { viewModel.selectTab(it) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(StylloBackground)
        ) {
            Crossfade(targetState = currentTab, label = "TabCrossfade") { tab ->
                when (tab) {
                    AppTab.TODAY -> TodayScreen(
                        viewModel = viewModel,
                        onNavigateToCreate = { viewModel.selectTab(AppTab.CREATE) },
                        onNavigateToCloset = { viewModel.selectTab(AppTab.CLOSET) },
                        onNavigateToDna = { viewModel.selectTab(AppTab.DNA) }
                    )
                    AppTab.CLOSET -> ClosetScreen(
                        viewModel = viewModel,
                        onNavigateToCreate = { viewModel.selectTab(AppTab.CREATE) }
                    )
                    AppTab.CREATE -> CreateOutfitScreen(
                        viewModel = viewModel,
                        onNavigateToDeck = { viewModel.selectTab(AppTab.STYLIST) }
                    )
                    AppTab.STYLIST -> SwipeDeckScreen(
                        viewModel = viewModel
                    )
                    AppTab.DNA -> StylistDnaScreen(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
