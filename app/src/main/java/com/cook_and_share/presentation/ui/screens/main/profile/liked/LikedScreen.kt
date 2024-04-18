package com.cook_and_share.presentation.ui.screens.main.profile.liked

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cook_and_share.R
import com.cook_and_share.presentation.ui.components.TopAppBarBackIcon
import com.cook_and_share.presentation.ui.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikedScreen(popUp: () -> Unit) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    LikedScreenContent(
        popUp = popUp,
        scrollBehavior = scrollBehavior
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LikedScreenContent(
    popUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                text = R.string.liked,
                scrollBehavior = scrollBehavior,
                iconButton = {
                    TopAppBarBackIcon(popUp)
                }
            )
        }
    ) { values ->
        Box(
            modifier = Modifier
                .padding(values)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            NestedScrolling()
        }
    }
}

@Composable
private fun NestedScrolling() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.padding(top = 16.dp))

        Text(
            text = "There will be liked posts",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}