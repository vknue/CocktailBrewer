package vknue.admdproject.utilities

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import vknue.admdproject.constants.StringKeys
import vknue.admdproject.models.DrinkDetail

class StorageUtils {

    companion object{
        @JvmStatic

        fun getAllCocktailsFromLocalStorage(context: Context) : List<DrinkDetail>{
            val prefs = context.getSharedPreferences(StringKeys.PREFERENCES_NAME, Context.MODE_PRIVATE)
            val jsonString = prefs.getString(StringKeys.COCKTAIL_LIST_KEY, null)

            val gson = Gson()
            val type = object : TypeToken<List<DrinkDetail>>() {}.type
            val fullList = gson.fromJson<List<DrinkDetail>>(jsonString, type)
            return fullList
        }

        fun getStoredCocktailFromLocalStorage(context: Context) : DrinkDetail{
            val prefs = context.getSharedPreferences(StringKeys.PREFERENCES_NAME, Context.MODE_PRIVATE)
            val jsonString = prefs.getString(StringKeys.COCKTAIL_DETAILS_TRANSFER_KEY, null)

            val gson = Gson()
            val type = object : TypeToken<DrinkDetail>() {}.type
            val cocktail = gson.fromJson<DrinkDetail>(jsonString, type)
            return cocktail
        }

    }

}