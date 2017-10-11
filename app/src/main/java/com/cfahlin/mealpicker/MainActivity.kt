package com.cfahlin.mealpicker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val foodList = arrayListOf("Chinese", "Burgers", "Pizza", "Fast Food")
    private val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        decideButton.setOnClickListener {
            selectedFoodText.text = foodList[random.nextInt(foodList.count())]
        }

        addPlaceBtn.setOnClickListener {
            if(!addFoodText.text.isBlank()) {
                foodList.add(addFoodText.text.toString())
                addFoodText.text.clear()
            }
        }
    }
}