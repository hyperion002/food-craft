package com.example.foodcraft.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodcraft.data.database.entities.FavouritesEntity
import com.example.foodcraft.data.database.entities.FoodJokeEntity
import com.example.foodcraft.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteRecipe(favouritesEntity: FavouritesEntity)

    @Query("SELECT * FROM favourite_recipes_table ORDER BY id ASC")
    fun readFavouriteRecipes(): Flow<List<FavouritesEntity>>

    @Delete
    suspend fun deleteFavouriteRecipes(favouritesEntity: FavouritesEntity)

    @Query("DELETE FROM favourite_recipes_table")
    suspend fun deleteAllFavouriteRecipes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)

    @Query("SELECT * FROM food_joke_table ORDER BY id ASC")
    fun readFoodJoke() : Flow<List<FoodJokeEntity>>
}