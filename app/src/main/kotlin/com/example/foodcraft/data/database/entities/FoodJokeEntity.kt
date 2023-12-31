package com.example.foodcraft.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodcraft.models.FoodJoke
import com.example.foodcraft.util.Constants.FOOD_JOKE_TABLE

@Entity(FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @Embedded
    var foodJoke: FoodJoke
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}