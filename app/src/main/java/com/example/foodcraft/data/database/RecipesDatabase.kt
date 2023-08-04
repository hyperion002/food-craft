package com.example.foodcraft.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodcraft.data.database.entities.FavouritesEntity
import com.example.foodcraft.data.database.entities.RecipesEntity

@TypeConverters(RecipesTypeConverter::class)
@Database(entities = [RecipesEntity::class, FavouritesEntity::class], version = 1, exportSchema = false)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun RecipesDao(): RecipesDao
}