package vknue.admdproject

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import vknue.admdproject.databinding.ActivityCocktailDetailsBinding
import vknue.admdproject.models.DrinkDetail
import vknue.admdproject.utilities.StorageUtils

class CocktailDetailsActivity : AppCompatActivity() {

    private lateinit var theCocktail : DrinkDetail

    private lateinit var binding : ActivityCocktailDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCocktailDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpListeners()
        fillData()

    }

    private fun setUpListeners() {
        binding.btnGoBack.setOnClickListener(){
            startActivity(Intent(this, HostActivity::class.java))
        }
    }

    private fun fillData() {
        receiveCocktail()

        Picasso.get().load(theCocktail.strDrinkThumb).into(binding.ivCocktailPhotograph)
        binding.tvCocktailName.text = theCocktail.strDrink
        binding.tvCocktailCategory.text = theCocktail.strCategory
        binding.tvCocktailIBA.text = theCocktail.strIBA
        binding.tvCocktailAlcoholic.text = theCocktail.strAlcoholic
        binding.tvCocktailGlass.text = theCocktail.strGlass
        binding.tvCocktailInstructions.text = theCocktail.strInstructions
        try {
            populateLists()
        }catch (ex:Exception){
            ex.printStackTrace()
        }
    }


    private fun populateLists() {
        if (theCocktail != null && theCocktail.lstDrinkIngredients != null && theCocktail.lstDrinkMeasures != null) {
            val data: MutableList<String> = mutableListOf()

            val ingredientList = theCocktail.lstDrinkIngredients
            val measureList = theCocktail.lstDrinkMeasures

            // Ensure both lists have the same size to avoid IndexOutOfBoundsException
            val minSize = minOf(ingredientList.size, measureList.size)

            for (i in 0 until minSize) {
                val ingredient = ingredientList[i]
                val measure = measureList[i]

                val element = "$ingredient : $measure"
                data.add(element)
            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
            binding.lvCocktailIngridients.adapter = adapter
        } else {
            // Handle the case where theCocktail or its properties are null
            // Show an error message or perform alternative logic
        }
    }


    private fun receiveCocktail() {
        theCocktail = StorageUtils.getStoredCocktailFromLocalStorage(this)
    }
}