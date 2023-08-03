package com.example.foodcraft.util

class Constants {
    companion object {
        const val API_KEY = "2a147263587443da88d176c63803f825"
//        const val API_KEY = "ac3dd11f23ae432a932fd2983d71890f"
        const val BASE_URL = "https://api.spoonacular.com"

        // API Query Keys
        const val QUERY_SEARCH = "query"
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        // ROOM Database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"

        // BottomSheet & Preferences
        const val DEFAULT_RECIPES_NUMBER = "50"
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"

        const val PREFERENCES_NAME = "food preferences"
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dealType"
        const val PREFERENCES_DIET_TYPE_ID = "dealTypeId"

        const val PREFERENCES_BACK_ONLINE = "backOnline"
    }
}