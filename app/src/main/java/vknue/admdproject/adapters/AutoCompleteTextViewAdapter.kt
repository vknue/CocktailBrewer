package vknue.admdproject.adapters

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import vknue.admdproject.models.DrinkDetail

class AutoCompleteTextViewAdapter(context: Context, private val drinkList: List<DrinkDetail>) :
    ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line) {

    private val originalList: List<DrinkDetail> = drinkList.toList()

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                val filteredList = mutableListOf<String>()

                if (constraint.isNullOrBlank()) {
                    for (item in originalList) {
                        filteredList.add(item.strDrink)
                    }
                } else {
                    val filterPattern = constraint.toString().trim().toLowerCase()

                    for (item in originalList) {
                        if (item.strDrink.toLowerCase().contains(filterPattern)) {
                            filteredList.add(item.strDrink)
                        }
                    }
                }

                results.values = filteredList
                results.count = filteredList.size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                clear()
                if (results != null && results.count > 0) {
                    addAll(results.values as List<String>)
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }
}

