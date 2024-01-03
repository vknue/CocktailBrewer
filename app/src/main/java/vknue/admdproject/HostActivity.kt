package vknue.admdproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import vknue.admdproject.adapters.AutoCompleteTextViewAdapter
import vknue.admdproject.constants.StringKeys
import vknue.admdproject.databinding.ActivityHostBinding
import vknue.admdproject.models.DrinkDetail
import vknue.admdproject.utilities.StorageUtils
import kotlin.random.Random

class HostActivity : AppCompatActivity() {

    private lateinit var cocktailList: List<DrinkDetail>
    private lateinit var randomChosenCocktail : DrinkDetail

    private lateinit var binding: ActivityHostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cocktailList = StorageUtils.getAllCocktailsFromLocalStorage(this)

        initiateRandomCocktail()
        setUpListeners()
        setUpAutocompleteField()

    }

    private fun setUpAutocompleteField() {
        val adapter = AutoCompleteTextViewAdapter(this, cocktailList)
        binding.actvSearch.setAdapter(adapter)
    }

    private fun checkCocktailDetails(cocktail: DrinkDetail){
        val gson = Gson()
        val cocktailJson = gson.toJson(cocktail)
        val prefs = this.getSharedPreferences(StringKeys.PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(StringKeys.COCKTAIL_DETAILS_TRANSFER_KEY, cocktailJson)
        editor.apply()
        startActivity(Intent(this, CocktailDetailsActivity::class.java))
    }

    private fun setUpListeners() {
        binding.btnCheckItOut.setOnClickListener {
            checkCocktailDetails(randomChosenCocktail)
        }
        binding.btnChangeIt.setOnClickListener(){
            initiateRandomCocktail()
        }
        binding.actvSearch.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            val selectedDrink = cocktailList.firstOrNull { it.strDrink == selectedItem }
            if(selectedDrink != null){
                checkCocktailDetails(selectedDrink)
            }
        }

    }


    private fun initiateRandomCocktail() {
        val randomIndex = Random.nextInt(0, cocktailList.size)
        randomChosenCocktail = cocktailList.get(randomIndex)

        binding.tvRandomCocktailName.setText(randomChosenCocktail.strDrink)

        if (randomChosenCocktail.strDrinkThumb != null)
            Picasso.get().load(randomChosenCocktail.strDrinkThumb).into(binding.ivRandomCocktail)

    }


}