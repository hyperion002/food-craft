package com.example.foodcraft.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodcraft.models.FoodRecipe
import com.example.foodcraft.util.Constants.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}