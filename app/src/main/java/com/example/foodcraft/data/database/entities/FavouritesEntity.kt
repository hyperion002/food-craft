package com.example.foodcraft.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodcraft.models.Result
import com.example.foodcraft.util.Constants.Companion.FAVOURITE_RECIPES_TABLE

@Entity(tableName = FAVOURITE_RECIPES_TABLE)
class FavouritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)