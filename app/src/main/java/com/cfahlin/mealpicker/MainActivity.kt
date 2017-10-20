package com.cfahlin.mealpicker

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.cfahlin.mealpicker.R.id.action_map
import com.cfahlin.mealpicker.R.id.action_settings
import com.cfahlin.mealpicker.R.menu.top_menu
import com.reqica.drilon.androidpermissionchecklibrary.CheckPermission
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val foodList = arrayListOf("Chinese", "Burgers", "Pizza", "Fast Food")
    private val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissions()

        my_toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(my_toolbar)


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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item != null) {
            return when(item.itemId) {
                action_map -> {
                    val mapIntent = Intent(this, MapsActivity::class.java)
                    startActivity(mapIntent)
                    true
                }

                action_settings->{

                    true
                }
                else -> {
                    super.onOptionsItemSelected(item)
                }
            }
        }
        return false
    }

    private fun checkPermissions() {
        val check  = CheckPermission(this)
        check.checkOne(android.Manifest.permission.ACCESS_FINE_LOCATION, null)

    }
}