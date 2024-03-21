package com.cook_and_share.presentation.main.screens.add_recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cook_and_share.data.FirestoreRepository
import com.cook_and_share.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val repository: FirestoreRepository
): ViewModel(){

    private val _createRecipeFlow = MutableStateFlow<Resource<Void>?>(null)
    val createRecipeFlow: StateFlow<Resource<Void>?> = _createRecipeFlow
    fun createRecipe(
        title: String,
        imageUrl: String,
        tags: List<String>,
        ingredients: List<String>,
        recipe: String
    ) = viewModelScope.launch {
        _createRecipeFlow.value = Resource.Loading
        val result = repository.createRecipe(title, imageUrl, tags, ingredients, recipe)
        _createRecipeFlow.value = result
    }
}