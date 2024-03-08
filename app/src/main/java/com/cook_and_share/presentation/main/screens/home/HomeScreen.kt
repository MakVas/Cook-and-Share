package com.cook_and_share.presentation.main.screens.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.cook_and_share.R
import com.cook_and_share.presentation.custom.CustomPagerIndicator
import com.cook_and_share.presentation.custom.RecipeItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(isSheetExpanded: MutableState<Boolean>) {

    Scaffold { values ->
        Box(
            modifier = Modifier
                .padding(values)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            NestedScrolling(isSheetExpanded)
        }
    }
}

@Composable
fun NestedScrolling(isSheetExpanded: MutableState<Boolean>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        //contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(id = R.string.top_of_the_day),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            SubRow(isSheetExpanded)

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(id = R.string.subscribed_recipes),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
        subColumn(
            isSheetExpanded,
            Modifier.padding(horizontal = 16.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SubRow(isSheetExpanded: MutableState<Boolean>) {
    val bannerList = (0..5).toList()
    val bannerPagerState = rememberPagerState { bannerList.size }
    var bannerAutomaticallyScrollPage by rememberSaveable { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = bannerAutomaticallyScrollPage, block = {
        scope.launch {
            delay(3000)
            if (bannerAutomaticallyScrollPage < bannerList.size) bannerAutomaticallyScrollPage++
            else bannerAutomaticallyScrollPage = 0
            bannerPagerState.animateScrollToPage(
                bannerAutomaticallyScrollPage, animationSpec = tween(500)
            )
        }
    })
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = bannerPagerState,
            contentPadding = PaddingValues(horizontal = 32.dp),
            pageSpacing = 16.dp,
        ) { page ->
            RecipeItem(
                onClick = {
                   isSheetExpanded.value = true
                },
                name = "username",
                title = "Item $page",
                likes = "123",
                isPreview = false
            )
        }
        CustomPagerIndicator(bannerPagerState.targetPage, bannerList.size)
    }
}

fun LazyListScope.subColumn(
    isSheetExpanded: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    items(50) {
        RecipeItem(
            onClick = {
                isSheetExpanded.value = true
            },
            name = "username",
            title = "Item $it",
            likes = "123",
            modifier = modifier,
            isPreview = false
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}