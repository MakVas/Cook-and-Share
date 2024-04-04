package com.cook_and_share.add_recipe.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cook_and_share.R
import com.cook_and_share.core.presentation.ui.components.CategoryItem
import com.cook_and_share.core.presentation.ui.components.CustomTextField
import com.cook_and_share.core.presentation.ui.components.TopAppBarBackIcon
import com.cook_and_share.core.presentation.ui.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    navController: NavHostController,
    onValueChange: MutableState<String>
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(
                text = R.string.categories,
                scrollBehavior = scrollBehavior,
                iconButton = {
                    TopAppBarBackIcon(
                        navController = navController
                    )
                }
            )
        }
    ) { values ->
        Box(
            modifier = Modifier
                .padding(values)
                .fillMaxSize()
                .background(colorScheme.background)
        ) {
            NestedScrolling(onValueChange)
        }
    }
}

@Composable
private fun NestedScrolling(onValueChange: MutableState<String>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Spacer(modifier = Modifier.padding(top = 16.dp))

            CustomTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(65.dp)
                    .shadow(1.dp, RoundedCornerShape(16.dp), clip = true),
                icon = Icons.Default.Search,
                fieldLabel = stringResource(id = R.string.search),
                text = onValueChange.value,
                onValueChange = { onValueChange.value = it }
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))

        }
        item {
            Categories()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Categories() {

    Text(
        text = stringResource(id = R.string.popular_categories),
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        textAlign = TextAlign.Start,
    )

    Spacer(modifier = Modifier.padding(top = 16.dp))

    FlowRow(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp)
    ) {
        repeat(50) {
            CategoryItem(category = "Category$it")
        }
    }
}