package vknue.admdproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import vknue.admdproject.constants.GeneralConstants
import vknue.admdproject.constants.StringKeys
import vknue.admdproject.constants.URLConstants
import vknue.admdproject.databinding.ActivitySplashScreenBinding
import vknue.admdproject.framework.applyAnimation
import vknue.admdproject.models.DrinkDetail
import vknue.admdproject.models.DrinkResponse
import vknue.admdproject.networking.Requester
import vknue.admdproject.utilities.NetworkingUtils

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()

        downloadCocktails()
        redirect()

    }

    private fun downloadCocktails() {
        val gson = Gson()
        try {
            val drinksJson = gson.toJson(loadCocktails())
            val prefs = this.getSharedPreferences(StringKeys.PREFERENCES_NAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString(StringKeys.COCKTAIL_LIST_KEY, drinksJson)
            editor.apply()
        }catch (ex: Exception){
            ex.printStackTrace()
        }

    }

    private fun loadCocktails(): List<DrinkDetail> {
        val gson = Gson()
        val finalReceivedCocktailList: MutableList<DrinkDetail> = mutableListOf()

        GeneralConstants.ALPHABET_ARRAY.forEach { letter ->

            val receivedCocktailList = Requester.makeGetRequest(
                NetworkingUtils.addParameterToUrl(
                    URLConstants.LIST_ALL_COCKTAILS_BY_FIRST_LETTER,
                    "f",
                    letter
                )
            )

            val response =
                gson.fromJson(receivedCocktailList, DrinkResponse::class.java)
            val cocktailList: MutableList<DrinkDetail> = mutableListOf()
            response.drinks.forEach { drink ->

                val ingredients = listOfNotNull(
                    drink.strIngredient1,
                    drink.strIngredient2,
                    drink.strIngredient3,
                    drink.strIngredient4,
                    drink.strIngredient5,
                    drink.strIngredient6,
                    drink.strIngredient7,
                    drink.strIngredient8,
                    drink.strIngredient9,
                    drink.strIngredient10,
                    drink.strIngredient11,
                    drink.strIngredient12
                )

                val measures = listOfNotNull(
                    drink.strMeasure1,
                    drink.strMeasure2,
                    drink.strMeasure3,
                    drink.strMeasure4,
                    drink.strMeasure5,
                    drink.strMeasure6,
                    drink.strMeasure7,
                    drink.strMeasure8,
                    drink.strMeasure9,
                    drink.strMeasure10,
                    drink.strMeasure11,
                    drink.strMeasure12,

                )
                cocktailList.add(
                    DrinkDetail(
                        drink.idDrink,
                        drink.strDrink,
                        drink.strCategory,
                        drink.strIBA,
                        drink.strAlcoholic,
                        drink.strGlass,
                        drink.strInstructions,
                        drink.strDrinkThumb ?: "",
                        ingredients,
                        measures
                    )
                )
            }
            finalReceivedCocktailList.addAll(cocktailList)


        }
        return finalReceivedCocktailList;
    }

    private fun redirect() {
        Handler(Looper.getMainLooper()).postDelayed(
            { startActivity(Intent(this, HostActivity::class.java)) },
            1L
        )
    }

    private fun startAnimations() {
        binding.ivShaker.applyAnimation(R.anim.shake)
        binding.tvPleaseWait.applyAnimation(R.anim.slide)

    }

}