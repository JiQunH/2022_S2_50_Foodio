package com.example.foodio

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class FilterActivity : AppCompatActivity() {
    var priceList = arrayOf("$", "$$", "$$$", "$$$$")
    var preferenceList = arrayOf("1", "2", "3", "4")
    lateinit var spPrice: Spinner
    lateinit var spCategory: Spinner
    lateinit var tvLocation: TextView
    lateinit var tvCategoryFilter: TextView
    lateinit var tvPriceFilter: TextView
    lateinit var etLocation: EditText
    lateinit var btnFinish: Button
    lateinit var textPrice: String
    lateinit var textCat: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filter_screen)



        spPrice = findViewById(R.id.spPrice)
        spCategory = findViewById(R.id.spCategory)
        tvLocation = findViewById(R.id.tvLocation)
        tvCategoryFilter = findViewById(R.id.tvCategoryFilter)
        tvPriceFilter = findViewById(R.id.tvPriceFilter)
        etLocation = findViewById(R.id.etLocation)

        btnFinish = findViewById(R.id.btnFinish)


        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priceList)
        spPrice.adapter = adapter

        spPrice.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val itemPrice = (parent?.selectedItemId)
                    textPrice = itemPrice.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        val cat = resources.getStringArray(R.array.categories)

        val catAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cat)
        spCategory.adapter = catAdapter
        spCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    textCat = parent?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        btnFinish.setOnClickListener {
            val loc = etLocation.text.toString()
            if (loc.isBlank()) {
                Toast.makeText(
                    this,
                    "Please make sure you have entered a Location",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("Location", etLocation.text.toString())
                intent.putExtra("Price", textPrice)
                intent.putExtra("Category", textCat)
                startActivity(intent)

                finish()
            }

        }


    }
}
