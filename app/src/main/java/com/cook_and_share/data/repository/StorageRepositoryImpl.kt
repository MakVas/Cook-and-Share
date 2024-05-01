package com.cook_and_share.data.repository

import com.cook_and_share.domain.model.Profile
import com.cook_and_share.domain.model.Recipe
import com.cook_and_share.domain.repository.AuthRepository
import com.cook_and_share.domain.repository.StorageRepository
import com.cook_and_share.presentation.util.Constants.COLLECTION_NAME_RECIPES
import com.cook_and_share.presentation.util.Constants.COLLECTION_NAME_USERS
import com.cook_and_share.presentation.util.Constants.CREATED_AT_FIELD
import com.cook_and_share.presentation.util.Constants.IS_DAILY_FIELD
import com.cook_and_share.presentation.util.Constants.SAVE_RECIPE_TRACE
import com.cook_and_share.presentation.util.Constants.UPDATE_RECIPE_TRACE
import com.cook_and_share.presentation.util.Constants.USER_ID_FIELD
import com.cook_and_share.presentation.util.trace
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AuthRepository
) : StorageRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val myRecipes: Flow<List<Recipe>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                firestore
                    .collection(COLLECTION_NAME_RECIPES)
                    .whereEqualTo(USER_ID_FIELD, user.id)
                    .orderBy(CREATED_AT_FIELD, Query.Direction.DESCENDING)
                    .dataObjects()
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val recipes: Flow<List<Recipe>>
        get() =
            auth.currentUser.flatMapLatest {
                firestore
                    .collection(COLLECTION_NAME_RECIPES)
                    //.whereEqualTo(USER_ID_FIELD, user.id)
                    .orderBy(CREATED_AT_FIELD, Query.Direction.DESCENDING)
                    .dataObjects()
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val dailyRecipes: Flow<List<Recipe>>
        get() = auth.currentUser.flatMapLatest {
            firestore
                .collection(COLLECTION_NAME_RECIPES)
                .whereEqualTo(IS_DAILY_FIELD, true)
                .dataObjects()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun searchProfiles(query: String, fieldName: String): Flow<List<Profile>> {
        return auth.currentUser.flatMapLatest {
            firestore
                .collection(COLLECTION_NAME_USERS)
                .whereEqualTo(fieldName, query)
                .dataObjects()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun searchRecipes(search: String): Flow<List<Recipe>> {
        return auth.currentUser.flatMapLatest {
            firestore
                .collection(COLLECTION_NAME_RECIPES)
                .dataObjects()
        }
    }

    override suspend fun getRecipe(recipeId: String): Recipe? =
        firestore.collection(COLLECTION_NAME_RECIPES).document(recipeId).get().await().toObject()

    override suspend fun save(recipe: Recipe): String =
        trace(SAVE_RECIPE_TRACE) {
            val updatedTask = recipe.copy(userID = auth.currentUserId)
            firestore.collection(COLLECTION_NAME_RECIPES).add(updatedTask).await().id
        }

    override suspend fun update(recipe: Recipe): Unit =
        trace(UPDATE_RECIPE_TRACE) {
            firestore.collection(COLLECTION_NAME_RECIPES).document(recipe.id).set(recipe).await()
        }

    override suspend fun delete(recipeId: String) {
        firestore.collection(COLLECTION_NAME_RECIPES).document(recipeId).delete().await()
    }
}