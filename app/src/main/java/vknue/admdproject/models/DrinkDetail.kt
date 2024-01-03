package vknue.admdproject.models

data class DrinkDetail(
    val idDrink: String,
    val strDrink: String,
    val strCategory: String,
    val strIBA: String,
    val strAlcoholic: String,
    val strGlass: String,
    val strInstructions: String,
    val strDrinkThumb: String?,
    val lstDrinkIngredients: List<String>,
    val lstDrinkMeasures: List<String>

)
