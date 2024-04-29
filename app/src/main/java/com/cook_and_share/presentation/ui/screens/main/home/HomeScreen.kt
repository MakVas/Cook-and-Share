package com.cook_and_share.presentation.ui.screens.main.home

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cook_and_share.R
import com.cook_and_share.domain.model.Recipe
import com.cook_and_share.presentation.ui.components.CustomPagerIndicator
import com.cook_and_share.presentation.ui.components.RecipeBottomSheet
import com.cook_and_share.presentation.ui.components.RecipeItem
import com.cook_and_share.presentation.ui.components.TopBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val recipes = viewModel.recipes.collectAsState(emptyList())
    val dailyRecipes = viewModel.dailyRecipes.collectAsState(emptyList())

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val sheetState = rememberModalBottomSheetState()
    val isSheetExpanded = rememberSaveable { mutableStateOf(false) }


    RecipeBottomSheet(
        sheetState = sheetState,
        isSheetExpanded = isSheetExpanded
    )
    HomeScreenContent(
        recipes = recipes.value,
        dailyRecipes = dailyRecipes.value,
        scrollBehavior = scrollBehavior,
        isSheetExpanded = isSheetExpanded,
        isRecipeLiked = viewModel::isRecipeLiked,
        onRecipeLikeClick = viewModel::onRecipeLikeClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    isRecipeLiked: (Recipe) -> Flow<Boolean>,
    recipes: List<Recipe>,
    dailyRecipes: List<Recipe>,
    scrollBehavior: TopAppBarScrollBehavior,
    isSheetExpanded: MutableState<Boolean>,
    onRecipeLikeClick: (Recipe) -> Unit
) {

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                text = R.string.app_name,
                scrollBehavior = scrollBehavior
            )
        }
    ) { values ->
        Box(
            modifier = Modifier
                .padding(values)
                .fillMaxSize()
                .background(colorScheme.background)
        ) {
            NestedScrolling(
                dailyRecipes = dailyRecipes,
                recipes = recipes,
                isSheetExpanded = isSheetExpanded,
                isRecipeLiked = isRecipeLiked,
                onRecipeLikeClick = onRecipeLikeClick
            )
        }
    }
}

@Composable
private fun NestedScrolling(
    dailyRecipes: List<Recipe>,
    recipes: List<Recipe>,
    isRecipeLiked: (Recipe) -> Flow<Boolean>,
    isSheetExpanded: MutableState<Boolean>,
    onRecipeLikeClick: (Recipe) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(id = R.string.top_of_the_day),
                style = typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            SubRow(
                isRecipeLiked = isRecipeLiked,
                onRecipeLikeClick = onRecipeLikeClick,
                recipes = dailyRecipes,
                isSheetExpanded = isSheetExpanded
            )

            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(id = R.string.subscribed_recipes),
                style = typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
        subColumn(
            onRecipeLikeClick = onRecipeLikeClick,
            recipes = recipes,
            isRecipeLiked = isRecipeLiked,
            isSheetExpanded = isSheetExpanded,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SubRow(
    isRecipeLiked: (Recipe) -> Flow<Boolean>,
    onRecipeLikeClick: (Recipe) -> Unit,
    recipes: List<Recipe>,
    isSheetExpanded: MutableState<Boolean>
) {
    val bannerPagerState = rememberPagerState { recipes.size }
    var bannerAutomaticallyScrollPage by rememberSaveable { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = bannerAutomaticallyScrollPage, block = {
        scope.launch {
            delay(3000)
            if (bannerAutomaticallyScrollPage < recipes.size) bannerAutomaticallyScrollPage++
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
        ) {
            val isLiked = isRecipeLiked(recipes[it]).collectAsState(initial = false)
            RecipeItem(
                isLiked = isLiked.value,
                onRecipeLikeClick = onRecipeLikeClick,
                recipe = recipes[it],
                onClick = {
                    isSheetExpanded.value = true
                },
                isPreview = false
            )
        }
        CustomPagerIndicator(bannerPagerState.targetPage, recipes.size)
    }
}

private fun LazyListScope.subColumn(
    isRecipeLiked: (Recipe) -> Flow<Boolean>,
    onRecipeLikeClick: (Recipe) -> Unit,
    recipes: List<Recipe>,
    isSheetExpanded: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    items(recipes, key = { it.id }) { recipeItem ->
        val isLiked = isRecipeLiked(recipeItem).collectAsState(initial = false)
        RecipeItem(
            isLiked = isLiked.value,
            onRecipeLikeClick = onRecipeLikeClick,
            recipe = recipeItem,
            onClick = {
                isSheetExpanded.value = true
            },
            modifier = modifier,
            isPreview = false
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}