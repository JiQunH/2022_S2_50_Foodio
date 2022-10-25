package com.example.foodio.filter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.foodio.ui.MainActivity
import com.example.foodio.R


class FilterActivity : AppCompatActivity() {
    var priceList = arrayOf("$", "$$", "$$$", "$$$$")
    var preferenceList = arrayOf("1", "2", "3", "4")
    var locationList = arrayOf(
        "Auckland",
        "Hamilton",
        "Tauranga",
        "Wellington",
        "Christchurch",
        "Dunedin"
    )
    lateinit var spPrice: Spinner
    lateinit var spCategory: Spinner
    lateinit var spLocation: Spinner
    lateinit var tvLocation: TextView
    lateinit var tvCategoryFilter: TextView
    lateinit var tvPriceFilter: TextView
    lateinit var btnFinish: Button
    lateinit var textPrice: String
    lateinit var textCat: String
    lateinit var textLocation: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filter_screen)



        spPrice = findViewById(R.id.spPrice)
        spCategory = findViewById(R.id.spCategory)
        tvLocation = findViewById(R.id.tvLocation)
        tvCategoryFilter = findViewById(R.id.tvCategoryFilter)
        tvPriceFilter = findViewById(R.id.tvPriceFilter)
        spLocation = findViewById(R.id.spLocation)

        btnFinish = findViewById(R.id.btnFinish)


        val locationAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, locationList)
        spLocation.adapter = locationAdapter
        spLocation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                textLocation = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                textPrice = "Auckland"
            }
        }

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
                    val itemPrice = (parent.selectedItemId) + 1
                    textPrice = itemPrice.toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    textPrice = "$$"
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
                    textCat = parent.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    textCat = "Chinese"
                }
            }

        btnFinish.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Location", textLocation)
            intent.putExtra("Price", textPrice)
            intent.putExtra("Category", textCat)
            startActivity(intent)

            finish()

        }


    }
}
